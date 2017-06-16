
package FRC2015.Erector;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	//------------------------------PORTS---------------------------------------
	//							COMPUTER PORTS
	final int DRIVE_STICK_PORT = 0;
	final int SHOOT_STICK_PORT = 1;
	final int LAUNCHPAD_PORT = 2;
	//							ANALOG PORTS
	final int GYRO_PORT = 0;
	//							DIGITAL PORTS
	
	//							PWM PORTS
	final int FRONT_LEFT = 0;
	final int FRONT_RIGHT = 1;
	final int BACK_LEFT = 2;
	final int BACK_RIGHT = 3;

	//-----------------------------IMPORTS-------------------------------------
	//							COMPUTER IMPORTS
	Joystick driveStick;
	Joystick shootStick;
	Joystick launchPad;
	//							BUILT-IN IMPORTS
	Compressor compressor;
	BuiltInAccelerometer robotAccel;
	//							ANALOG IMPORTS
	Gyro gyro;
	//							DIGITAL IMPORTS
	//							PWM IMPORTS
	Talon frontLeft;
	Talon frontRight;
	Talon backLeft;
	Talon backRight;
	
    public void robotInit() {
    	driveStick = new Joystick(DRIVE_STICK_PORT);
    	compressor = new Compressor();
    	robotAccel = new BuiltInAccelerometer();
    	gyro = new Gyro(GYRO_PORT);
    	frontLeft = new Talon(FRONT_LEFT);
    	frontRight = new Talon(FRONT_RIGHT);
    	backLeft = new Talon(BACK_LEFT);
    	backRight = new Talon(BACK_RIGHT);
    	
    	compressor.start();
    	gyro.initGyro();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	gyro.reset();
    	while (isEnabled() && isOperatorControl()){
    		
    		double heading = gyro.getAngle();
    		
    		// Controller 1/2, Stick L/R, Axis X/Y
    		double C1LX = driveStick.getRawAxis(0);
    		double C1LY = driveStick.getRawAxis(1);
	        double C1RX = driveStick.getRawAxis(2);
	        
	        // Forward, Strafe, Rotation
	     	double forward = C1LY;
	     	double strafe = C1LX;
	     	double rotation = C1RX;
	     	double rad = ((heading/10)*3.141592654)/180;
	     	
	     	strafe = forward * Math.sin(rad) + strafe * Math.cos(rad);
			forward  = forward * Math.cos(rad) - strafe * Math.sin(rad);
			
			frontLeft.set(-forward - strafe - rotation);
			frontRight.set(forward - strafe - rotation);
			backLeft.set(-forward + strafe - rotation);
			backRight.set(forward + strafe - rotation);
			if(driveStick.getRawButton(6)) gyro.reset();
    	}
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
