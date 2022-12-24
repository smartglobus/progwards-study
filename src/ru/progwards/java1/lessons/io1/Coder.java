package ru.progwards.java1.lessons.io1;

import java.io.*;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {

        try {
            FileReader reader = new FileReader(inFileName);

            FileWriter writer = new FileWriter(outFileName, true);
            FileWriter logWriter = new FileWriter(logName, true);

            try {
                for (int c; (c = reader.read()) >= 0; ) {

                    writer.write(code[c]);
                }

            } catch (Exception e) {

                logWriter.write(e.getMessage() + "\n");
            } finally {

                writer.close();
                logWriter.close();
            }
        } catch (Exception e) {


        }

    }
}
