import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class Color {
	public static void main(String[] args) {

		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		SampleProvider sampleProvider = colorSensor.getColorIDMode();
		float[] samples = new float[sampleProvider.sampleSize()];
		sampleProvider.fetchSample(samples,0);

		System.out.println("Color = " + samples[0]);

		colorSensor.close();
  }
}
