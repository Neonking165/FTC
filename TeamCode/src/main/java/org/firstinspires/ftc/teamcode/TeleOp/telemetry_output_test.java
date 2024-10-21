package org.firstinspires.ftc.teamcode.TeleOp;

import java.io.File;
import java.io.IOException;

public class telemetry_output_test {
    public static void main(String[] args) {
        try {
            File myObj = new File("gamepad_telementry_data.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}