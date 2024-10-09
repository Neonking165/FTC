package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.ServoTest;

//teleop using externally declared subsystems, drives robot and actuates a servo
@TeleOp(name="LiftTestTeleOp", group="Iterative OpMode")

public class LiftTestTeleOp extends OpMode {

    private RemoteDrive tankDrive;

    private ElapsedTime time;

    private double startTime;

    private double endTime;

    private DcMotor LinkageMotor;
    private DcMotor PivotMotor;


    //288 ticks per rotation
    private int pivotHighBaksetTicks = 169;//135 degrees
    private int pivotCollectTicks = 306;//245 degrees

    private int pivotTargetPosition = 0;

    public void init() {
        tankDrive = new RemoteDrive(hardwareMap);
        tankDrive.Drive(0,0);

        telemetry.addData("Status", "Initialised");
        telemetry.update();
        time = new ElapsedTime();
        time.reset();

        startTime = 0;
        endTime = 0;

        LinkageMotor = hardwareMap.get(DcMotor.class, "linkage_motor");
        LinkageMotor.setDirection(DcMotor.Direction.FORWARD);
        LinkageMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LinkageMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        PivotMotor = hardwareMap.get(DcMotor.class, "pivot_motor");
        PivotMotor.setDirection(DcMotor.Direction.REVERSE);
        PivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PivotMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop() {
        endTime =  time.milliseconds();
        double frequency = 1 /  ((endTime - startTime) / 1000);
        telemetry.addData("Frequency", frequency + "Hz");

        startTime = time.milliseconds();
        double x  =  gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;

        tankDrive.Drive(x,y);


        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Power", "Left: " + tankDrive.GetLeftVelocity() + "Right: " + tankDrive.GetRightVelocity());

        telemetry.update();

        LinkageMotor.setPower(gamepad2.right_trigger);

        PivotMotor.setPower(1);

        if (gamepad2.dpad_down){

        }else if (gamepad2.dpad_up){
            pivotTargetPosition += (endTime - startTime) * 0.075;
            if(pivotTargetPosition < 0){
                pivotTargetPosition = 0;
            }
        }else{
            if(gamepad2.left_trigger > 0.6){
                pivotTargetPosition = pivotHighBaksetTicks;
                //high basket
            } else if (gamepad2.left_bumper) {
                pivotTargetPosition = pivotCollectTicks;
                //collect from ground
            }else if (gamepad2.dpad_left || gamepad2.dpad_right){
                pivotTargetPosition = 0;
                //home
            }
        }
    }

    @Override
    public void stop() {
        tankDrive.NoMoreDrive();
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
