/** @file opcontrol.c
 * @brief File for operator control code
 *
 * This file should contain the user operatorControl() function and any functions related to it.
 *
 * Copyright (c) 2011-2014, Purdue University ACM SIG BOTS.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Purdue University ACM SIG BOTS nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL PURDUE UNIVERSITY ACM SIG BOTS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Purdue Robotics OS contains FreeRTOS (http://www.freertos.org) whose source code may be
 * obtained from http://sourceforge.net/projects/freertos/files/ or on request.
 */

#include "main.h"
extern Gyro gyro;

int frontLeft = 2;
int frontRight = 3;
int backLeft = 4;
int backRight = 5;
int elevatorLeft = 6;
int elevatorRight = 7;
int MOTOR_MAX = 127;
int MOTOR_MIN = -127;
/*
 * Runs the user operator control code. This function will be started in its own task with the
 * default priority and stack size whenever the robot is enabled via the Field Management System
 * or the VEX Competition Switch in the operator control mode. If the robot is disabled or
 * communications is lost, the operator control task will be stopped by the kernel. Re-enabling
 * the robot will restart the task, not resume it from where it left off.
 *
 * If no VEX Competition Switch or Field Management system is plugged in, the VEX Cortex will
 * run the operator control task. Be warned that this will also occur if the VEX Cortex is
 * tethered directly to a computer via the USB A to A cable without any VEX Joystick attached.
 *
 * Code running in this task can take almost any action, as the VEX Joystick is available and
 * the scheduler is operational. However, proper use of delay() or taskDelayUntil() is highly
 * recommended to give other tasks (including system tasks such as updating LCDs) time to run.
 *
 * This task should never exit; it should end with some kind of infinite loop, even if empty.
 */
void operatorControl() {

	while(1) {
		//DRIVETRAIN
		int heading = gyroGet(gyro);
		// Controller 1/2, Stick L/R, Axis X/Y
		int C1LX = joystickGetAnalog(1, 4);
		int C1LY = joystickGetAnalog(1, 3);
		int C1RX = joystickGetAnalog(1, 1);

		// Forward, Strafe, Rotation
		int forward = C1LY;
		int strafe = C1LX;
		int rotation = C1RX;
		float rad = ((heading)*M_PI)/180;

		// rotate coordinate system - gyro positive angle is CCW
		strafe = forward * sin(rad) + strafe * cos(rad);
		forward  = forward * cos(rad) - strafe * sin(rad);

		//Set motor values
		motorSet(frontLeft, -forward - strafe - rotation);
		motorSet(frontRight,  forward - strafe - rotation);
		motorSet(backLeft, -forward + strafe - rotation);
		motorSet(backRight,  forward + strafe - rotation);
		// Motor values can only be updated every 20ms
		delay(20);
		for(int x=0; x<=10;){
			x++;
			if (motorGet(x)>MOTOR_MAX){
				motorSet(x,MOTOR_MAX);
				delay(20);
			}
			else if (motorGet(x)<MOTOR_MIN){
				motorSet(x,MOTOR_MIN);
				delay(20);
			}
		}
		//Buttons
		bool BTN7U = joystickGetDigital(1,7,JOY_UP);
		bool BTN7D = joystickGetDigital(1,7,JOY_DOWN);
		bool BTN8D = joystickGetDigital(1,8,JOY_DOWN);
		if (BTN8D ==1){
			gyroReset(gyro);
			delay(2000);
		}
		//ELEVATOR
		if (BTN7U == 1){
			motorSet(elevatorLeft,MOTOR_MAX);
			motorSet(elevatorRight,MOTOR_MIN);
		}
		else if (BTN7D == 1){
			motorSet(elevatorLeft,MOTOR_MIN);
			motorSet(elevatorRight,MOTOR_MAX);
		}
	}

}
