/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    Joystick DriveStick = new Joystick(1);
    
    RobotDrive rd;
    
    Jaguar jag1 = new Jaguar(1);
    Jaguar jag2 = new Jaguar(2);
    Jaguar jag3 = new Jaguar(3);
    Jaguar jag4 = new Jaguar(4);
    
    protected final int BLOCKER_EXTRACT_BUTTON = 6;           
    protected final int BLOCKER_RETRACT_BUTTON = 7;                    
    protected final int V_EXTRACT_BUTTON = 4;                          
    protected final int V_RETRACT_BUTTON = 5;                                  
    protected final int I_EXTRACT_BUTTON = 3;                                
    protected final int I_RETRACT_BUTTON = 2; 
    
    final int SOLENOID_1_EXTRACT = 1;                                 
    final int SOLENOID_1_RETRACT = 2;                                         
    final int SOLENOID_2_EXTRACT = 3;                                         
    final int SOLENOID_2_RETRACT = 4;                                           
    final int SOLENOID_3_EXTRACT = 5;                                         
    final int SOLENOID_3_RETRACT = 6;  
    
    this.Blocker = new DoubleSolenoid(SOLENOID_1_EXTRACT,SOLENOID_1_RETRACT);  
    this.iPiston = new DoubleSolenoid(SOLENOID_3_EXTRACT,SOLENOID_3_RETRACT);   
    this.vPiston = new DoubleSolenoid(SOLENOID_2_EXTRACT,SOLENOID_2_RETRACT); 
    
    public void autonomous() {
        rd.setSafetyEnabled(false);
        while (isEnabled() && isAutonomous()){
            get.Watchdog().feed();
            rd.drive(0.15, 0.0);
            
        }
    
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
    while (isEnabled() && isOperatorControl())
    {
        rd = new RobotDrive (jag1, jag2, jag3, jag4);
        //Declare inputs to mecanum class
        rd.mecanumDrive_Cartesian(DriveStick.getX(), DriveStick.getY(), jsL.getTwist(), 0.0);
        //Add gyro later.
        
       if (DriveStick.getRawButton(BLOCKER_EXTRACT_BUTTON)){                  
           Blocker.set(DoubleSolenoid.Value.kForward);}                    
       else if(DriveStick.getRawButton(BLOCKER_RETRACT_BUTTON)){          
           Blocker.set(DoubleSolenoid.Value.kReverse);}                      
       else {Blocker.set(DoubleSolenoid.Value.kOff);}                        
       
       if (DriveStick.getRawButton(V_EXTRACT_BUTTON)){                       
           vPiston.set(DoubleSolenoid.Value.kForward);}               
       else{vPiston.set(DoubleSolenoid.Value.kReverse);}                  
                
       if (DriveStick.getRawButton(I_EXTRACT_BUTTON)){                
           iPiston.set(DoubleSolenoid.Value.kForward);}      
       else {iPiston.set(DoubleSolenoid.Value.kReverse);}  
        
    }
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
