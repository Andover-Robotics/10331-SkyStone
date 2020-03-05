package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Foundation Moving Auto", group = "Linear Opmode")
public class FoundationMovingAuto extends LinearOpMode {

    private static DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private static DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private static MecanumDrive driveTrain;
    private static final double mecanumCircumference = 32, flywheelCircumference = 16;
    private static final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    //public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final static double TILE_LENGTH = (24), FIELD_LENGTH = 6*TILE_LENGTH;
    private final static int ALLIANCE = 1;//1 means on right side of field, -1 means left
    // New servo to move foundation
    private Servo leftServo, rightServo, capstoneServo;

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
        /*
         * drive backward 32 inches
         * put down foundation movers
         * move forward 12inches (test with TeleOp)
         * turn foundation (and robot!) 90 degrees
         * drive backward to park on line
         * */

        while (opModeIsActive()) {

            drive(32,-1);
            leftServo.setPosition(0.25);
            rightServo.setPosition(0.25);
            drive(12,1);
            turn(90,ALLIANCE*-1);
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
    public void turn (double degrees, int direction) throws InterruptedException {
        //direction = 1 to turn ccw, -1 to turn cw

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive};

        for (DcMotor motor : motors) {
            motor.setPower(direction*-0.5);
        }

        double dps = 97*113/90*77/90;//degrees per second

        //ex: 60 total degrees /
        //30 degrees per second =
        //2 total seconds

        double seconds = degrees/dps;
        double millis = seconds*1000;
        Thread.sleep((long)millis);

        for (DcMotor motor : motors) {
            motor.setPower(0);
        }

    }

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }

}
