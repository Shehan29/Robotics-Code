package org.usfirst.frc.team5036.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonDrive extends CommandBase {
	double goal;
    public AutonDrive(double setpoint){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	goal = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.arcadeDrive(-0.75, -(drivetrain.getGyroReading())/5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(drivetrain.getRightEncoderReading()>=goal){
        	return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.brake(0.75);
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
