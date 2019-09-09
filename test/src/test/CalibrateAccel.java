package test;

import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.Dump;
import lejos.robotics.filter.LinearCalibrationFilter;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
 
public class CalibrateAccel {
  //DexterIMUSensor         sensor;
  EV3IRSensor               sensor;
  SampleProvider          accel;
  LinearCalibrationFilter calibrate;
  SampleProvider          dump;
  SampleProvider          mean;
  LED                     led      = LocalEV3.get().getLED();
  String                  filename = "DexterIMUAccel3";
 
  public static void main(String[] args) {
    CalibrateAccel foo = new CalibrateAccel();
    foo.showParameters();
    foo.showSamples();
    foo.calibrate();
    foo.showParameters();
    foo.showSamples();
    foo.sensor.close();
  }
 
  /**
   * Constructor. Initiate sensor and filters.
   */
  CalibrateAccel() {
    // Use High speed I2C
    //Port port1 = LocalEV3.get().getPort("S1");
    //I2CPort myPort = port1.open(I2CPort.class);
    //myPort.setType(I2CPort.TYPE_HIGHSPEED);
 
    sensor = new EV3IRSensor(SensorPort.S1);
    accel = sensor.getDistanceMode();
 
    // Use a mean filter to remove some of the noise from the samples.
    mean = new MeanFilter(accel, 30);
 
    // Use the Linear calibration filter
    calibrate = new LinearCalibrationFilter(mean);
 
    // Calibrate for offset errors only.
    calibrate.setCalibrationType(LinearCalibrationFilter.OFFSET_AND_SCALE_CALIBRATION);
 
    // Use a Dump filter to display the samples on screen
    dump = new Dump(calibrate);
  }
 
  /**
   * Calibration routine (LED color is red to indicate calibration). Use buttons
   * to control calibration. Up suspends calibration (LED color is orange), Down
   * resumes calibration (LED color is red). Enter ends calibration and saves
   * parameters to file system (LED green). Esc ends calibration and does not
   * save the parameters (LED off).
   */
  void calibrate() {
    float[] sample = new float[dump.sampleSize()];
 
    led.setPattern(2);
 
    // Calibrate offset error against a real value of 0
    calibrate.setOffsetCalibration(0);
 
    // Calibrate scale error against a maximum value of 9.81 and a minimum value
    // of -9.81
    calibrate.setScaleCalibration(9.81f);
 
    calibrate.startCalibration();
 
    while (Button.ESCAPE.isUp() && Button.ENTER.isUp()) {
      dump.fetchSample(sample, 0);
      if (Button.UP.isDown()) {
        // Suspend calibration
        led.setPattern(3);
        calibrate.suspendCalibration();
        while (Button.UP.isDown())
          ;
      }
      if (Button.DOWN.isDown()) {
        // Resume calibration
        led.setPattern(2);
        calibrate.resumeCalibration();
        while (Button.DOWN.isDown())
          ;
      }
      Delay.msDelay(33);
    }
    calibrate.stopCalibration();
    if (Button.ENTER.isDown()) {
      led.setPattern(1);
      calibrate.save(filename);
    } else {
      led.setPattern(0);
    }
    while (Button.ESCAPE.isDown() || Button.ENTER.isDown())
      ;
  }
 
  /**
   * Periodically fetch a sample and show on screen
   */
  void showSamples() {
    float[] sample = new float[dump.sampleSize()];
 
    led.setPattern(0);
 
    while (Button.ESCAPE.isUp() && Button.ENTER.isUp()) {
      dump.fetchSample(sample, 0);
      Delay.msDelay(33);
    }
    while (Button.ESCAPE.isDown() || Button.ENTER.isDown())
      ;
 
  }
 
  /**
   * Show the calibration parameters in use.
   */
  void showParameters() {
    float[] offset = calibrate.getOffsetCorrection();
    float[] scale = calibrate.getScaleCorrection();
    System.out.format("Calibration: %d%n", calibrate.getCalibrationType());
    for (int i = 0; i < calibrate.sampleSize(); i++) {
      System.out.format("%f  %f%n", offset[i], scale[i]);
    }
    Button.ENTER.waitForPressAndRelease();
  }
 
}