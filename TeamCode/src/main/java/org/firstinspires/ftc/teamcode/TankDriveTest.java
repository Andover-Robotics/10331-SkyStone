package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.TankDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Tank Drive Test", group = "Linear Opmode")
public class TankDriveTest extends LinearOpMode {

    private static DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private static DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private static TankDrive driveTrain;
    private static final double mecanumCircumference = 32, flywheelCircumference = 16;
    private static final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    //public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final static double TILE_LENGTH = (24), FIELD_LENGTH = 6*TILE_LENGTH;
    private final static int ALLIANCE = 1;//1 means on right side of field, -1 means left

    @Override
    public void runOpMode() {

        runtime.reset();

        telemetry.addLine("This started");
        ColorSensor color_sensor;
        color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftFrontFlywheel = hardwareMap.get(DcMotor.class, "leftFrontFlywheel");
        leftBackFlywheel = hardwareMap.get(DcMotor.class, "leftBackFlywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "rightFrontFlywheel");
        rightBackFlywheel = hardwareMap.get(DcMotor.class, "rightBackFlywheel");

        driveTrain = TankDrive.fromMotors(leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive, this, 35, 1120);
        driveTrain.setDefaultDrivePower(1);

        waitForStart();

        while (opModeIsActive()) {
            leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            /*
            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition());
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition());
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition());
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition());*/
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            driveTrain.driveForwards(12, 1);
        }

    }



    public static void drive(double distance){

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive};

        //you actually need to set the mode to stop_and_reset_encoder BEFORE setting target position
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));

        //then after setting position, you ALSO set power AND set mode to run to position
        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = motors[i];
            if (i % 2 == 1) {
                motor.setPower(0.25);
            }else {
                motor.setPower(1);
            }
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        while (leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy()) {
            //does nothing -- this is effectively the sleep function but based on a conditional
        }

        for (DcMotor motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

