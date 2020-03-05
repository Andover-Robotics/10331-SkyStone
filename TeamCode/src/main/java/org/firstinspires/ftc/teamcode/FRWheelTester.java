/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "FR Wheel Tester", group = "Iterative Opmode")
//@Disabled
public class FRWheelTester extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, rightFrontFlywheel;
    // boolean to see if Flywheels are running
    private boolean runFlywheelsForward = false, runFlywheelsBackwards = false, stop=false, runServosForward=false, runServosBackward=false;
    //Using ARC-Core's Mecanum Drive class, we initialized a Mecanum Drive as seen below
    //private MecanumDrive driveTrain;

    // New servo to move foundation
    private Servo leftServo, rightServo, capstoneServo;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");

        leftFrontFlywheel = hardwareMap.get(DcMotor.class, "leftFrontFlywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "rightFrontFlywheel");

        leftServo = hardwareMap.get(Servo.class, "foundationMoverServoL");
        rightServo = hardwareMap.get(Servo.class, "foundationMoverServoR");
        capstoneServo = hardwareMap.get(Servo.class, "capstoneServo");
        // Initialize our Mecanum Drive using our motors as parameters
        // Set default power of mecanum drive to 1.0
        //driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive,rightFrontDrive,leftBackDrive,rightBackDrive, this, 89, 1120);
        //driveTrain.setDefaultDrivePower(1);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        // Flywheels move backwards to move blocks inward
        rightFrontFlywheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontFlywheel.setDirection(DcMotor.Direction.REVERSE);

        leftFrontFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        leftFrontFlywheel.setPower(0);
        rightFrontFlywheel.setPower(0);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    int count = 0;

    @Override
    public void loop() {
        count++;
        telemetry.addLine(((Integer)count).toString());
        telemetry.addData("RF WHEEL power: ",  rightFrontDrive.getPower());
        telemetry.addData("RT Flywheel Power: ", rightFrontFlywheel.getPower());
        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double driveLeft = gamepad1.left_stick_y;
        double driveRight = gamepad1.right_stick_y;
        //leftFrontDrive.setPower(driveLeft);
        //leftBackDrive.setPower(driveLeft);
        //rightBackDrive.setPower(driveRight);
        rightFrontDrive.setPower(driveRight);

        //when 'a' button is pressed and front flywheels are not running, set bool to true
        if (gamepad2.a) {
            //runFlywheelsForward = true;
            //runFlywheelsBackwards = false;
            // when 'a' button is pressed and front flywheels are running, set bool to false
        } //else if (gamepad2.a && runFlywheelsForward) {
        //runFlywheelsForward = false;
        //}

        else if (gamepad2.b) {
            //runFlywheelsForward = false;
            //runFlywheelsBackwards = true;
        }

        else if (gamepad2.x) {
            //runFlywheelsForward = false;
            //runFlywheelsBackwards = false;
        }


        //capstone
        boolean runCapstone = false;
        if (gamepad2.y) {
//            runCapstone = !runCapstone;
//            if (runCapstone) capstoneServo.setPosition(0.0);
//            else capstoneServo.setPosition(0.5);
        }

        //foundation mover RB is down and LB is up

        if (gamepad2.left_bumper) {
//            runServosForward = false;
//            runServosBackward = true;
        }
        else if (gamepad2.right_bumper) {
//            runServosForward = true;
//            runServosBackward = false;
        }

        //run flywheels at full power when bool true

        /*if (!runFlywheelsBackwards) {
            if (runFlywheelsForward) {
                leftFrontFlywheel.setPower(1);
                rightFrontFlywheel.setPower(1);
            } else {
                leftFrontFlywheel.setPower(0);
                rightFrontFlywheel.setPower(0);
            }
        }*/



        if (!runFlywheelsBackwards && !runFlywheelsForward) {
             leftFrontFlywheel.setPower(0);
            rightFrontFlywheel.setPower(0);
        }else if (runFlywheelsForward) {
            leftFrontFlywheel.setPower(0.5);
            rightFrontFlywheel.setPower(-0.5);
        }else {
            leftFrontFlywheel.setPower(-0.5);
            rightFrontFlywheel.setPower(0.5);
        }

        //when 'b' button is pressed and back flywheels are not running, set bool to true
        /*if (gamepad2.b && !runFlywheelsBackwards && !runFlywheelsForward) {
            runFlywheelsBackwards = true;
            // when 'b' button is pressed and back flywheels are running, set bool to false
        } else if (gamepad2.b && runFlywheelsBackwards) {
            runFlywheelsBackwards = false;
        }*/

        //run flywheels at full power when bool true

        /*if (!runFlywheelsForward) {
            if (runFlywheelsBackwards) {
                leftFrontFlywheel.setPower(-1);
                rightFrontFlywheel.setPower(-1);
            } else {
                leftFrontFlywheel.setPower(0);
                rightFrontFlywheel.setPower(0);
            }
        }*/

        if (gamepad2.dpad_down) {
//            runServosForward = true;
//            runServosBackward = false;
        }else if (gamepad2.dpad_up) {
//            runServosBackward = true;
//            runServosForward = false;
        }else if (gamepad2.dpad_right) {
//            runServosForward = false;
//            runServosBackward = false;
        }

        if (!runServosBackward && !runServosForward) {
            //do nothing??
        }else if (runServosForward) {
            leftServo.setPosition(0.25);
            rightServo.setPosition(0.25);
        }else {
            leftServo.setPosition(0.75);
            rightServo.setPosition(0.75);
        }

        telemetry.addLine("Left Servo: "+ leftServo.getPosition());
        telemetry.addLine("Right Servo: "+ rightServo.getPosition());
        telemetry.addLine("Capstone: " + capstoneServo.getPosition());
        telemetry.update();


        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        /*
        leftBackDrive.setPower(leftbackPower);
        rightBackDrive.setPower(rightbackPower);
        leftFrontDrive.setPower(leftfrontPower);
        rightFrontDrive.setPower(rightfrontPower);
        */

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }
    //test

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }


    //test commit

}
