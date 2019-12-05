package org.firstinspires.ftc.teamcode;

import com.andoverrobotics.core.drivetrain.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ColorSensorAuto extends LinearOpMode {

    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
    //NEW FLYWHEELS
    private DcMotor leftFrontFlywheel, leftBackFlywheel, rightFrontFlywheel, rightBackFlywheel;
    private MecanumDrive driveTrain;
    private final double mecanumCircumference = 32, flywheelCircumference = 16;
    private final int ticksPerMecanum = 1120, ticksPerFlywheel = 538;
    public enum SkyStoneStatus {NO_STONE, STONE, SKYSTONE}
    ElapsedTime runtime = new ElapsedTime();

    private final double TILE_LENGTH = inchesToCm(24), FIELD_LENGTH = 6*TILE_LENGTH;
    @Override
    public void runOpMode() {
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
        while(opModeIsActive()) {
            leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));
            rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + (int) findTotalTicks(ticksPerMecanum, mecanumCircumference, inchesToCm(24)));

          for(int i=0;i<3;i++) {
            if(color_sensor.alpha()<=0.2){
             //pick up the block

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


        }
    }
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

    private static double inchesToCm(double inches) {
        return inches*2.54;
    }
}
