package org.usfirst.frc.team5036.robot.commands;

import org.usfirst.frc.team5036.robot.OI;
import org.usfirst.frc.team5036.robot.subsystems.Conveyor;
import org.usfirst.frc.team5036.robot.subsystems.Drivetrain;
import org.usfirst.frc.team5036.robot.subsystems.Tomahawks;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CommandBase extends Command {
	public static Drivetrain drivetrain;
	static Conveyor conveyor;
	public static Tomahawks tomahawks;
	static OI oi = new OI();
	public static void init(){
	    drivetrain = Drivetrain.getInstance();
		conveyor = Conveyor.getInstance();
		tomahawks = Tomahawks.getInstance();
		oi.init();
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}
