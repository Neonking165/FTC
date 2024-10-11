package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class lift_subsystem {
    private final DcMotor lift_Motor;
    private int lift_up; // Define the field here

    private int fineTuneTPS = 100;


    public lift_subsystem(HardwareMap hardwareMap){

        lift_Motor = hardwareMap.get(DcMotor.class, "lift_motor");

        lift_Motor.setDirection(DcMotor.Direction.REVERSE);

        lift_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lift_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lift_Motor.setPower(maxSpeed);
        lift_Motor.setTargetPosition(0);

    }



    public void raise_lift() {
        lift_up = 2; // Set field value
    }

    public void lower_lift() {
        lift_up = 0; // Set field value
    }
    public void half_lift() {
        lift_up =1; // Set field value
    }

    public int liftIsUp() {
        return lift_up; // Return the field
    }
}
