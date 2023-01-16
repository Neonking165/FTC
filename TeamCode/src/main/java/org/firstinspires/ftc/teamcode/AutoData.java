package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

import ftc.rogue.blacksmith.Anvil;
import ftc.rogue.blacksmith.units.GlobalUnits;

// TODO: Find better file location for this

@Config
public class AutoData {
    // Timing variables
    public static double CLAW_CLOSE_OFFSET = 0.05;
    public static double INTAKE_LIFT_OFFSET = 0.565;
    public static double INTAKE_DELAY = .83;

    public static double LOWER_OFFSET = 0.08;
    public static double DEPOSIT_DELAY = 0.45;
    public static double DEPOSIT_OFFSET = 0.2;
    public static double RETRACT_OFFSET = 0.1;

    // Positions in centimeters/degrees of where to intake/deposit
    public static double INTAKE_X = 158.25;
    public static double INTAKE_Y = -26.98;

    // Depositing positon
    public static double DEPOSIT_X = 87.97;
    public static double DEPOSIT_Y = -17.29;
    public static double DEPOSIT_ANGLE = 139.94;

    public static int DEPOSIT_DROP_AMOUNT = 500;

    public static int AUTO_INTAKE_LIFT_HEIGHT_1 = 320 + 9 + 13;
    public static int AUTO_INTAKE_LIFT_HEIGHT_2 = 240 + 9 + 13 + 10;
    public static int AUTO_INTAKE_LIFT_HEIGHT_3 = 180 + 9 + 13 + 12;
    public static int AUTO_INTAKE_LIFT_HEIGHT_4 = 120 - 8 + 6 + 3;
    public static int AUTO_INTAKE_LIFT_HEIGHT_5 = 0;
}
