/*
@author Shan Virk;
Team 5036 - RoboDevils
*/
#pragma config(Motor,  port1,           frontLeftSC,   tmotorVex393_HBridge, openLoop, driveLeft)
#pragma config(Motor,  port2,           frontRightSC,  tmotorVex393_MC29, openLoop, driveRight)
#pragma config(Motor,  port3,           backLeftSC,    tmotorVex393_MC29, openLoop, reversed, driveLeft)
#pragma config(Motor,  port4,           backRightSC,   tmotorVex393_MC29, openLoop, reversed, driveRight)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//
/*Robot Diagram
    ____________________
    |M1              M2|
    |                  |
    |                  |
    |                  |
    |                  |
    |                  |
    |M3              M4|
    ____________________

M1 => frontLeftSC
M2 => frontRightSC
M3 => backLeftSC
M4 => backRightSC
*/
//						KAJ DRIVE - TANK DRIVETRAIN
task main(){
	while(true){
  int leftSpeed = (vexRT[Ch3] + vexRT[Ch1]);
  int rightSpeed = (vexRT[Ch3] - vexRT[Ch1]);
  motor[frontLeftSC] = leftSpeed;
  motor[frontRightSC] = rightSpeed;
	motor[backLeftSC] = leftSpeed;
	motor[backRightSC] = rightSpeed;
	}// End Bracket of while statement
}//End Bracket of task "main"
