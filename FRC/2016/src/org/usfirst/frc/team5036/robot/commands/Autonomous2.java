package org.usfirst.frc.team5036.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Autonomous2 extends CommandBase {
	final double encoderDistance = 223;
    public Autonomous2() {
    	requires(tomahawks);
    	requires(drivetrain);
    	requires(conveyor);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetEncoder();
    	drivetrain.resetGyro();
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    public void runCommand(){
    	SmartDashboard.putString("Auton Status:", "AUTON RUNNING");
    	while(drivetrain.getRightEncoderReading()<16){
    		drivetrain.arcadeDrive(0.75,-(drivetrain.getGyroReading())/5);
    	}
    	drivetrain.stop();
    	tomahawks.deploy();
    	Timer.delay(1);
    	while(drivetrain.getRightEncoderReading()<(encoderDistance-50)){
    		drivetrain.arcadeDrive(0.75, -(drivetrain.getGyroReading()/5));
    		SmartDashboard.putNumber("Encoder:",drivetrain.getRightEncoderReading());
    		SmartDashboard.putNumber("Gyro:", drivetrain.getGyroReading());
    		SmartDashboard.putNumber("Rotation", -(drivetrain.getGyroReading()/5));
    	}
    	drivetrain.brake(0.75);
    	drivetrain.stop();
    	Timer.delay(1);
    	while(drivetrain.getRightEncoderReading()<encoderDistance){
    		drivetrain.arcadeDrive(0.4,0);
    		SmartDashboard.putNumber("Encoder:",drivetrain.getRightEncoderReading());
    		SmartDashboard.putNumber("Gyro:", drivetrain.getGyroReading());
    		SmartDashboard.putNumber("Rotation", -(drivetrain.getGyroReading()/5));
    	}
    	SmartDashboard.putNumber("Encoder:",drivetrain.getRightEncoderReading());
		SmartDashboard.putNumber("Gyro:", drivetrain.getGyroReading());
		SmartDashboard.putNumber("Rotation", -(drivetrain.getGyroReading()/5));
		drivetrain.brake(0.4);
    	drivetrain.stop();
    	drivetrain.resetGyro();
    	drivetrain.resetEncoder();
    	while(drivetrain.getGyroReading()<54){
    		drivetrain.arcadeDrive(0, 0.5);
    	}
    	drivetrain.stop();
    	drivetrain.resetGyro();
    	drivetrain.resetEncoder();
    	while(drivetrain.getRightEncoderReading()<131){
    		drivetrain.arcadeDrive(0.75, -(drivetrain.getGyroReading()/5));
    	}
    	drivetrain.stop();
    	conveyor.reverseConveyor();
    	while(conveyor.isBallInPosition()){
    	}
    	conveyor.poke();
    	Timer.delay(3);
    	conveyor.stopConveyor();
    	conveyor.retractPokeyPiston();
    }
    public void stationary(){
    	drivetrain.stop();
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