package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class text_write_subsystem {

    public static void main(String[] args) {
        String filePath = "output.txt"; // specify the file name or path
        String textToWrite = "Hello, this is a sample text to write to the file.";

        writeToFile(filePath, textToWrite);
    }

    public static void writeToFile(String filePath, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(text);
            writer.newLine(); // write a newline after the text
            System.out.println("Text written to file successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
