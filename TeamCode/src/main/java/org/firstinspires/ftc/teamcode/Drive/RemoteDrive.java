package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

//remote drive class for remote control of drive train in teleop
public class RemoteDrive {

    //private HardwareMap hardwareMap;
    private DcMotorEx leftDrive;
    private DcMotorEx rightDrive;


    private double maximumSpeed = 1;//dont put to 1 please, otherwise robot will drift

    private double leftTargetVelocity;
    private double rightTargetVelocity;

    public RemoteDrive(HardwareMap hardwareMap){

        leftDrive = hardwareMap.get(DcMotorEx.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotorEx.class, "right_drive");

        leftDrive.setDirection(DcMotorEx.Direction.FORWARD);
        rightDrive.setDirection(DcMotorEx.Direction.REVERSE);

        MotorConfigurationType motorConfigurationType = leftDrive.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);

        leftDrive.setMotorType(motorConfigurationType);
        rightDrive.setMotorType(motorConfigurationType);

        setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);


        Drive(0,0);
    }

    public void setMode(DcMotorEx.RunMode runMode) {
        leftDrive.setMode(runMode);
        rightDrive.setMode(runMode);
    }

    public void setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior zeroPowerBehavior) {
        leftDrive.setZeroPowerBehavior(zeroPowerBehavior);
        rightDrive.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void Drive(double x, double y) {
        x = Range.clip(x, -1, 1);
        y = Range.clip(y, -1, 1);

        double leftVelocity = y + x;
        double rightVelocity = y - x;

        leftVelocity = Range.clip(leftVelocity * maximumSpeed, -maximumSpeed, maximumSpeed);
        rightVelocity = Range.clip(rightVelocity * maximumSpeed, -maximumSpeed, maximumSpeed);

        leftDrive.setPower(leftVelocity);
        rightDrive.setPower(rightVelocity);

        leftTargetVelocity = leftVelocity;
        rightTargetVelocity = rightVelocity;

    }

    public void NoMoreDrive(){
        setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        Drive(0,0);
    }


    public double GetLeftVelocity(){
        return leftDrive.getPower();
    }

    public double GetRightVelocity(){
        return rightDrive.getPower();
    }
}
