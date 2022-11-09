package ru.progwards.java1.lessons.tokenizer;

import java.util.StringTokenizer;

public class JavaFormatter {

    public static String format(String code) {
        // разбиваем исходный код на строки по знакам абзаца
        StringTokenizer rawCode = new StringTokenizer(code, "\n");

        // создаём и заполняем массив полученными неформатированными строками
        int n = rawCode.countTokens();
        String[] codeStringsRaw = new String[n];
        String[] codeStringsFormated = new String[n];// инициализация массива для конечного варианта

        for (int i = 0; i < n; i++) {
            codeStringsRaw[i] = rawCode.nextToken();
        }


        // цикл обработки отдельной строки

        int curlyBracketsCount = 0;
        int squareBracketsCount = 0;
        int roundBracketsCount = 0;
        int angledBracketsCount = 0;

        for (int i = 0; i < n; i++) {
            // раскладываем строку на лексемы, включая все возможные знаки
            StringTokenizer rawString = new StringTokenizer(codeStringsRaw[i], " \t\n\r\f.![]{}()\";:+-/*%=<>", true);
            // представляем строку как массив
            String[] rawCodeStr = new String[rawString.countTokens()];
            /*
            определять, является ли лексема ключевым словом. Если да, то после него пробел, за исключением случаев,
             когда сразу после стоит .;[()
             */

        }


        return "тестируем пробелы \n ещё один \n  табуляция, отсюда\tдосюда";
    }

    static boolean isKeyWord(String word) {
        String keyWordList = " abstract assert boolean break byte case catch char class const continue default do " +
                " double else enum extends final finally float for goto if implements import instanceof int interface " +
                " long native new package private protected public return short static strictfp super switch " +
                " synchronized this throw Throws transient try void volatile while ";
        if (keyWordList.contains(" " + word + " ")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String code = "public static void main(String[] args)\n" +
                "{\n" +
                "System.out.println(\"Enter two numbers\");\n" +
                "int first = 10;\n" +
                "int second = 20;\n" +
                "System.out.println(first + \" \" + second);\n" +
                "int sum = first + second;\n" +
                "System.out.println(\"The sum is: \" + sum);\n" +
                "  }";
        format(code);
        System.out.println(isKeyWord("enum"));
    }
}
/*
public static void main(String[] args)   - проверить все пробелы
{                                        - перенести наверх, посчитать номер
System.out.println("Enter two numbers");
int first = 10;
int second = 20;
System.out.println(first + " " + second);
int sum = first + second;
System.out.println("The sum is: " + sum);
  }
 */