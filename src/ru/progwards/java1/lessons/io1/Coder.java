package ru.progwards.java1.lessons.io1;

import java.io.*;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) throws IOException{
        try {
            FileReader reader = new FileReader(inFileName);
            FileWriter writer = new FileWriter(outFileName, true);

            try {
                for (int c; (c = reader.read()) >= 0; ) {
                    writer.write(code[c]);
                }
            } finally {
                reader.close();
                writer.close();
            }
        } catch (Exception e) {

            FileWriter logWriter = new FileWriter(logName, true);
            logWriter.write(e.getMessage() + "\n");
            logWriter.close();
        }


    }
}
