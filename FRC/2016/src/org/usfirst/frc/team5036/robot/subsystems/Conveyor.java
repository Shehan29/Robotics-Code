package org.usfirst.frc.team5036.robot.subsystems;

import org.usfirst.frc.team5036.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Conveyor extends Subsystem {
	RobotMap map = new RobotMap();
    static Conveyor instance;
    Talon conveyorMotor;
    DigitalInput button;
    Relay relay;
    DoubleSolenoid pokeyPiston;
    boolean areLightsOn = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public static Conveyor getInstance(){
    	if(instance==null){
    		instance = new Conveyor();
    	}
    	return instance;
    }
    public Conveyor(){
    	conveyorMotor = new Talon(map.conveyorMotor);
    	button = new DigitalInput(map.buttonSensor);
    	relay = new Relay(0);
    	pokeyPiston = new DoubleSolenoid(map.pokeyPistonPort1,map.pokeyPistonPort2);
    }
    public void startConveyor(){
    	conveyorMotor.set(1);
    }
    public void stopConveyor(){
    	conveyorMotor.set(0);
    }
    public void setCustomSpeed(double speed){
    	conveyorMotor.set(speed);
    }
    public void reverseConveyor(){
    	conveyorMotor.set(-1);
    }
    public boolean isBallInPosition(){
    	return !(button.get());
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void lightUp(){
    	if(!areLightsOn){
    		relay.set(Relay.Value.kForward);
    		areLightsOn = true;
    	}
    }
    public void turnOffLights(){
    	if(areLightsOn){
        	relay.set(Relay.Value.kReverse);
        	areLightsOn = false;
    	}
    }
    public boolean isLitUp(){
    	return areLightsOn;
    }
    public void poke(){
    	pokeyPiston.set(DoubleSolenoid.Value.kForward);
    }
    public void retractPokeyPiston(){
    	pokeyPiston.set(DoubleSolenoid.Value.kReverse);
    }
}

