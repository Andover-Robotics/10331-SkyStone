package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Test extends LinearOpMode {
    //Really basic impl. of color sensor -- not an algorithmic implementation by any means
    @Override
    public void runOpMode() {
        ColorSensor test;
        test = hardwareMap.get(ColorSensor.class,"color_sensor");

        int hueVal;
        waitForStart();

        while (opModeIsActive()) {
            hueVal = test.argb();
            if (hueVal <= 40 && hueVal >= 80) {
                telemetry.addData("Hue: ", hueVal);

                //test commit (Rishika)
            }
        }

    }

}
