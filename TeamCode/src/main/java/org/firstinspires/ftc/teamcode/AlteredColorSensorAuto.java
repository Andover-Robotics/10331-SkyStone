package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Tank Drive Autonomous", group = "Linear Opmode")
public class AlteredColorSensorAuto extends LinearOpMode {

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

        //driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive, this, 89, 1120);
        //driveTrain.setDefaultDrivePower(1);

        waitForStart();

        while (opModeIsActive()) {

            leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition());
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition());
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition());
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition());
            drive(24);

            /*
            replace below code with driveTrain

            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));

            */

            // move forward in order to sense blocks


            for (int i = 6; i > 3; i--) {
                if (color_sensor.alpha() <= 0.2) {

                    driveWithSkystone(i);

                    //drive to the next SkyStone
                    drive(8 * (9 - i) + 2.5 * TILE_LENGTH);
                    driveWithSkystone(i-3);
                    drive(2.5* TILE_LENGTH);


                /*
                leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                rightBackDrive.setTargetPositioaqqqqqqqqqqqqqqqqqn(rightBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(5)));
                leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(5)));
                rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(5)));
                rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(5)));
                */

                /*
                turn 90 degrees counterclockwise to pickup skystone:

                leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(2.5)));
                leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(2.5)));
                rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(2.5)));
                rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(2.5)));
                */

                }
                else {
                    //TODO: change this to turning and moving
                    //driveTrain.strafeInches(8, 0);
                    turn(90, -1*ALLIANCE);//left turn if on right side, vice versa
                    drive(8);
                }

            }

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
                motor.setPower(0.75);
            }else {
                motor.setPower(1);
            }
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (leftFrontDrive.isBusy() || leftBackDrive.isBusy() || rightFrontDrive.isBusy() || rightBackDrive.isBusy()) {
            //does nothing -- this is effectively the sleep function but based on a conditional
        }
    }


       /* runtime.reset();

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

        boolean skyStone = false;
        boolean parkedOverLine = false;
        double stoneLength = 20;
        SkyStoneStatus status = SkyStoneStatus.NO_STONE;
        SkyStoneStatus[] statusArr = new SkyStoneStatus[6];
        waitForStart();
        int amtSkyStone = 0;
        int amtStone = 6;
        double distanceToStone=120;
        //given number of tick (from the specific  motor)  per revolution
        //each encoder is different

        double flywheel = 15.95;

        double mecanumTest = findTotalTicks(ticksPerMecanum, mecanumCircumference, 20);
        double flywheelTest = findTotalTicks(ticksPerFlywheel, flywheelCircumference, 20);
        double ticksToStone = findTotalTicks(ticksPerMecanum, mecanumCircumference, distanceToStone);

        while (opModeIsActive()) {
            driveTrain.driveForwards(TILE_LENGTH+2.5);
            //drive to start position to be the right distance away from the first stone

            int block = 0;
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

    public static void turn (double degrees, int direction) {
        //direction = 1 to turn left, -1 to turn right

        //creates array of motors and loops through for efficiency
        DcMotor[] motors = {leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive};

        //you actually need to set the mode to stop_and_reset_encoder BEFORE setting target position
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        int distance = 42;//TODO: figure out this distance value

        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(distance)));

        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = motors[i];
            if (i < 2) motor.setPower(direction);
            //which motor is 1 and which is -1 depends on direction param (i < 2 indicates it's a left motor)
            else motor.setPower(-1*direction);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (leftFrontDrive.isBusy() || leftBackDrive.isBusy() || rightFrontDrive.isBusy() || rightBackDrive.isBusy()) {
            //does nothing -- this is effectively the sleep function but based on a conditional
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

    public static void driveWithSkystone(int i) throws InterruptedException {
        //strafes to right of the SkyStone in middle of next block
        turn(90, 1);
        drive(8);

        //turn 90 degrees counterclockwise to pickup  SkyStone (clockwise if on left side)
        //TODO: change this to actual turning
        //driveTrain.rotateCounterClockwise(90);
        turn(90, 1*ALLIANCE);//is this actually necessary?

        //turn & move to knock out next block to line up with SkyStone
        //TODO: change this to actual turning and moving
        //driveTrain.strafeInches(4, 0);
        turn(90, -1*ALLIANCE);//is this necessary (it is if the top one is there, but that doesn't look like an option)
        drive(4);
        turn(90, 1*ALLIANCE);

        //drive forwards to be close to the SkyStone for intake
        drive(2);

        flywheelIntake();

        //move out of the line of stones
        //TODO: change this to actual turning and moving
        //driveTrain.strafeInches(-8, 0);
        turn(90, 1*ALLIANCE);
        drive(8);
        turn(90, -1*ALLIANCE);

        //drive backwards to other side of field
        drive(-8 * (6 - i) + 2.5 * TILE_LENGTH);

        flywheelOuttake();
    }

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }
    public static void flywheelIntake() throws InterruptedException {
        leftFrontFlywheel.setPower(1);
        rightFrontFlywheel.setPower(1);
        Thread.sleep(2000);
        //leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        //rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));

    }
    public static void flywheelOuttake() throws InterruptedException {
        leftFrontFlywheel.setPower(-1);
        rightFrontFlywheel.setPower(-1);
        Thread.sleep(2000);
        //leftBackFlywheel.setTargetPosition(leftBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        //rightBackFlywheel.setTargetPosition(rightBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
    }
}

