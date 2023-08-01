package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;

import org.firstinspires.ftc.teamcode.Robots.BasicRobot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.RFMecanumDrive;

import java.util.ArrayList;

@TeleOp(name = "ButterflyTest")
public class ButterflyTest extends LinearOpMode {
    BasicRobot robot;
    RFMecanumDrive drive;
    boolean isButtered = false;
    double lastSwitchTime = 0;
    double BUTTERED_POSITION = 0.6;
    double INIT_POSITION = 1.0;
    double [] offset = {0.005,-0.005,0.01,0.02 };
    ArrayList<Servo> servos;
    public void runOpMode(){
        robot = new BasicRobot(this,true);
        drive = new RFMecanumDrive();
        servos = new ArrayList<>();
        servos.add(hardwareMap.servo.get("servoLeftFront"));
        servos.add(hardwareMap.servo.get("servoLeftBack"));
        servos.add(hardwareMap.servo.get("servoRightFront"));
        servos.add(hardwareMap.servo.get("servoRightBack"));
        toggleServos();
        waitForStart();
        while(opModeIsActive()){
            if(!isButtered){
                drive.setJoystickPower(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            }else{
                double y = gamepad1.left_stick_y;
                double a = -gamepad1.right_stick_x;
                telemetry.addData("rightStckX", a);
                double[] powers = {y+a,y+a,y-a,y-a};
                drive.setMotorPowers(powers);
            }
            if(time>lastSwitchTime+1.0 && gamepad1.a){
                isButtered=!isButtered;
                toggleServos();
                lastSwitchTime=time;
            }
            robot.update();
        }
    }
    public void toggleServos(){
        if(isButtered){
            for(int i=0;i<4;i++){
                servos.get(i).setPosition(BUTTERED_POSITION+offset[i]);
            }
        }
        else{
            for(int i=0;i<4;i++){
                servos.get(i).setPosition(INIT_POSITION);
            }
        }
    }
}
