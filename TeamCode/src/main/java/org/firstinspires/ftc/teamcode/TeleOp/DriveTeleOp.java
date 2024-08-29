package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.ServoTest;

//teleop using externally declared subsystems, drives robot and actuates a servo
@TeleOp(name="DriveTeleOp", group="Iterative OpMode")

public class DriveTeleOp extends OpMode {

    private RemoteDrive tankDrive;

    private ServoTest testServo;

    private ElapsedTime time;

    private double startTime;

    private double endTime;

    public void init() {
        tankDrive = new RemoteDrive(hardwareMap);
        tankDrive.Drive(0,0);

        testServo = new ServoTest(hardwareMap);
        telemetry.addData("Status", "Initialised");
        telemetry.update();
        time = new ElapsedTime();

        startTime = 0;
        endTime = 0;
    }

    /*
    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }
    */

    @Override
    public void loop() {
        
        endTime =  time.milliseconds();
        double frequency = 1 /  ((endTime - startTime) / 1000);
        telemetry.addData("Frequency", frequency + "Hz");

        startTime = time.milliseconds();
        double x  =  gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;

        tankDrive.Drive(x,y);

        if (gamepad1.right_bumper){
            testServo.SetPosition(1);
        }else if (gamepad1.left_bumper){
            testServo.SetPosition(0);
        }



        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Power", "Left: " + tankDrive.GetLeftVelocity() + "Right: " + tankDrive.GetRightVelocity());
        telemetry.addData("Servo Position", testServo.GetPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        tankDrive.NoMoreDrive();
        testServo.SetPosition(0);
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
