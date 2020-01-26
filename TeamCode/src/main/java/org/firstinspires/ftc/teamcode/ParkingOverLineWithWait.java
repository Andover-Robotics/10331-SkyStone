package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//A simple OpMode that parks over the line without the use of an encoder where the back wheels are positioned 2 tiles away.
//(Waits 25 seconds for another team to finish auto before parking)

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Wait & Park", group = "Linear Opmode")

public class ParkingOverLineWithWait extends LinearOpMode {

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
    private final static double FRONT_WHEEL_CIRCUMFERENCE = inchesToCm(3*Math.PI);

    @Override
    public void runOpMode() throws InterruptedException {

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

            sleep(25000);
            drive(TILE_LENGTH, 1);

            break;

        }

    }


    public void drive(double distance, int direction) throws InterruptedException {

        //DIRECTION: 1 = forwards, -1 = backwards

        double num_tiles = distance/TILE_LENGTH;

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive};

        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = motors[i];
            if (i < 2) motor.setPower(-1*direction);
            else motor.setPower(1*direction);
        }

        double tps = 1.8;//tiles per second -- need to test

        //ex: 6 total tiles /
        //3 tiles per second =
        //2 total seconds

        double seconds = num_tiles/tps;
        double millis = seconds*1000;
        Thread.sleep((long)millis);

        for (DcMotor motor : motors) {
            motor.setPower(0);
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

