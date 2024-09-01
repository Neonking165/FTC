package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.Drive.PositionDrive;

//test auto make little manouver to test repeatability
@Autonomous(name="TestAuto", group="Iterative OpMode")
public class AutoTest extends OpMode {

    private PositionDrive tankDrive;

    public void init() {
        tankDrive = new PositionDrive(hardwareMap);
    }

    @Override
    public void start() {
        tankDrive.DriveCm(20,20, true);
        tankDrive.DriveCm(30,-30, true);
        tankDrive.DriveCm(10,10, true);
        tankDrive.DriveCm(-10,-10, true);
        tankDrive.DriveCm(-30,30, true);
        tankDrive.DriveCm(-20,-20, true);
        tankDrive.Brake();
    }
    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}
