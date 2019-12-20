package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto w/ Sensor", group = "Linear Opmode")
public class ColorSensorAuto extends LinearOpMode {

    private static DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private static DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private static MecanumDrive driveTrain;
    private static final double mecanumCircumference = 32, flywheelCircumference = 16;
    private static final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    //public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final static double TILE_LENGTH = (24), FIELD_LENGTH = 6*TILE_LENGTH;
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

        driveTrain = MecanumDrive.fromCrossedMotors(leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive, this, 89, 1120);
        driveTrain.setDefaultDrivePower(1);

        waitForStart();

        while (opModeIsActive()) {

            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition());
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition());
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition());
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition());
            driveTrain.driveForwards(24);

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
                    driveTrain.driveForwards(8 * (9 - i) + 2.5 * TILE_LENGTH);
                    driveWithSkystone(i-3);
                    driveTrain.driveForwards(2.5* TILE_LENGTH);


                /*
                leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
                rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(4)));
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
            }


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

    public static void driveWithSkystone(int i){
        //strafes to right of the SkyStone in middle of next block
        driveTrain.strafeInches(8, 0);

        //turn 90 degrees counterclockwise to pickup  SkyStone
        driveTrain.rotateCounterClockwise(90);

        //strafe to knock out next block to line up with SkyStone
        driveTrain.strafeInches(4, 0);

        //drive forwards to be close to the SkyStone for intake
        driveTrain.driveForwards(2);

        flywheelIntake();

        //move out of the line of stones
        driveTrain.strafeInches(-8, 0);

        //drive backwards to other side of field
        driveTrain.driveBackwards(8 * (6 - i) + 2.5 * TILE_LENGTH, 1);

        flywheelOuttake();
    }

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }
    public static void flywheelIntake() {
        leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
    }
    public static void flywheelOuttake() {
        leftBackFlywheel.setTargetPosition(leftBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
        rightBackFlywheel.setTargetPosition(rightBackFlywheel.getCurrentPosition()+(int)findTotalTicks(ticksPerFlywheel,flywheelCircumference, 2*flywheelCircumference));
    }
}

