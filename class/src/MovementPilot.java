import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

/** Example of using Chassis and MovePilot.
 * Ref: https://lejosnews.wordpress.com/2015/05/12/lejos-navigation-the-chassis/
 * 
 * @author Janyl Jumadinova
 *
 */
public class MovementPilot {
  
  /** Main method with declarations and statements for
   * simple movements (travel and rotation) of MovePilot 
   * @param args
   */
  public static void main(String[] args) {
    Wheel left = WheeledChassis.modelWheel(Motor.A, 42.2).offset(72).gearRatio(2);
    Wheel right = WheeledChassis.modelWheel(Motor.B, 42.2).offset(-72).gearRatio(2);
    Chassis myChassis = new WheeledChassis( new Wheel[]{left, right}, WheeledChassis.TYPE_DIFFERENTIAL);
    
    MovePilot pilot = new MovePilot(myChassis);
    
    pilot.travel(50);
    Delay.msDelay(100);
    pilot.rotate(90);
    Delay.msDelay(100);
    pilot.travel(100);
    
  }
}
