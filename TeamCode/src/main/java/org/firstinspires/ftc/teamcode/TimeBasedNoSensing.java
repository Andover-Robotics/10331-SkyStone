package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "2 Stones Auto", group = "Linear Opmode")
public class TimeBasedNoSensing extends LinearOpMode {

    private static DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private static DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private static MecanumDrive driveTrain;
    private static final double mecanumCircumference = 32, flywheelCircumference = 16;
    private static final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    //public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final static double TILE_LENGTH = 24, FIELD_LENGTH = 6*TILE_LENGTH;
    private final static int ALLIANCE = 1;//1 means on right side of field, -1 means left

    @Override
    public void runOpMode() throws InterruptedException {

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

        waitForStart();

        while (opModeIsActive()) {

            //Simplified version that just gets the nearest stone and its counterpart.
            // 2/3 chance of getting one skystone

            drive(TILE_LENGTH*1.5, 1);

            int i = 6;
            driveWithSkystone(i);
            drive(8 * (9 - i) + 2.5 * TILE_LENGTH, 1);
            driveWithSkystone(i-4);
            drive(2.5* TILE_LENGTH, 1);
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

    public void turn (double degrees, int direction) throws InterruptedException {
        //direction = 1 to turn ccw, -1 to turn cw

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive};

        for (DcMotor motor : motors) {
            motor.setPower(direction*0.5);
        }

        double dps = 100*113/90;//degrees per second

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

    public void driveWithSkystone(int i) throws InterruptedException {
        //strafes to right of the SkyStone in middle of next block
        turn(90, 1*ALLIANCE);
        drive(8, 1);

        //turn 90 degrees counterclockwise to pickup  SkyStone (clockwise if on left side)
        //driveTrain.rotateCounterClockwise(90);
        turn(90, -1*ALLIANCE);//is this actually necessary?

        //turn & move to knock out next block to line up with SkyStone
        //driveTrain.strafeInches(4, 0);
        //turn(90, -1*ALLIANCE);//is this necessary (it is if the top one is there, but that doesn't look like an option)
        drive(4, 1);
        turn(90, 1*ALLIANCE);

        //drive forwards to be close to the SkyStone for intake
        drive(2, 1);

        flywheelIntake();

        //move out of the line of stones
        //driveTrain.strafeInches(-8, 0);
        turn(90, -1*ALLIANCE);
        drive(8, -1);
        turn(90, 1*ALLIANCE);

        //drive backwards to other side of field
        //you lost the game ^>^
        drive(-8 * (6 - i) + 2.5 * TILE_LENGTH, 1);

        flywheelOuttake();
    }

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }
    public void flywheelIntake() throws InterruptedException {
        leftFrontFlywheel.setPower(1);
        rightFrontFlywheel.setPower(1);
        drive(3, 1);
        leftFrontFlywheel.setPower(0);
        rightFrontFlywheel.setPower(0);
        //leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        //rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));

    }
    public void flywheelOuttake() throws InterruptedException {
        leftFrontFlywheel.setPower(-1);
        rightFrontFlywheel.setPower(-1);
        drive(3, 1);
        leftFrontFlywheel.setPower(0);
        rightFrontFlywheel.setPower(0);
        //leftBackFlywheel.setTargetPosition(leftBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        //rightBackFlywheel.setTargetPosition(rightBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
    }
}

