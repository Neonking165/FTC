package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class pivot_subsystem {

    private DcMotorEx pivotMotor;

    private float maxSpeed = 1;

    private int fineTuneTPS = 100;

    //core hex is 288 ticks per revolution
    private int IntakeTickPosition = -3700;
    private int BasketTickPosition = -1960;
    private int SpecimenTickPosition = -2780;

    //stow is pos 0
    //intake is pos 1
    //basket is pos 2
    //specimen is pos 3
    //intermediate position due to fine tune is 4

    private int PivotPosition = 0;

    private int PivotTicksPosition = 0;

    public pivot_subsystem(HardwareMap hardwareMap){

        pivotMotor = hardwareMap.get(DcMotorEx.class, "pivot_motor");

        pivotMotor.setDirection(DcMotor.Direction.FORWARD);

        pivotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivotMotor.setTargetPosition(0);

        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

       // pivotMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivotMotor.setPower(maxSpeed);
        pivotMotor.setTargetPosition(0);

    }

    public int position(){
        return PivotPosition;
    }

    public int real_positionticks(){
        return pivotMotor.getCurrentPosition();
    }

    public void stow(){
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(PivotPosition != 0){
            PivotTicksPosition = 0;
            pivotMotor.setTargetPosition(PivotTicksPosition);
            PivotPosition = 0;
        }
    }

    public void intake(){
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(PivotPosition != 1){
            PivotTicksPosition = IntakeTickPosition;
            pivotMotor.setTargetPosition(PivotTicksPosition);
            PivotPosition = 1;
        }
    }

    public void basket(){
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(PivotPosition != 2){
            PivotTicksPosition = BasketTickPosition;
            pivotMotor.setTargetPosition(PivotTicksPosition);
            PivotPosition = 2;
        }
    }

    public void specimen(){
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(PivotPosition != 3){
            PivotTicksPosition = SpecimenTickPosition;
            pivotMotor.setTargetPosition(PivotTicksPosition);
            PivotPosition = 3;

        }
    }

    public void fineTune(float speed){
        PivotPosition = 4;
        pivotMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pivotMotor.setVelocity(speed);
        PivotTicksPosition = pivotMotor.getCurrentPosition();
    }

}