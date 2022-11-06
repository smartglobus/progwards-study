package ru.progwards.java1.lessons.tokenizer;

import java.util.StringTokenizer;

public class JavaFormatter {
    public static String format(String code){
        StringTokenizer test = new StringTokenizer(code," \n");
        return "тестируем пробелы \n ещё один \n  табуляция, отсюда\tдосюда";
    }
}
