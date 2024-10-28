package org.firstinspires.ftc.teamcode.Misc;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Delay {
    private ElapsedTime time;
    public Delay(double millis){
        time = new ElapsedTime();
        time.reset();
        double targetMillis = time.milliseconds() + millis;
        while(time.milliseconds() < targetMillis);
    }

}
