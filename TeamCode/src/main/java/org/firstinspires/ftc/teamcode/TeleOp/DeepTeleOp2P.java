package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.pivot_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.lift_subsystem;
//gamepad 1 driver
//gamepad 2 subsysems
@TeleOp(name="DeepTeleOp2P", group="Iterative OpMode")

//full robot teleop controlled by two users
public class DeepTeleOp2P extends OpMode {

    private RemoteDrive tankDrive;
    private pivot_subsystem pivot;
    private lift_subsystem lift;
    private ElapsedTime time;
    private double startTime;
    private double endTime;
    private double tLowerSlides = 0;
    public void init() {
        tankDrive = new RemoteDrive(hardwareMap);
        tankDrive.Drive(0,0);
        pivot = new pivot_subsystem(hardwareMap);
        pivot.stow();
        lift_subsystem lift = new lift_subsystem(hardwareMap);

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

    @Override
    public void loop() {

        endTime =  time.milliseconds();
        double frequency = 1 /  ((endTime - startTime) / 1000);
        telemetry.addData("Frequency", frequency + "Hz");

        startTime = time.milliseconds();

        //drive
        //left joystick is speed, right joystick is rotation
        double x = gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;
        if(gamepad1.left_trigger>0.6){
            tankDrive.Drive((x/2),(y/2));

        }
        else{
            tankDrive.Drive((x),(y));


        }
        tankDrive.Drive(x,y);


        //lift
        if(gamepad2.right_trigger>0.6){
            lift.raise_lift();
            tLowerSlides = 0;
        }
        else{
            pivot.stow();
            //then lower lift
            if(tLowerSlides == 0){
                tLowerSlides = time.milliseconds() + 500;
            }else{
                if(time.milliseconds() > tLowerSlides){
                    lift.lower_lift();
                    tLowerSlides = 0;
                }
            }

        }


             //pivot
        //left trigger or right trigger is basket, left bumper is specimen, A is intake, B is stow, up/down dpad is fine tune
        if(gamepad2.b){
            //stow pivot
            pivot.stow();
        } else if (gamepad2.a) {
            //intake pivot position
            pivot.intake();
        } else if (gamepad2.left_trigger > 0.8 || gamepad2.right_trigger > 0.8){
            //basket pivot position
            pivot.basket();
        } else if (gamepad2.left_bumper){
            //specimen pivot position
            pivot.specimen();
        } else {
            //assume fine-tune mode and check for lowering slides
            if(gamepad2.dpad_up){
                pivot.fineTune(1, (endTime - startTime));
            } else if (gamepad2.dpad_down) {
                pivot.fineTune(-1, (endTime - startTime));
            }

            if(pivot.position() == 2 && gamepad2.right_trigger < 0.5){
                //user is lowering from basket, go to rest position
                pivot.stow();
            }

        }
        //elevator
        //hold right trigger or right bumper to raise

        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Power", "Left: " + tankDrive.GetLeftVelocity() + "Right: " + tankDrive.GetRightVelocity());
        telemetry.addData("Pivot Position", "Ticks: " + pivot.real_positionticks() + "Step: " + pivot.position());
        telemetry.update();
    }

    @Override
    public void stop() {
        tankDrive.NoMoreDrive();
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}