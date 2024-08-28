package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Misc.Delay;
import org.opencv.core.Mat;

//motor position based drive class for repeatable autonomy
public class PositionDrive {

    //private HardwareMap hardwareMap;
    private DcMotor leftDrive;
    private DcMotor rightDrive;


    private double maximumSpeed = 0.65;//keep low to reduce acceleration and therefore tyre slip
    private double gearRatio = 15.1147;//x:1
    private double wheelDiameter = 9;//cm
    private double ticksPerMotorRevolution = 28;

    private double TicksPerCentimeter;

    public PositionDrive(HardwareMap hardwareMap){

        DcMotor leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        MotorConfigurationType motorConfigurationType = leftDrive.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        leftDrive.setMotorType(motorConfigurationType);
        rightDrive.setMotorType(motorConfigurationType);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double circumference = wheelDiameter * Math.PI;
        double TicksPerRevolution = ticksPerMotorRevolution * gearRatio;

        TicksPerCentimeter = TicksPerRevolution / circumference;

        SetPositionTicks(0,0);
    }

    public void setMode(DcMotor.RunMode runMode) {
        leftDrive.setMode(runMode);
        rightDrive.setMode(runMode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        leftDrive.setZeroPowerBehavior(zeroPowerBehavior);
        rightDrive.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void DriveCm(double leftDistanceCm, double rightDistanceCm, boolean blocking) {

        leftDrive.setPower(maximumSpeed);
        rightDrive.setPower(maximumSpeed);

        int tickChangeLeft = (int)Math.round(leftDistanceCm * TicksPerCentimeter);
        int tickChangeRight = (int)Math.round(rightDistanceCm * TicksPerCentimeter);

        int leftTargetPosition = leftDrive.getCurrentPosition() + tickChangeLeft;
        int rightTargetPosition = rightDrive.getCurrentPosition() + tickChangeRight;

        leftDrive.setTargetPosition(leftTargetPosition);
        rightDrive.setTargetPosition(rightTargetPosition);

        if (blocking){
            boolean rangeL = (leftDrive.getCurrentPosition() > leftDrive.getTargetPosition() - 5) && (leftDrive.getCurrentPosition() < leftDrive.getTargetPosition() + 5);
            boolean rangeR = (rightDrive.getCurrentPosition() > rightDrive.getTargetPosition() - 5) && (rightDrive.getCurrentPosition() < rightDrive.getTargetPosition() + 5);
            while(!rangeL && !rangeR){
                rangeL = (leftDrive.getCurrentPosition() > leftDrive.getTargetPosition() - 5) && (leftDrive.getCurrentPosition() < leftDrive.getTargetPosition() + 5);
                rangeR = (rightDrive.getCurrentPosition() > rightDrive.getTargetPosition() - 5) && (rightDrive.getCurrentPosition() < rightDrive.getTargetPosition() + 5);
                new Delay(1);
            }
            new Delay(100);
        }
    }

    public void DriveTicks(int leftTicks, int rightTicks) {

        leftDrive.setPower(maximumSpeed);
        rightDrive.setPower(maximumSpeed);

        int leftTargetPosition = leftDrive.getCurrentPosition() + leftTicks;
        int rightTargetPosition = rightDrive.getCurrentPosition() + rightTicks;

        leftDrive.setTargetPosition(leftTargetPosition);
        rightDrive.setTargetPosition(rightTargetPosition);
    }
    public void SetPositionTicks(int leftPosition, int rightPosition) {

        leftDrive.setPower(maximumSpeed);
        rightDrive.setPower(maximumSpeed);

        leftDrive.setTargetPosition(leftPosition);
        rightDrive.setTargetPosition(rightPosition);
    }

    public void NoMoreDrive(){
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
    public void Brake(){
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    public double GetLeftPosition(){
        return leftDrive.getCurrentPosition();
    }

    public double GetRightPosition(){
        return rightDrive.getCurrentPosition();
    }
}
