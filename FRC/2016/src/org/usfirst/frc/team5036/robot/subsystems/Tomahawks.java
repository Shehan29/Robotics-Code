package org.usfirst.frc.team5036.robot.subsystems;

import org.usfirst.frc.team5036.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Tomahawks extends Subsystem {
	RobotMap map = new RobotMap();
    static Tomahawks instance;
    DoubleSolenoid tomahawkPistons;
    boolean isDeployed = false;
    public static Tomahawks getInstance(){
    	if(instance==null){
    		instance = new Tomahawks();
    	}
    	return instance;
    }
    public Tomahawks(){
    	tomahawkPistons = new DoubleSolenoid(map.tomahawkPistonsPort1,map.tomahawkPistonsPort2);
    }
    public boolean getPosition(){
    	return isDeployed;
    }
    public void deploy(){
    		tomahawkPistons.set(DoubleSolenoid.Value.kForward);
    		isDeployed = true;
    }
    public void retract(){
    		tomahawkPistons.set(DoubleSolenoid.Value.kReverse);
    		isDeployed = false;
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

