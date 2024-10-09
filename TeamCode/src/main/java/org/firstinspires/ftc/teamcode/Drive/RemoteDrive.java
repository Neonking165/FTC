package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

//remote drive class for remote control of drive train in teleop
public class RemoteDrive {

    //private HardwareMap hardwareMap;
    private DcMotor leftDrive;
    private DcMotor rightDrive;


    private double maximumSpeed = 0.85;//dont put to 1 please, otherwise robot will drift

    private double leftTargetVelocity;
    private double rightTargetVelocity;

    public RemoteDrive(HardwareMap hardwareMap){

        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        MotorConfigurationType motorConfigurationType = leftDrive.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);

        leftDrive.setMotorType(motorConfigurationType);
        rightDrive.setMotorType(motorConfigurationType);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        Drive(0,0);
    }

    public void setMode(DcMotor.RunMode runMode) {
        leftDrive.setMode(runMode);
        rightDrive.setMode(runMode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
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
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Drive(0,0);
    }

    public double GetLeftVelocity(){
        return leftDrive.getPower();
    }

    public double GetRightVelocity(){
        return rightDrive.getPower();
    }
}
