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


    private double maximumSpeed = 0.7;//keep low to reduce acceleration and therefore tyre slip
    private double gearRatio = 15;//x:1
    private double wheelDiameter = 15;//cm
    private double ticksPerMotorRevolution = 15;

    private double TicksPerCentimeter;

    private int leftTargetPosition;
    private int rightTargetPosition;

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

        leftTargetPosition += tickChangeLeft;
        rightTargetPosition += tickChangeRight;

        leftDrive.setTargetPosition(leftTargetPosition);
        rightDrive.setTargetPosition(rightTargetPosition);

        if (blocking){
            if((leftDrive.getCurrentPosition() > leftDrive.getTargetPosition() - 5) && (leftDrive.getCurrentPosition() < leftDrive.getTargetPosition() + 5)){
                if((rightDrive.getCurrentPosition() > rightDrive.getTargetPosition() - 5) && (rightDrive.getCurrentPosition() < rightDrive.getTargetPosition() + 5)){
                    //both wheels are in reasonable range to target position wait small period and continue
                    new Delay(100);
                }
            }
        }
    }

    public void DriveTicks(int leftTicks, int rightTicks) {

        leftDrive.setPower(maximumSpeed);
        rightDrive.setPower(maximumSpeed);

        leftTargetPosition += leftTicks;
        rightTargetPosition += rightTicks;

        leftDrive.setTargetPosition(leftTargetPosition);
        rightDrive.setTargetPosition(rightTargetPosition);
    }
    public void SetPositionTicks(int leftPosition, int rightPosition) {

        leftDrive.setPower(maximumSpeed);
        rightDrive.setPower(maximumSpeed);

        leftTargetPosition = leftPosition;
        rightTargetPosition = rightPosition;

        leftDrive.setTargetPosition(leftTargetPosition);
        rightDrive.setTargetPosition(rightTargetPosition);
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
        return leftTargetPosition;
    }

    public double GetRightPosition(){
        return rightTargetPosition;
    }
}
