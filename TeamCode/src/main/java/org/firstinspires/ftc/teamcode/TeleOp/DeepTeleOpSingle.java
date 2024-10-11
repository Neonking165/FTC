package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.pivot_subsystem;

@TeleOp(name="DeepTeleOpSingle", group="Iterative OpMode")

//full robot teleop controllable by a single user
public class DeepTeleOpSingle extends OpMode {

    private RemoteDrive tankDrive;
    private pivot_subsystem pivot;

    private ElapsedTime time;
    private double startTime;
    private double endTime;

    public void init() {
        tankDrive = new RemoteDrive(hardwareMap);
        tankDrive.Drive(0,0);

        pivot = new pivot_subsystem(hardwareMap);
        pivot.stow();

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
        double x  =  gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;

        tankDrive.Drive(x,y);

        //pivot
        //left trigger or right trigger is basket, left bumper is specimen, A is intake, B is stow, up/down dpad is fine tune
        if(gamepad2.b || gamepad1.b){
            //stow pivot
            pivot.stow();
        } else if (gamepad2.a || gamepad1.a) {
            //intake pivot position
            pivot.intake();
        } else if (gamepad2.left_trigger > 0.8 || gamepad2.right_trigger > 0.8 || gamepad1.left_trigger > 0.8 || gamepad1.right_trigger > 0.8){
            //basket pivot position
            pivot.basket();
        } else if (gamepad2.left_bumper || gamepad1.left_bumper){
            //specimen pivot position
            pivot.specimen();
        } else {
            //assume fine-tune mode and check for lowering slides
            if(gamepad2.dpad_up || gamepad1.dpad_up){
                pivot.fineTune(1, (endTime - startTime));
            } else if (gamepad2.dpad_down || gamepad1.dpad_down) {
                pivot.fineTune(-1, (endTime - startTime));
            }

            if(pivot.position() == 2 && gamepad2.right_trigger < 0.5 && gamepad1.right_trigger < 0.5){
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
