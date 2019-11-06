package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController; // this is for the controller

public class Auto extends LinearOpMode {

    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private MecanumDrive driveTrain;
    @Override
    public void runOpMode() {
        ColorSensor color_sensor;
        color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        leftFrontFlywheel = hardwareMap.get(DcMotor.class, "left_front_flywheel");
        leftBackFlywheel = hardwareMap.get(DcMotor.class, "left_back_flywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "right_front_flywheel");
        rightBackFlywheel = hardwareMap.get(DcMotor.class, "right_back_flywheel");

        driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive,rightFrontDrive,leftBackDrive,rightBackDrive, this, 89, 1120);
        driveTrain.setDefaultDrivePower(1);

        int hueVal;
        double alphaVal;
        boolean skyStone = false;
        boolean parkedOverLine = false;
        double stoneLength = 20;
        double distAcrossField;
        waitForStart();
        int amtSkyStone = 0;
        double distanceToStone=20;
        //given number of tick (from the specific  motor)  per revolution
        //each encoder is different

        double flywheel = 15.95;

        double mecanumTest = findTotalTicks(1120, 32, 20);
        double flywheelTest = findTotalTicks(538, 16, 20);
        double ticksToStone = findTotalTicks(1120, 32, distanceToStone);

        while (opModeIsActive()) {
            hueVal = color_sensor.argb();
            alphaVal = color_sensor.alpha();
            if (hueVal >= 40 && hueVal <= 80) {
                telemetry.addData("Hue: ", "Yellow");


                //test commit (Rishika)
                //test commit (Madeline)
                //test commit (Emily)
            }
            while (amtSkyStone < 2) {  // this means it senses the stone
                //senses stone
                while (!skyStone){
                    if (alphaVal <= 0.1) {
                        skyStone = true;

                    }
                }

                leftFrontDrive.setTargetPosition((int) ticksToStone);
                leftBackDrive.setTargetPosition((int) ticksToStone);
                rightFrontDrive.setTargetPosition((int) ticksToStone);
                rightBackDrive.setTargetPosition((int) ticksToStone);

                leftFrontFlywheel.setTargetPosition((int)flywheelTest);
                leftBackFlywheel.setTargetPosition((int)flywheelTest);
                rightFrontFlywheel.setTargetPosition((int)flywheelTest);
                rightBackFlywheel.setTargetPosition((int)flywheelTest);

                driveTrain.rotateCounterClockwise(90);


                //alpha val is the lightness or darkness .1 is in the middle of white 1 and black 0
                //move the motors specific amount of ticks to sky stone
                //move the fly wheels
                //  stop when eventually reach the amount of ticks needed
                //turn 90 degrees with outtake system facing drop off point
                //move backwards to drop off point
                //reverse fly wheels to exit stone
                //increase count by one
                //if count is 2, exit while loop
                //else go back to next stone

                //later



            }


            //dividing line

            while (!parkedOverLine) {
                leftFrontDrive.setTargetPosition((int) mecanumTest);
                leftBackDrive.setTargetPosition((int) mecanumTest);
                rightFrontDrive.setTargetPosition((int) mecanumTest);
                rightBackDrive.setTargetPosition((int) mecanumTest);
                //if we need to turn, set the left wheels to negative
            }
        }
    }

    public static double findTotalTicks(int ticksPerRev, double circumference, double intendedDist) {


        double amtOfWheelUsed = intendedDist / circumference;

        double degrees = amtOfWheelUsed * 360;

        double finalTicks = degrees * ticksPerRev / 360;

        /*

        225/360 = x/1120
        x = 700

         */

        return finalTicks;
    }


}
