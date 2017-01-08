package org.usfirst.frc.team5036.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick logitech, ps4, cameraJoystick;
	public void init(){
		 logitech = new Joystick(0);
		ps4 = new Joystick(1);
		cameraJoystick = new Joystick(2);
	}
	//Driver Controls
	public double getForward(){
		return -logitech.getRawAxis(1);
	}
	public double getRotation(){
		return logitech.getRawAxis(4);
	}
	//Operator Controls
	public boolean getOperatorStartCompressorButton(){
		return ps4.getRawButton(10);
	}
	public boolean getOperatorStopCompressorButton(){
		return ps4.getRawButton(9);
	}
	public boolean getTomahawksButton(){
		return ps4.getRawButton(8);
	}
	public boolean getConveyorIntakeManualControl(){
		return ps4.getRawButton(6);
	}
	public boolean getConveyorOutakeManualControl(){
		return ps4.getRawButton(5);
	}
	public boolean getConveyorIntakeSequenceButton(){
		return ps4.getRawButton(2);
	}
	public double getCameraJoystick(){
		return cameraJoystick.getRawAxis(0);
		
	}
	public boolean getPokeyPistonButton(){
		return ps4.getRawButton(7);
	}
}

