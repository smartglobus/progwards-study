package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LineCount {
    public static int calcEmpty(String fileName){
        int emptyLine = 0;
try{
    FileReader reader = new FileReader(fileName);
    Scanner scanner = new Scanner(reader);
    try{
        while (scanner.hasNextLine()){
            if (scanner.nextLine().isEmpty()){
                emptyLine++;
            }
        }
    }finally {
        scanner.close();
    }
}catch (IOException e){
    return  -1;
}
        return emptyLine;
    }

    public static void main(String[] args) {
        System.out.println(calcEmpty("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\io1\\DateDiff current.txt"));
    }
}
