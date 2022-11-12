package ru.progwards.java1.lessons.tokenizer;

import java.util.StringTokenizer;

public class JavaFormatter {

    public static String format(String code) {
        // разбиваем исходный код на строки по знакам абзаца
        StringTokenizer rawCode = new StringTokenizer(code, "\n");

        // создаём и заполняем массив полученными неформатированными строками
        int n = rawCode.countTokens();
        String[] codeStringsFormated = new String[n];// инициализация массива для конечного варианта

        String[] codeStringsRaw = new String[n];// массив необработанных строк кода
        for (int i = 0; i < n; i++) {
            codeStringsRaw[i] = rawCode.nextToken();
        }


        // цикл обработки отдельной строки

        int curlyBracketsCount = 0;
        int squareBracketsCount = 0;
        int roundBracketsCount = 0;
        int angledBracketsCount = 0;

// цикл до n. Предполагаем, что конечный код по кол-ву строк не длиннее исходного (???)
        for (int i = 0; i < n; i++) {

            // раскладываем строку на лексемы и все возможные знаки, кроме пробелов (считаем, что комментарии в задание не включены)
            StringTokenizer rawString = new StringTokenizer(codeStringsRaw[i], "\t\n\r\f.![]{}()\";:+-/*%=<>", true);
            // представляем строку как массив отдельных слов и символов
            int strElementsNum = rawString.countTokens();
            String[] rawCodeStr = new String[strElementsNum];
            for (int j = 0; j < strElementsNum; j++) {
                rawCodeStr[j] = rawString.nextToken();
                System.out.println(rawCodeStr[j]);
            }
// форматируем отдельную строку, предварительно представленную как массив отдельных слов и символов
            codeStringsFormated[i] = singleStringFormat(rawCodeStr);


            //считаем фигурные скобки. '{' curlyBracketsCount++; '}' curlyBracketsCount--;
            //
            for (String element : rawCodeStr) {
                if (element == "{") {
                    curlyBracketsCount++;
                }
                if (element == "}") {
                    curlyBracketsCount--;
                }
            }

//            while (rawString.hasMoreTokens()) {
//                System.out.println(rawString.nextToken());
//            }

        }


        return "тестируем пробелы \n ещё один \n  табуляция, отсюда\tдосюда";
    }

    static boolean isOperator(String lex) {
        String keyWordList = " abstract assert boolean break byte case catch char class const continue default do " +
                " double else enum extends final finally float for goto if implements import instanceof int interface " +
                " long native new package private protected public return short static strictfp super switch " +
                " synchronized this throw Throws transient try void volatile while ";
        if (keyWordList.contains(" " + lex + " ")) {
            return true;
        }
        return false;
    }

    static boolean isSign(String lex) {
        String signList = " + - / % * = < > ! & | : ";
        if (signList.contains(" " + lex + " ")) {
            return true;
        }
        return false;
    }

    public static String singleStringFormat(String[] str) {
        String result = "";
        int firstWordNum = 0;
        for (int i = 0; i < str.length; i++) {
            // пропускаем пробелы в начале строки, если они там есть
            if (str[0] == " ") {
                while (str[i] == " ") {
                    i++;
                }
            }
            result += str[i];// вписываем в начало строки первый значимый элемент
            firstWordNum = i;// номер первого значимого слова в массиве
        }

// основной цикл фоматирования строки
        int strLength = str.length;
        for (int i = firstWordNum + 1; i < strLength; i++) {

            if (str[i] == " ") {
                continue;
            }
            if (str[i] == ".") {
                result += str[i];
                continue;
            }
            if (str[i - 1] == ".") {
                result += str[i];
                continue;
            }

            //проверка на наличие двойных знаков (<=, !=, ...)
            if (i < strLength - 2 && isSign(str[i]) && isSign(str[i + 1])) {
                result += str[i] + str[i + 1] + " ";
                i++;
                continue;
            }


// если лексема не оператор и за ней идёт круглая скобка (, пишем без пробела лексему и скобку
            if (i < strLength - 2 && !isOperator(str[i]) && str[i + 1] == "(") {
                result += str[i] + str[i + 1];
                i++;
                continue;
            }

// после открывающей круглой скобки пробела нет
            if (str[i] == "(") {
                result += str[i];
                continue;
            }
// после закрывающей круглой скобки пробел есть, кроме случаев: ';', ')', '.', ','
            if (i < strLength - 2 && str[i] == ")") {
                int next = i + 1;
                if (str[next] == ";" || str[next] == ")" || str[next] == "." || str[next] == ",") {
                    result += str[i] + str[next];
                    i++;
                    continue;
                } else {
                    result += str[i] + " ";
                    continue;
                }
            }

// по умолчанию добавляем пробел после дописывания любой лексемы, если далее не следует ';'
            if (i < strLength - 2 && str[i + 1] != ";")
                result += str[i] + " ";
        }
        return result;
        /*
            определять, является ли лексема ключевым словом. Если да, то после него пробел, за исключением случаев,
             когда сразу после стоит .;[()
             */
    }


    public static void main(String[] args) {
        String code = "   public static void main(String[] args)\n" +
                "{\n" +
                "System.out.println(\"Enter two numbers\");\n" +
                "int first = 10;\n" +
                "int second = 20;\n" +
                "System.out.println(first + \" \" + second);\n" +
                "int sum = first + second;\n" +
                "System.out.println(\"The sum is: \" + sum);\n" +
                "  }";
        format(code);

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