package test;
import java.rmi.RemoteException;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class CalibrateColor {
  public static void main(String[] args) {
    try {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
        SampleProvider sampleProvider=colorSensor.getColorIDMode();
        float[] samples=new float[3];
        samples=sampleProvider.fetchSample(samples,0);
        
        System.out.println("RGB="+samples[0]+" / "+samples[1]+" / "+samples[2]);

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
