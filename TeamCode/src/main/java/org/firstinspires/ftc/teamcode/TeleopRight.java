/* FTC Team 7572 - Version 1.0 (11/11/2023)
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * TeleOp RIGHT side of field
 */
@TeleOp(name="Teleop-Right", group="7592")
//@Disabled
public class TeleopRight extends Teleop {

    @Override
    public void setAllianceSpecificBehavior() {
        // PowerPlay is symmetric for Red vs. Blue, and Left vs. Right
        // during Tele-Op.  We define this, but so far never use it.
        leftAlliance = false;

        // CENTERSTAGE AprilTag assignments:
        int  aprilTagLeft   = 4;  // Red Alliance LEFT   Backdrop
        int  aprilTagCenter = 5;  // Red Alliance CENTER Backdrop
        int  aprilTagRight  = 6;  // Red Alliance RIGHT  Backdrop
        // Red Alliance 5-stack 2"/50mm  = 8
        // Red Alliance 5-stack 5"/127mm = 7

    }
} // TeleopRight