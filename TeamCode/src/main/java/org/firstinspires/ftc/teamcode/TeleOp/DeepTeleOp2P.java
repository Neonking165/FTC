//Terrible code do not use

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
@TeleOp(name="DeepTeleOp2P_testing_1.4", group="Iterative OpMode")
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
    //delete this
    //private boolean prevClawButton = false;
    private boolean GP1Control = true;

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
    //delete this
    /*
    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }
    */
    //Updates
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
        if(gamepad2.right_trigger > 0.6 || (gamepad1.right_trigger > 0.6 && GP1Control)){
            lift.raise_lift();
            pivot.basket();
            tLowerSlides = 0;
            pivotChangeCount++;
        }
        else{
            //then lower lift
            if(tLowerSlides == 0){
                if (!gamepad2.x && !(gamepad1.x && GP1Control) && !gamepad2.y && !(gamepad1.y && GP1Control)){
                    claw.open_claw();
                }
                pivot.stow();
                pivot_type="stow";
                tLowerSlides = time.milliseconds() + 800;
                pivotChangeCount++;
            }else{
                if(time.milliseconds() > tLowerSlides && tLowerSlides > 2){

                    claw.close_claw();
                    lift.lower_lift();
                    tLowerSlides = 1;
                    //set to 1 to stop looping and only run pivot stow once
                }
            }
        }



        //pivot
        //left trigger or right trigger is basket, left bumper is specimen, A is intake, B is stow, up/down dpad is fine tune
        if(gamepad2.b || (gamepad1.b && GP1Control)){
            //stow pivot
            pivot.stow();
            pivot_type="stow";
            claw.close_claw();
            operation_type="close";
            pivotChangeCount++;
        } else if (gamepad2.left_trigger > 0.6 || (gamepad1.left_trigger > 0.6 && GP1Control)) {
            //intake pivot position
            pivot.intake();
            pivotChangeCount++;
        } else if (gamepad2.left_bumper || (gamepad1.left_bumper&& GP1Control)){
            //specimen pivot position
            pivot.specimen();
            pivotChangeCount++;
        } else {
            //assume fine-tune mode and check for lowering slides
            if (gamepad2.dpad_up || (gamepad1.dpad_up && GP1Control)) {
                pivot.fineTune(1);
                pivotChangeCount++;
            } else if (gamepad2.dpad_down || (gamepad1.dpad_down && GP1Control)) {
                pivot.fineTune(-1);
                pivotChangeCount++;
            }
        }

        //claw
        if(gamepad2.x || (gamepad1.x && GP1Control)){
            claw.close_claw();
        }
        if(gamepad2.y || (gamepad1.y && GP1Control)){
            claw.open_claw();
        }

        //elevator
        //hold right trigger or right bumper to raise

        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Power", "Left: " + tankDrive.GetLeftVelocity() + "Right: " + tankDrive.GetRightVelocity());
        telemetry.addData("Pivot Position", "Ticks: " + pivot.real_positionticks() + "Step: " + pivot.position());
        telemetry.addData("claw:",operation_type);
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