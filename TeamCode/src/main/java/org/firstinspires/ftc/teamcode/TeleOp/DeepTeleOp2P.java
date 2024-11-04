package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.pivot_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.lift_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.claw_subsystem;
//gamepad 1 driver
//gamepad 2 subsysems
@TeleOp(name="DeepTeleOp2P_testing", group="Iterative OpMode")
//full robot teleop controlled by two users
public class DeepTeleOp2P extends OpMode {

    private RemoteDrive tankDrive;
    private pivot_subsystem pivot;
    private claw_subsystem claw;
    private lift_subsystem lift;
    private ElapsedTime time;
    private double startTime;
    private double endTime;
    private double tLowerSlides = 0;

    public void init() {
        tankDrive = new RemoteDrive(hardwareMap);
        tankDrive.Drive(0,0);
        pivot = new pivot_subsystem(hardwareMap);
        claw = new claw_subsystem(hardwareMap);
        pivot.stow();
        pivot_type="stow";
        claw.close_claw();
        operation_type="close";
        lift = new lift_subsystem(hardwareMap);

        telemetry.addData("Status", "Initialised");
        telemetry.update();
        time = new ElapsedTime();
        time.reset();

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

    String operation_type = "operating";
    String pivot_type = "operating";
    String error_type = "erroring";

    @Override
    public void loop() {
        int pivotChangeCount = 0;
        endTime =  time.milliseconds();
        double frequency = 1 /  ((endTime - startTime) / 1000);
        telemetry.addData("Frequency", frequency + "Hz");

        startTime = time.milliseconds();

        //drive
        //left joystick is speed, right joystick is rotation
        double x = gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;

        if(gamepad1.right_bumper || gamepad2.right_bumper) {
            tankDrive.Drive((x/4),(y/4));
        }
        else{
            tankDrive.Drive((x),(y));
        }

        //lift
        if(gamepad2.right_trigger > 0.6 || gamepad1.right_trigger > 0.6){
            lift.raise_lift();
            pivot.basket();
            tLowerSlides = 0;
            pivotChangeCount++;
        }
        else{
            //then lower lift
            if(tLowerSlides == 0){
                pivot.stow();
                claw.open_claw();
                pivot_type="stow";
                tLowerSlides = time.milliseconds() + 800;
                pivotChangeCount++;
            }else{
                if(time.milliseconds() > tLowerSlides){
                    //claw.close_claw();
                    claw.close_claw();
                    lift.lower_lift();
                    tLowerSlides = -1;
                    //set to -1 to stop looping and only run pivot stow once
                }
            }
        }
        if(gamepad2.right_bumper|| gamepad1.right_bumper){
            lift.raise_lift();
        }


        //pivot
        //left trigger or right trigger is basket, left bumper is specimen, A is intake, B is stow, up/down dpad is fine tune
        if(gamepad2.b || gamepad1.b){
            //stow pivot
            pivot.stow();
            pivot_type="stow";
            claw.close_claw();
            operation_type="close";
            pivotChangeCount++;
        } else if (gamepad2.a || gamepad1.a) {
            //intake pivot position
            pivot.intake();
            pivotChangeCount++;
        } else if (gamepad2.left_bumper || gamepad1.left_bumper){
            //specimen pivot position
            pivot.specimen();
            pivotChangeCount++;
        } else {
            //assume fine-tune mode and check for lowering slides
            if (gamepad2.dpad_up || gamepad1.dpad_up) {
                pivot.fineTune(0.4f);
                pivotChangeCount++;
            } else if (gamepad2.dpad_down || gamepad1.dpad_down) {
                pivot.fineTune(-0.4f);
                pivotChangeCount++;
            }
        }

        //claw
        if(gamepad2.x || gamepad1.x ){
            claw.close_claw();
            operation_type="close";
            pivotChangeCount++;
        } else if(gamepad2.y || gamepad1.y ){
            claw.open_claw();
            operation_type="open";
            pivotChangeCount++;
        }

        //elevator
        //hold right trigger or right bumper to raise

        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Power", "Left: " + tankDrive.GetLeftVelocity() + "Right: " + tankDrive.GetRightVelocity());
        telemetry.addData("Pivot Position", "Ticks: " + pivot.real_positionticks() + "Step: " + pivot.position());
        telemetry.addData("testing:",operation_type);
        telemetry.addData("pivot_type:",pivot_type);
        telemetry.addData("Errors:",error_type);
        telemetry.addData("PivotChangesThisFrame:", pivotChangeCount);

        telemetry.update();
    }

    @Override
    public void stop() {
        tankDrive.NoMoreDrive();
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}