package test;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class CalibrateColor {
  public static void main(String[] args) {
    try {
      EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
      SampleProvider sampleProvider = colorSensor.getColorIDMode();
      float[] samples = new float[3];
      samples = sampleProvider.fetchSample(samples,0);

      System.out.println("RGB = " + samples[0] + " / " + samples[1] + " / " + samples[2]);

      while (System.in.available() == 0) {
        samples=sampleProvider.fetchSample();

        if ((samples[0] > 0.255f) && (samples[0] < 0.265f) && (samples[1] > 0.260f) &&
            (samples[1] < 0.270f) && (samples[2] > 0.195f) && (samples[2] < 0.205f)) {
          System.out.println("White");
        }

        if ((samples[0] > 0.175f) && (samples[0] < 0.190f) && (samples[1] > 0.040f) &&
            (samples[1] < 0.055f) && (samples[2] > 0.025f) && (samples[2] < 0.045f)) {
          System.out.println("Red");
        }
        Delay.msDelay(50);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        sampleProvider.close();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }
}
