package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class lift_subsystem {
    private final Servo lift_servo;
    private boolean lift_open; // Define the field here

    public lift_subsystem(HardwareMap hardwareMap) {
        lift_servo = hardwareMap.get(Servo.class, "lift_servo");
        lift_servo.setPosition(0);
        lift_open = false; // Initialize the field
    }

    public void SetPpeed(float position) {
        lift_servo.setSpeed(Range.clip(position, 0, 1));
    }

    public double GetPosition() {
        return lift_servo.getPosition();
    }

    public void open_lift() {
        lift_servo.setPosition(0.5);
        lift_open = true; // Set field value
    }

    public void close_lift() {
        lift_servo.setPosition(0);
        lift_open = false; // Set field value
    }

    public boolean liftIsOpen() {
        return lift_open; // Return the field
    }
}
