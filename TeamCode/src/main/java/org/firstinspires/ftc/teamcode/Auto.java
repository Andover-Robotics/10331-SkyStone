package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorController; // this is for the controller
public class Auto extends LinearOpMode {

    @Override
    public void runOpMode() {
        ColorSensor color_sensor;
        color_sensor = hardwareMap.get(ColorSensor.class,"color_sensor");
         DcMotorController leftFrontDrive;
         DcMotorController rightFrontDrive;
         DcMotorController leftBackDrive;
         DcMotorController rightBackDrive;
         DcMotorController leftFrontFlywheel;
         DcMotorController rightFrontFlywheel;
         DcMotorController leftBackFlywheel;
         DcMotorController rightBackFlywheel;

        int hueVal;
        double alphaVal;
        boolean skyStone = false;
        waitForStart();
        int amtSkyStone = 0;
        //given number of tick (from the specific  motor)  per revolution
        //each encoder is different

        while (opModeIsActive()) {
            hueVal = color_sensor.argb();
            alphaVal = color_sensor.alpha();
            if (hueVal >= 40 && hueVal <= 80) {
                telemetry.addData("Hue: ", "Yellow");


                //test commit (Rishika)
                //test commit (Madeline)
                //test commit (Emily)
            }
            while (amtSkyStone < 2 ) {  // this means it senses the stone
                //senses stone
                if(alphaVal<=0.1) {

                }
                //alpha val is the lightness or darkness .1 is in the middle of white 1 and black 0
                //move the motors specific amount of ticks to sky stone
                skyStone = true;
                //move the fly wheels
                //  stop when eventually reach the amount of ticks needed
                //turn 90 degrees with outtake system facing drop off point
                //move backwards to drop off point
                //reverse fly wheels to exit stone
                //increase count by one
                amtSkyStone++;
                //if count is 2, exit while loop
                //else go back to next stone

                //later


            }




            //dividing line




        }
    }

}
