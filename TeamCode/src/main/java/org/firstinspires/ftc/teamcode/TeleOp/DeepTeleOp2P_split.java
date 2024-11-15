//Terrible code do not use

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drive.RemoteDrive;
import org.firstinspires.ftc.teamcode.Subsystems.claw_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.lift_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.pivot_subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.text_write_subsystem;

//gamepad 1 driver
//gamepad 2 subsysems
@TeleOp(name="New_teleop_1.1", group="Iterative OpMode")
//full robot teleop controlled by two users
public class DeepTeleOp2P_split extends OpMode {

    private RemoteDrive tankDrive;
    private pivot_subsystem pivot;
    private claw_subsystem claw;
    private lift_subsystem lift;
    private ElapsedTime time;
    private text_write_subsystem tel_up;
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
        endTime = time.milliseconds();
        double frequency = 1 / ((endTime - startTime) / 1000);
        telemetry.addData("Frequency", frequency + "Hz");

        startTime = time.milliseconds();

        //drive
        //left joystick is speed, right joystick is rotation
        double x = gamepad1.right_stick_x;
        double y = -gamepad1.left_stick_y;

        if (gamepad1.right_trigger>0.6) {
            tankDrive.Drive((x / 4), (y / 4));
        } else {
            tankDrive.Drive((x), (y));
        }





        if(gamepad1.left_trigger>0.6 || gamepad2.left_trigger>0.6) {
            lift.raise_lift();

        }
        else{
            tankDrive.Drive((x),(y));
        }

        if(gamepad1.y || gamepad2.y) {
            claw.close_claw();
            operation_type="close";

        }
        if(gamepad1.x || gamepad2.x) {
            claw.open_claw();
            operation_type="open";

        }


        if(gamepad1.b|| gamepad2.b) {
            pivot.basket();
            pivot_type="basket";
        }
        else if(gamepad1.right_bumper){
            pivot.intake();
            pivot_type="intake";

        }
        else if(gamepad1.left_bumper){
            pivot.specimen();
            pivot_type="specimen";
        }

        else{
            pivot.stow();
            pivot_type="stow";
        }



        text_write_subsystem.writeToFile("gamepad_telementry_data.txt", "testing123");







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