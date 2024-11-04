package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;


public class lift_subsystem {

    private final DcMotor lift_Motor;
    private int lift_up; // Define the field here
    private int liftUpTicks = 300;//(300 assuming motor is 1680 per rev )tick threshold to determine if slides up and reduce motor power


    public lift_subsystem(HardwareMap hardwareMap){

        lift_Motor = hardwareMap.get(DcMotor.class, "lift_motor");

        lift_Motor.setDirection(DcMotor.Direction.REVERSE);
    }




    public void raise_lift() {
        lift_up = 2; // Set field value
        if(lift_Motor.getCurrentPosition() < liftUpTicks){
            lift_Motor.setPower(1); //max force to raise lift

        }else{
            lift_Motor.setPower(0.5);//reduce force as lift is raised to save power;
        }

    }

    public void lower_lift() {
        lift_up = 0; // Set field value
        lift_Motor.setPower(0); //max_reverse
    }
    public void half_lift() {
        lift_up =1; // Set field value
        lift_Motor.setPower(0.5); //half power
    }

    public int liftIsUp() {
        return lift_up; // Return the field
    }
}
