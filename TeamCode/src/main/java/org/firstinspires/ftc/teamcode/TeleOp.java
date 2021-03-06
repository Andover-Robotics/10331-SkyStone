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

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.andoverrobotics.core.utilities.Coordinate;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


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

//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Tank Drive TeleOp", group = "Iterative Opmode")
//@Disabled
public class TeleOp extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    // boolean to see if Flywheels are running
    private boolean runFlywheelsForward = false, runFlywheelsBackwards = false, stop=false, runServosForward=false, runServosBackward=false;
    //Using ARC-Core's Mecanum Drive class, we initialized a Mecanum Drive as seen below
    private MecanumDrive driveTrain;

    // New servo to move foundation
    private Servo servo;
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
        leftBackFlywheel = hardwareMap.get(DcMotor.class, "leftBackFlywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "rightFrontFlywheel");
        rightBackFlywheel = hardwareMap.get(DcMotor.class, "rightBackFlywheel");

        servo = hardwareMap.get(Servo.class, "servo");

        // Initialize our Mecanum Drive using our motors as parameters
        // Set default power of mecanum drive to 1.0
        driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive,rightFrontDrive,leftBackDrive,rightBackDrive, this, 89, 1120);
        driveTrain.setDefaultDrivePower(1);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        // Flywheels move backwards to move blocks inward
        rightFrontFlywheel.setDirection(DcMotor.Direction.REVERSE);
        rightBackFlywheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackFlywheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontFlywheel.setDirection(DcMotor.Direction.REVERSE);

        leftFrontFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


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
        leftBackFlywheel.setPower(0);
        rightFrontFlywheel.setPower(0);
        rightBackFlywheel.setPower(0);

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    int count = 0;

    @Override
    public void loop() {
        count++;
        telemetry.addLine(((Integer)count).toString());
        telemetry.addData("LF Flywheel Power: ",  leftFrontFlywheel.getPower());
        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad2.left_stick_x;

        // Strafe -- parameters: x and y coordinate to determine direction of movement
        // strafe gives x-direction of movement, drive gives y
        if (strafe < 0) {
            telemetry.addLine("Trying to strafe");
            leftFrontDrive.setPower(-1);
            rightFrontDrive.setPower(-1);
            leftBackDrive.setPower(1);
            rightBackDrive.setPower(1);//not going the correct way
            //driveTrain.setStrafe(strafe, drive);
        }

        else if (strafe > 0) {
            telemetry.addLine("Trying to strafe");
            leftFrontDrive.setPower(1);
            rightFrontDrive.setPower(1);
            leftBackDrive.setPower(-1);
            rightBackDrive.setPower(-1);// not going the correct way
        }

        else {
            leftFrontDrive.setPower(0);
            rightFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightBackDrive.setPower(0);
            driveTrain.setMovementPower(drive);
        }


        // If turn is positive (joystick is pushed to the right), then rotate cw
        // If turn is negative (joystick is pushed to the left), then rotate ccw
        // NOTE: ADJUST FOR SENSITIVITY -- coordinate w/drive team
        if(turn > 0) {
            telemetry.addLine("Trying to turn");
            //driveTrain.rotateClockwise(10,1);
            driveTrain.setRotationPower(1);
        }else if(turn < 0) {
            telemetry.addLine("Trying to turn");
            //driveTrain.rotateCounterClockwise(10,1);
            driveTrain.setRotationPower(-1);
        }else{
            driveTrain.setRotationPower(0);
        }

        //when 'a' button is pressed and front flywheels are not running, set bool to true
        if (gamepad2.a) {
            runFlywheelsBackwards = false;
            runFlywheelsForward = true;
            // when 'a' button is pressed and front flywheels are running, set bool to false
        } //else if (gamepad2.a && runFlywheelsForward) {
            //runFlywheelsForward = false;
        //}

        else if (gamepad2.b) {
            runFlywheelsForward = false;
            runFlywheelsBackwards = true;
        }

        else if (gamepad2.x) {
            runFlywheelsForward = false;
            runFlywheelsBackwards = false;
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
            leftFrontFlywheel.setPower(1);
            rightFrontFlywheel.setPower(-1);
        }else {
            leftFrontFlywheel.setPower(-1);
            rightFrontFlywheel.setPower(1);
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

        if (gamepad1.a) {
            runServosForward = true;
            runServosBackward = false;
        }else if (gamepad1.b) {
            runServosBackward = true;
            runServosForward = false;
        }else if (gamepad1.x) {
            runServosForward = false;
            runServosBackward = false;
        }

        if (!runServosBackward && !runServosForward) {
            servo.setPosition(servo.getPosition() + 90);
        }else if (runServosForward) {
            servo.setPosition(servo.getPosition() - 90);
        }else {
            servo.setPosition(servo.getPosition());
        }


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
