package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController; // this is for the controller

public class TempAuto extends LinearOpMode {

    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private MecanumDrive driveTrain;
    private final double mecanumCircumference = 32, flywheelCircumference = 16;
    private final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}

    private final double TILE_LENGTH = inchesToCm(24), FIELD_LENGTH = 6*TILE_LENGTH;
    @Override
    public void runOpMode() {
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
            driveTrain.driveForwards(2*TILE_LENGTH+2.5);
            driveTrain.rotateCounterClockwise(90);

            driveTrain.driveForwards(4);
            leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()+(int)flywheelTest);
            rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()+(int)flywheelTest);

            driveTrain.rotateCounterClockwise(90);
            driveTrain.driveForwards(TILE_LENGTH);
            driveTrain.rotateCounterClockwise(90);
            driveTrain.driveForwards(2*TILE_LENGTH);

            leftFrontFlywheel.setTargetPosition(leftFrontFlywheel.getCurrentPosition()-(int)flywheelTest);
            rightFrontFlywheel.setTargetPosition(rightFrontFlywheel.getCurrentPosition()-(int)flywheelTest);
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
