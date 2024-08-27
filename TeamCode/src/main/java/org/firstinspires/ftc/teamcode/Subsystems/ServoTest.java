package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.hardware.HardwareMap;

//test servo subsystem with set position and position retrieval
public class ServoTest {
    private Servo testServo;
    private double targetPosition;
    public ServoTest(HardwareMap hardwareMap){
        testServo = hardwareMap.servo.get("testServo");
        testServo.setPosition(0);
        targetPosition = 0;
    }
    public void SetPosition(double position){
        testServo.setPosition(Range.clip(position, 0, 1));
        targetPosition = position;
    }
    public double GetPosition(){
        return targetPosition;
    }
}
