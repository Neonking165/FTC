package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class pivot_subsystem {

    private DcMotor pivotMotor;

    private float maxSpeed = 0.9f;

    private int fineTuneTPS = 100;

    //core hex is 288 ticks per revolution
    private int IntakeTickPosition = 0;
    private int BasketTickPosition = 0;
    private int SpecimenTickPosition = 0;

    //stow is pos 0
    //intake is pos 1
    //basket is pos 2
    //specimen is pos 3
    //intermediate position due to fine tune is 4

    private int PivotPosition = 0;

    private float PivotTicksPosition = 0;

    public pivot_subsystem(HardwareMap hardwareMap){

        pivotMotor = hardwareMap.get(DcMotor.class, "pivot_motor");

        pivotMotor.setDirection(DcMotor.Direction.REVERSE);

        pivotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        pivotMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        if(PivotPosition != 0){
            PivotTicksPosition = 0;
            pivotMotor.setTargetPosition(Math.round(PivotTicksPosition));
            PivotPosition = 0;
        }
    }
    public void intake(){
        if(PivotPosition != 1){
            PivotTicksPosition = IntakeTickPosition;
            pivotMotor.setTargetPosition(Math.round(PivotTicksPosition));
            PivotPosition = 1;
        }
    }
    public void basket(){
        if(PivotPosition != 2){
            PivotTicksPosition = BasketTickPosition;
            pivotMotor.setTargetPosition(Math.round(PivotTicksPosition));
            PivotPosition = 2;
        }
    }
    public void specimen(){
        if(PivotPosition != 3){
            PivotTicksPosition = SpecimenTickPosition;
            pivotMotor.setTargetPosition(Math.round(PivotTicksPosition));
            PivotPosition = 3;
        }
    }

    public void fineTune(float speed, double deltaTimeMs){
        PivotPosition = 4;
        PivotTicksPosition += (speed * deltaTimeMs * (fineTuneTPS / 1000));
        pivotMotor.setTargetPosition(Math.round(PivotTicksPosition));
    }

}
