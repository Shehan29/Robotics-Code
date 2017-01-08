
package org.usfirst.frc.team5036.robot;

import org.usfirst.frc.team5036.robot.commands.AutonDrive;
import org.usfirst.frc.team5036.robot.commands.Autonomous1;
import org.usfirst.frc.team5036.robot.commands.Autonomous2;
import org.usfirst.frc.team5036.robot.commands.CommandBase;
import org.usfirst.frc.team5036.robot.commands.Teleop;
import org.usfirst.frc.team5036.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Drivetrain drivetrain;
	Compressor compressor;
	Teleop teleop;
	CameraServer camera;
	SendableChooser autonChooser;
	String selected;
    /**
     * Th   is function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		CommandBase.init();
		//drivetrain = new Drivetrain();
        teleop = new Teleop();
        if(CameraServer.getInstance()!=null){
        	camera = CameraServer.getInstance();
            camera.setQuality(25);
            camera.startAutomaticCapture("cam0");
        }
        autonChooser = new SendableChooser();
        autonChooser.addDefault("Low Bar Straigt Drive","10 Points");
        autonChooser.addObject("Low Bar + Low Goal", "15 Points");
        autonChooser.addObject("Nothing","nothing");
        autonChooser.addObject("Bumpy Obstacles Straight Drive", "10 Points, Plan B");
        autonChooser.addObject("Port Cullis","backwards straight drive");
        autonChooser.addObject("Short Drive","2 Points");
        SmartDashboard.putData("AUTONS:",autonChooser);
        compressor = new Compressor();
//        chooser.addObject("My Auto", new MyAutoCommand());
       
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 * 
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	compressor.start();
    	teleop.drivetrain.resetEncoder();
    	selected = (String)autonChooser.getSelected();
    	//Scheduler.getInstance().add(autonDrive2);
    	
        //selected = (String)autonChooser.getSelected();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	teleop.drivetrain.resetEncoder();
    	teleop.drivetrain.resetGyro();
    	if(selected.equals("10 Points")){
    		Timer.delay(5);
    	while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<18){
    		teleop.drivetrain.arcadeDrive(0.75, -(teleop.drivetrain.getGyroReading())/5);
    		SmartDashboard.putNumber("Encoder", teleop.drivetrain.getRightEncoderReading());
    	}
    	teleop.drivetrain.stop();
    	teleop.tomahawks.retract();
    	Timer.delay(1);
    	while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<233){
    		teleop.drivetrain.arcadeDrive(0.75, -(teleop.drivetrain.getGyroReading())/5);
    		SmartDashboard.putNumber("Encoder",teleop.drivetrain.getRightEncoderReading());
    	}
    	teleop.drivetrain.stop();
    	teleop.tomahawks.deploy();
    	while(isAutonomous()){
    		teleop.drivetrain.stop();
    	}
    	SmartDashboard.putNumber("Encoder", teleop.drivetrain.getRightEncoderReading());
    	}
    	else if(selected.equals("nothing")){
    		while(isAutonomous()){
    			teleop.drivetrain.stop();
    		}
    	}
    	else if(selected.equals("15 Points")){
    		Timer.delay(5);
    		while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<18){
        		teleop.drivetrain.arcadeDrive(0.75, -(teleop.drivetrain.getGyroReading())/5);
        		SmartDashboard.putNumber("Encoder", teleop.drivetrain.getRightEncoderReading());
        	}
        	teleop.drivetrain.stop();
        	teleop.tomahawks.retract();
        	Timer.delay(1);
        	while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<180-50){
        		teleop.drivetrain.arcadeDrive(0.4,0 );
        		SmartDashboard.putNumber("Encoder",teleop.drivetrain.getRightEncoderReading());
        	}
        	teleop.drivetrain.brake(0.75);
        	teleop.drivetrain.stop();
        	while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<180){
        		teleop.drivetrain.arcadeDrive(0.4, 0);
        		SmartDashboard.putNumber("Encoder",teleop.drivetrain.getRightEncoderReading());
        	}
        	teleop.drivetrain.brake(0.4);
        	teleop.drivetrain.stop();
        	teleop.tomahawks.deploy();
        	while(isAutonomous()&& teleop.drivetrain.getGyroReading()<54){
        		teleop.drivetrain.arcadeDrive(0,0.5);
        	}
        	teleop.drivetrain.stop();
        	teleop.drivetrain.resetEncoder();
        	teleop.drivetrain.resetGyro();
        	//Add code for low goal here.
        	while(isAutonomous()){
        		teleop.drivetrain.stop();
        	}
    	}
    	else if(selected.equals("10 Points, Plan B")){
    		teleop.tomahawks.deploy();
    		while(isAutonomous()&&teleop.drivetrain.getRightEncoderReading()<10){
    			teleop.drivetrain.arcadeDrive(0.5,0);
    		}
    		while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()<233){
    			teleop.drivetrain.arcadeDrive(1, -(teleop.drivetrain.getGyroReading()/5));
    		}
    		teleop.drivetrain.stop();
    		while(isAutonomous()){
        		teleop.drivetrain.stop();
        	}
    	}
    	else if(selected.equals("backwards straight drive")){
    		Timer.delay(5);
    		teleop.tomahawks.retract();
    		Timer.delay(1.5);
    		while(isAutonomous() && teleop.drivetrain.getRightEncoderReading()>-180){
    			teleop.drivetrain.arcadeDrive(-0.4,0);
    		}
    		teleop.drivetrain.stop();
    		while(isAutonomous()){
    			teleop.drivetrain.stop();
    		}
    	}
    	else if(selected.equals("2 Points")){
    		teleop.tomahawks.deploy();
    		while(isAutonomous()&&teleop.drivetrain.getRightEncoderReading()<100){
    			teleop.drivetrain.arcadeDrive(0.4,70);
    		}
    		teleop.drivetrain.stop();
    		while(isAutonomous()){
    			teleop.drivetrain.stop();
    		}
    	}
    }
    public void teleopInit() {
    	compressor.start();
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	//Scheduler.getInstance().removeAll();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() { 
    	while(isOperatorControl() && isEnabled()){
    		teleop.runCommand();
    	}
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	
    	//LiveWindow.addActuator("Drivetrain", "GYRO PID", teleopOther.drivetrain.gyroControl);
    	//LiveWindow.addActuator("Drivetrain", "Right Encoder", teleopOther.drivetrain.rightControl);
    }
    public void testPeriodic() {
    	
    	while(isTest()){
    		LiveWindow.run();
    	}
    }
}
