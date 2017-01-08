package org.usfirst.frc.team5036.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Teleop extends CommandBase {

    public Teleop() {
       requires(conveyor);
       requires(tomahawks);
       requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.useServo(0.5);
    	drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }
    public void runCommand(){
    	drive();
    	if(oi.logitech.getRawButton(1)){
    		drivetrain.resetEncoder();
    	}
    	if(oi.getPokeyPistonButton()){
    		conveyor.retractPokeyPiston();
    	}
    	else{
    		conveyor.poke();
    	}
    	SmartDashboard.putString("LIGTS STATUS:", lightStatus());
    	SmartDashboard.putString("Conveyor: ",conveyorStatus());
    	SmartDashboard.putString("Ball: ", ballStatus());
    	tomahawks();
    	conveyor();
    	cameraServo();
    	if(oi.logitech.getRawButton(3)){
    		drivetrain.resetEncoder();
    		drivetrain.resetGyro();
    	}
    	SmartDashboard.putNumber("Right Side",drivetrain.getRightEncoderReading());
    	SmartDashboard.putNumber("Gyro",drivetrain.getGyroReading());
    	SmartDashboard.putNumber("Accel-Y:", drivetrain.getAccelerometer('y'));
    	SmartDashboard.putNumber("Accel-X:", drivetrain.getAccelerometer('x'));
    	SmartDashboard.putNumber("Accel-Z:", drivetrain.getAccelerometer('z'));
    }
    public void drive()
    {
    	drivetrain.arcadeDrive(oi.getForward(), oi.getRotation());
    }
    public String conveyorStatus(){
    	if(oi.getConveyorIntakeManualControl()){
    		return "INTAKING";
    	}
    	else if(oi.getConveyorOutakeManualControl()){
    		return "REVERSING";
    	}
    	else if(oi.getConveyorIntakeSequenceButton()){
    		return "RUNNING SEQUENCE";
    	}
    	return "NOT RUNNING";
    }
    public String lightStatus(){
    	if(conveyor.isBallInPosition()){
    		conveyor.lightUp();
    		return "ON";
    	}
        conveyor.turnOffLights();
    	return "OFF";
    }
    public String ballStatus(){
    	if(conveyor.isBallInPosition()){
    		return "BALL DETECTED";
    	}
    	return "NO BALL";
    }
    public void conveyor(){
    	if(oi.getConveyorIntakeManualControl() && !conveyor.isBallInPosition()){
    		conveyor.startConveyor();
    	}
    	else if(oi.getConveyorOutakeManualControl()){
    		conveyor.reverseConveyor();
    	}
    	else if(oi.getConveyorIntakeSequenceButton()){
    		intakeSequence();
    	}
    	else{
    		conveyor.stopConveyor();
    	}
    }
    public void intakeSequence(){
    	while(!conveyor.isBallInPosition() && !oi.getConveyorIntakeSequenceButton()){
    		conveyor.startConveyor();
    		drive();
    		tomahawks();
    		cameraServo();
    	}
    	conveyor.stopConveyor();
    }
    public void tomahawks(){
    	if(oi.getTomahawksButton()){
    		tomahawks.retract();
    		SmartDashboard.putString("Tomahawks:", "DEPLOYED");
    	}
    	else{
    		tomahawks.deploy();
    		SmartDashboard.putString("Tomahawks", "RETRACTED");
    	}
    }
    public void cameraServo(){
    	drivetrain.useServo((oi.getCameraJoystick()+1)/2);
    }
    public void pokeyPiston(){
    	if(oi.getPokeyPistonButton()){
    		conveyor.poke();
    	}
    	else{
    		conveyor.retractPokeyPiston();
    	}
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

