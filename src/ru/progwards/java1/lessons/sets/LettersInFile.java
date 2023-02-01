package ru.progwards.java1.lessons.sets;

import java.io.FileReader;
import java.util.*;

public class LettersInFile {
    public static String process(String fileName) throws Exception {
        String resultLine = "";
        Set<Character> characterSet = new TreeSet<>();
        try (FileReader reader = new FileReader(fileName);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                char[] lineToArray = scanner.nextLine().toCharArray();
                for (char ch : lineToArray) {
                    if (Character.isAlphabetic(ch)) {
                        characterSet.add(ch);
                    }
                }
            }
        }

        for (char c : characterSet) {
            resultLine += c;
        }
        return resultLine;
    }

    public static void main(String[] args) {
       try {
           System.out.println(process("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\io1\\testFile.txt"));
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
/*
Реализовать класс, считывающий содержимое файла и возвращающего набор букв, которые встречались в этом файле.
Буквы, это латинские [A..Z[ и [a..z] и русские [А..Я] и [а..я], остальные символы надо игнорировать
3.1 Метод public static String process(String fileName) - вернуть все буквы, которые встретились в файле, сконкатенированные в виде строки.
Буквы должны быть упорядочены по алфавиту, типа “ADEF...”. Все возникающие исключения, по работе с потоками, пробросить выше.

 */