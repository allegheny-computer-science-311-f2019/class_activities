import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class CalibrateColor {
	public static void main(String[] args) {

		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		SampleProvider sampleProvider = colorSensor.getColorIDMode();
		float[] samples = new float[sampleProvider.sampleSize()];
		sampleProvider.fetchSample(samples,0);
		
		while(true) {
			sampleProvider.fetchSample(samples,0);
			System.out.println("Color = " + samples[0]);
			// stop if detect red
			if(samples[0] == Color.RED) {
				break;
			}
		}
		
		colorSensor.close();
  }
}