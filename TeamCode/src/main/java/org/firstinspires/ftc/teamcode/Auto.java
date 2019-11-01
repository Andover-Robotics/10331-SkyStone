package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Auto extends LinearOpMode {

    @Override
    public void runOpMode() {
        ColorSensor color_sensor;
        color_sensor = hardwareMap.get(ColorSensor.class,"color_sensor");

        int hueVal;
        double alphaVal;
        boolean skyStone = false;
        waitForStart();

        while (opModeIsActive()) {
            hueVal = color_sensor.argb();
            alphaVal = color_sensor.alpha();
            if (hueVal >= 40 && hueVal <= 80) {
                telemetry.addData("Hue: ", "Yellow");


                //test commit (Rishika)
                //test commit (Madeline)
                //test commit (Emily)
            }
            if (alphaVal <= 0.1) {
                skyStone = true;
            }



            //dividing line




        }
    }

}
