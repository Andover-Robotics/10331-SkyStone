package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

//this class exists to change the ticks. in the other class, the mode on the encoders is set to run_with_encoder,
//but we need it in run_to_position mode such that it stops.this means we need to calculate the number of ticks
//correctly for both the 40 and 60 motors.

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Parking Over Line With Stopping", group = "Linear Opmode")

public class ParkingOverLineModified extends LinearOpMode {

    private static DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private static DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    //private static MecanumDrive driveTrain;
    private static final double mecanumCircumference = 32, flywheelCircumference = 16;
    private static final int ticksPer40 = 1120, ticksPer60 = 1680;
    //public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final static double TILE_LENGTH = (24), FIELD_LENGTH = 6*TILE_LENGTH;
    private final static int ALLIANCE = 1;//1 means on right side of field, -1 means left
    private final static double BACK_WHEEL_CIRCUMFERENCE = inchesToCm(4*Math.PI);
    private final static double FRONT_WHEEL_CIRCUMFERENCE = inchesToCm(4*Math.PI);

    @Override
    public void runOpMode() {

        runtime.reset();

        telemetry.addLine("This started");
        ColorSensor color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftFrontFlywheel = hardwareMap.get(DcMotor.class, "leftFrontFlywheel");
        leftBackFlywheel = hardwareMap.get(DcMotor.class, "leftBackFlywheel");
        rightFrontFlywheel = hardwareMap.get(DcMotor.class, "rightFrontFlywheel");
        rightBackFlywheel = hardwareMap.get(DcMotor.class, "rightBackFlywheel");

        //driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive, this, 89, 1120);
        //driveTrain.setDefaultDrivePower(1);

        waitForStart();

        while (opModeIsActive()) {

            leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

//            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition());
//            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition());
//            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition());
//            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition());
            this.drive(6);

            break;

        }

    }


    public void drive(double distance){
        //< 2 is front, >=2 is back
        //odd #s are right, even #s are left

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive};

        //you actually need to set the mode to stop_and_reset_encoder BEFORE setting target position
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPer40, FRONT_WHEEL_CIRCUMFERENCE, inchesToCm(distance)));
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPer40, BACK_WHEEL_CIRCUMFERENCE, inchesToCm(distance)));
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPer40, FRONT_WHEEL_CIRCUMFERENCE, inchesToCm(distance)));
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPer40, BACK_WHEEL_CIRCUMFERENCE, inchesToCm(distance)));

        //then after setting position, you ALSO set power AND set mode to run to position
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(1);
        }

        //this conditional is an OR in this version because the LF wheel will eventually catch itself up,
        //meaning it might have a wiggling effect but will eventually end up aligned
        while (/*leftFrontDrive.isBusy() || leftBackDrive.isBusy() ||*/ rightFrontDrive.isBusy() /*|| rightBackDrive.isBusy()*/) {
            telemetry.addLine("This is busy");
            for (DcMotor motor : motors) {
                //Telemetry debug (thanks Michael)
                telemetry.addData("motor currPos -> targetPos", "%d -> %d", motor.getCurrentPosition(), motor.getTargetPosition());
                telemetry.addData("mode & isBusy?", "%s & %s", motor.getMode(), motor.isBusy() ? "yes" : "no");
            }
            if (Math.abs(rightFrontDrive.getCurrentPosition()) > Math.abs(rightFrontDrive.getTargetPosition())) break;
            telemetry.update();
            idle();
        }

        for (DcMotor motor : motors) {
            motor.setPower(0);
            //motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

