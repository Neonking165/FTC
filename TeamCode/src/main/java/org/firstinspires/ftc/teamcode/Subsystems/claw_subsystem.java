package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class claw_subsystem {
    private final Servo claw_servo;
    private boolean claw_open; // Define the field here

    public claw_subsystem(HardwareMap hardwareMap) {
        claw_servo = hardwareMap.get(Servo.class, "claw_servo");
        claw_servo.setPosition(0);
        claw_open = false; // Initialize the field
    }

    public void SetPosition(double position) {
        claw_servo.setPosition(Range.clip(position, 0, 1));
    }

    public double GetPosition() {
        return claw_servo.getPosition();
    }

    public void open_claw() {
        claw_servo.setPosition(0.5);
        claw_open = true; // Set field value
    }

    public void close_claw() {
        claw_servo.setPosition(0);
        claw_open = false; // Set field value
    }

    public boolean clawIsOpen() {
        return claw_open; // Return the field
    }
}
