package ru.progwards.java1.lessons.io1;

import java.io.*;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
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
            try {
                FileWriter logWriter = new FileWriter(logName, true);
                try {
                    logWriter.write(e.getMessage() + "\n");
                }finally {
                    logWriter.close();
                }
            } catch (IOException e1) {
                System.out.println(e1);
            }
        }
    }
}
