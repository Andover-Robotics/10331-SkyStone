package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController; // this is for the controller
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Parking Auto", group = "Linear Opmode")

public class ParkAuto extends LinearOpMode {

    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private MecanumDrive driveTrain;
    private final double flywheelCircumference = 16;
    private final int ticksPerFlywheel = 538;
    ElapsedTime runtime = new ElapsedTime();

    private final double TILE_LENGTH = inchesToCm(24), FIELD_LENGTH = 6*TILE_LENGTH;
    @Override
    public void runOpMode() {

        runtime.reset();

        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");

        leftFrontFlywheel = hardwareMap.get(DcMotor.class, "leftFrontFlywheel");
        leftBackFlywheel = hardwareMap.get(DcMotor.class, "leftBackFlywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "rightFrontFlywheel");
        rightBackFlywheel = hardwareMap.get(DcMotor.class, "rightBackFlywheel");

        driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive,rightFrontDrive,leftBackDrive,rightBackDrive, this, 89, 1120);
        driveTrain.setDefaultDrivePower(1);

        waitForStart();
        //given number of tick (from the specific  motor)  per revolution
        //each encoder is different

        double flywheelTest = findTotalTicks(ticksPerFlywheel, flywheelCircumference, 20);

        while (opModeIsActive()) {
            /*driveTrain.driveForwards(TILE_LENGTH+2.5);
            //drive to start position to be the right distance away from the first stone

            int block;
            for(block = 0; block < 6 && runtime.seconds() < 25; block++) {
                driveTrain.driveForwards(TILE_LENGTH);
                driveTrain.rotateCounterClockwise(90);

                driveTrain.driveForwards(4); // test val of 4 ??
                //the calibration based on which iteration we're on is done at the bottom of the loop
                leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()+(int)flywheelTest);
                rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()+(int)flywheelTest);
                //pick up the next stone

                driveTrain.rotateCounterClockwise(90);
                driveTrain.driveForwards(TILE_LENGTH);
                driveTrain.rotateCounterClockwise(90);
                driveTrain.driveForwards(2*TILE_LENGTH + 8*block);
                //head over to the building zone

                leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()-(int)flywheelTest);
                rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()-(int)flywheelTest);
                //outtake stone

                driveTrain.driveBackwards(2*TILE_LENGTH + 8*(block+1));
                //drive back to the next stone (an extra block distance for every iteration)

                driveTrain.rotateCounterClockwise(90);
            }

            // park over line
            driveTrain.rotateClockwise(90);
            // calibrate spec. val of constant to add
            driveTrain.driveForwards(8*(block+1)+TILE_LENGTH);
             */

            //driveTrain.driveForwards(TILE_LENGTH);
            //driveTrain.strafeLeft(TILE_LENGTH);

            //int TILE_LENGTH_INT = (int)TILE_LENGTH;


            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int)findTotalTicks(1120, 32, TILE_LENGTH));
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));

            driveTrain.setRotationPower(1);
            sleep(500);

            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int)findTotalTicks(1120, 32, TILE_LENGTH));
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() +(int)findTotalTicks(1120, 32, TILE_LENGTH));

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

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }
}
