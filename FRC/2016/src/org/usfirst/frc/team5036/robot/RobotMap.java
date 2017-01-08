package org.usfirst.frc.team5036.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//PWM PORTS
	//Drivetrain
	final public static int rightDrivetrain = 0;
	final public static int leftDrivetrain = 1;
	//Conveyor
	final public static int conveyorMotor = 2;
	//DIGITAL I/0
	//Drivetrain
	final public static int rightEncoderInput = 2;
	final public static int rightEncoderOutput = 3;
	//Conveyor
	final public static int buttonSensor = 0;
	//PISTONS
	//Tomahawks
	final public static int tomahawkPistonsPort1 = 0;
	final public static int tomahawkPistonsPort2 = 1;
	//Pokey Piston
	final public static int pokeyPistonPort1 = 2;
	final public static int pokeyPistonPort2 = 3;
}
