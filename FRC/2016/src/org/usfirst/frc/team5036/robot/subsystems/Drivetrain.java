package org.usfirst.frc.team5036.robot.subsystems;

import org.usfirst.frc.team5036.robot.RobotMap;
import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 *
 */
public class Drivetrain extends Subsystem {
	RobotMap map = new RobotMap();
    static Drivetrain drivetrainInstance;
    Victor leftSide,rightSide;
    Encoder leftEncoder,rightEncoder;
    public PIDController leftControl,rightControl,gyroControl;
    //Compressor compressor;
    ADXRS450_Gyro gyro;
    ADXL362 accel;
    Servo servo;
    public static Drivetrain getInstance(){
    	if(drivetrainInstance==null){
    		drivetrainInstance = new Drivetrain();
    	}
    	return drivetrainInstance;
    }
    public Drivetrain(){
    	leftSide = new Victor(map.leftDrivetrain);
    	rightSide = new Victor(map.rightDrivetrain);
    	gyro = new ADXRS450_Gyro();
    	rightEncoder = new Encoder(map.rightEncoderInput,map.rightEncoderOutput,false,Encoder.EncodingType.k4X);
    	rightEncoder.setMaxPeriod(.1);
    	rightEncoder.setMinRate(10);
    	rightEncoder.setDistancePerPulse(0.098646);
    	rightEncoder.setReverseDirection(false);
    	rightEncoder.setSamplesToAverage(7);
    	rightControl = new PIDController(0,0,0,0,rightEncoder,rightSide);
    	//leftControl = new PIDController(0,0,0,0,leftEncoder,rightSide);
    	//compressor = new Compressor();
    	gyroControl = new PIDController(0,0,0,gyro,leftSide);
    	accel = new ADXL362(Accelerometer.Range.k2G);
    	servo = new Servo(5);
    }
    public void arcadeDrive(double f, double r){
    	f = 0.7*(f*f*f)+(0.3*f);
    	r = 0.7*(r*r*r)+(0.3*r);
    	double scale = Math.max(1, Math.abs(f)+Math.abs(r));
    	leftSide.set(((f+r)/scale));
    	rightSide.set(-((f-r)/scale));
    }
    public void drivePID(double setpoint){
    	rightControl.setSetpoint(0);
    	rightControl.enable();
    	while(rightControl.getError()>0){
    		leftSide.set(rightSide.get());
    	}
    	rightControl.disable();
    }
    public double getLeftEncoderReading(){
    	return 0;
    }
    public double getRightEncoderReading(){
    	return rightEncoder.getDistance();
    }
    public double getGyroReading(){
    	return gyro.getAngle();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpec;ialCommand());
    }
    public void useServo(double x){
    	servo.set(x);
    }
    public void gyroPID(){
    	gyroControl.enable();
    	while(gyroControl.getError()>0.5){
    		rightSide.set(-leftSide.get());
    	}
    	leftSide.set(0);
    	rightSide.set(0);
    }
    public void resetGyro(){
    	gyro.reset();
    }
    public void resetEncoder(){
    	rightEncoder.reset();
    }
    public void stop(){
    	leftSide.set(0);
    	rightSide.set(0);
    }
    public double getAccelerometer(char axis){
    	if(axis=='z'){
    		return accel.getZ();
    	}
    	else if(axis=='x'){
    		return accel.getX();
    	}
    	return accel.getY();
    }
    public void brake(double value){
    	arcadeDrive(-value,0);
    	Timer.delay(0.1);
    	arcadeDrive(0,0);
    }
}
