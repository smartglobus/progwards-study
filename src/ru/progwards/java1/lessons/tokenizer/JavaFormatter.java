package ru.progwards.java1.lessons.tokenizer;

import java.util.Arrays;
import java.util.StringTokenizer;

public class JavaFormatter {

    public static String format(String code) {

// раскладываем строку на лексемы и все возможные знаки, включая пробелы (чтобы не потерять оригинальный формат фраз в кавычках)
        StringTokenizer rawString = new StringTokenizer(code, " \t\n\r\f.,!?|[]{}()\";:+-/*%=<>", true);
        // представляем строку как массив отдельных слов и символов
        int strElementsNum = rawString.countTokens();
        String[] rawCodeStr = new String[strElementsNum];
        Arrays.fill(rawCodeStr, ""); // предварительное заполнение массива, чтобы избежать значения null в пустых строках
        for (int j = 0; j < strElementsNum; j++) {
            rawCodeStr[j] = rawString.nextToken();
        }

        String codeStringFormated = singleStringFormat(rawCodeStr);

        StringTokenizer secondPace = new StringTokenizer(codeStringFormated, "\n{};", true);
        int curlyBracketsFormatedStringElementsNum = secondPace.countTokens();
        String[] curlyBracketsFormatedString = new String[curlyBracketsFormatedStringElementsNum];
        Arrays.fill(curlyBracketsFormatedString, "");
        for (int i = 0; i < curlyBracketsFormatedStringElementsNum; i++) {
            curlyBracketsFormatedString[i] = secondPace.nextToken();
        }

        // форматирование фигурных скобок
        for (int i = 2; i < curlyBracketsFormatedStringElementsNum; i++) {

            // форматирование открывающих фигурных скобок
            if (curlyBracketsFormatedString[i - 1].equals("\n") && curlyBracketsFormatedString[i].equals("{")) {
                if (curlyBracketsFormatedString[i - 1].equals("\n") && curlyBracketsFormatedString[i].equals("{") && curlyBracketsFormatedString[i + 1].equals("\n")) {

                    curlyBracketsFormatedString[i - 1] = " ";
                    continue;
                } else {

                    curlyBracketsFormatedString[i - 1] = "{";
                    curlyBracketsFormatedString[i] = "\n";
                    continue;
                }
            }


            // форматирование закрывающих фигурных скобок
            if (curlyBracketsFormatedString[i - 1].equals(";") && curlyBracketsFormatedString[i].equals("}")) {
                curlyBracketsFormatedString[i - 1] = ";\n";
                i++;
                while (i < curlyBracketsFormatedStringElementsNum && curlyBracketsFormatedString[i].equals("}")) {
                    curlyBracketsFormatedString[i] = "\n}";
                    i++;
                }
            }
        }
        // переводим в строку отформатированный по всем параметрам, кроме начальных отступов, код
        // (иначе из-за оставшихся артефактов, типа "\n}", затруднительно провести форматирование отступов по '{', '}' и '\n'
        String curlyBracketsFormated = "";
        for (String a : curlyBracketsFormatedString) {
            curlyBracketsFormated += a;
        }

        StringTokenizer thirdPace = new StringTokenizer(curlyBracketsFormated, "\n{}", true);
        int finalyFormatedStringElementsNum = thirdPace.countTokens();
        //последний, "подсадной" элемент (+1) для корректной работы tabsFormat (обработка str[i+1]). Там в рабочем цикле он игнорируется
        String[] finalyFormatedString = new String[finalyFormatedStringElementsNum + 1];
        Arrays.fill(finalyFormatedString, "");
        for (int i = 0; i < finalyFormatedStringElementsNum; i++) {
            finalyFormatedString[i] = thirdPace.nextToken();
        }

        return tabsFormat(finalyFormatedString);
    }


    public static String tabsFormat(String[] str) {
        int curlyBracketsCount = 0;
        String result = "";
        int strLength = str.length;
        for (int i = 0; i < strLength - 1; i++) {

            // считаем фигурные скобки
            if (str[i].equals("{")) {
                curlyBracketsCount++;
            }
            if (str[i + 1].equals("}")) {
                curlyBracketsCount--;
            }

            if (str[i].equals("\n")) {
                if (str[i].equals("\n") && (str[i+1].equals("\n"))){
                    result += str[i];
                    continue;
                }

                for (int j = 0; j < curlyBracketsCount; j++) {
                    str[i] += "    ";
                }
                result += str[i];
            } else {
                result += str[i];
            }
        }

        return result;
    }


    // основной цикл фоматирования строки
    // решение о вписывании пробела перед str[i] принимается по предшествующему контексту
    public static String singleStringFormat(String[] str) {
        String result = "";
        String lastLex = "";
        int strLength = str.length;

        for (int i = 0; i < strLength; i++) {

            if (lastLex.equals("\n") || i == 0) {
                while (i < strLength - 1 && str[i].equals(" ")) {
                    i++;
                }
                result += str[i];// вписываем в начало строки первый значимый элемент
                lastLex = str[i];
                continue;
            }

            if (str[i].equals("\n")) {
                result += str[i];
                lastLex = str[i];
                continue;
            }

            // если лексема кавычка, то дописываем строку без форматирования до следующей кавычки
            // вписываем пробел перед открывающими кавычками только в случаях, если перед ними '+', ',', '=' или 'return'
            if (str[i].equals("\"")) {
                if (lastLex.equals("+") || lastLex.equals(",") || lastLex.equals("=") || lastLex.equals("return")) {
                    result += " \"";
                } else {
                    result += "\"";
                }
                do {
                    i++;
                    result += str[i];
                } while (!str[i].equals("\""));
                lastLex = str[i];
                continue;
            }

            // после исключения из форматирования комментариев и фраз в кавычках, пропускаем пробелы исходного кода
            if (str[i].equals(" ")) {
                continue;
            }

            // если лексема ';', ')', '.', ',', '[', ']', '}' то вписываем её без пробела
            if (str[i].equals(")") || str[i].equals(",") || str[i].equals(".") || str[i].equals(";") || str[i].equals("[") || str[i].equals("]") || str[i].equals("}")) {
                result += str[i];
                lastLex = str[i];
                continue;
            }
            // если предыдущая лексема точка, то текущую вписываем без пробела
            if (lastLex.equals(".")) {
                result += str[i];
                lastLex = str[i];
                continue;
            }

            //проверка на наличие двойных знаков (<=, !=, ...)
            if (isSign(lastLex) && isSign(str[i])) {
                result += str[i];
                lastLex = str[i];
                continue;
            }

            // перед открывающей круглой скобкой вписывается пробел, только если скобке предшествует оператор или знак операций арифметических, сравнения и логических
            if (str[i].equals("(")) {
                if (isOperator(lastLex) || isSign(lastLex)) {
                    result += " " + str[i];
                    lastLex = str[i];
                    continue;
                } else {
                    result += str[i];
                    lastLex = str[i];
                    continue;
                }
            }

            // перед открывающей фигурной скобкой вписывается пробел, кроме случаев, когда ей предшествует '{' или ']'
            if (str[i].equals("{")) {
                if (lastLex.equals("]") || lastLex.equals("{")) {
                    result += str[i];
                    lastLex = str[i];
                    continue;
                } else {
                    result += " " + str[i];
                    lastLex = str[i];
                    continue;
                }
            }

            // после открывающих скобок пробел не ставится
            if (lastLex.equals("(") || lastLex.equals("{") || lastLex.equals("[")) {
                result += str[i];
                lastLex = str[i];
                continue;
            }

            // все остальные варианты вписываются после пробела
            result += " " + str[i];
            lastLex = str[i];

        }
        return result;
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
        String signList = "+-/%*=<>!&|:^?";
        if (signList.contains(lex)) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        String code = "   public    static void main(String[] args)\n" +
                "{\n" +
                "System.out.println(\"Enter two numbers\");\n" +
                "int first = 10;\n" +
                "int second = 20;\n" +
                "System.out.println(first + \" \" + second);\n" +
                "int sum = first + second;\n" +
                "System.out.println(\"The   sum is: \" + sum);\n" +
                "  }\n" +
                "public static void main(String [] args)\n" +
                "   {\n" +
                "     int num = 1234, reversed = 0;\n" +
                "  System.out.println(\"Original Number: \" + num);\n" +
                "    while(num != 0)\n" +
                " {\n" +
                "        int digit = num%10;\n" +
                "        reversed=reversed*10+    digit ;\n" +
                "        num /= 10;\n" +
                "    }\n" +
                "    System.out.println(\"Reversed Number: \" + reversed);}\n" +
                "public static void main(String  []  args) {\n" +
                "    int row=2 , column=   3;\n" +
                "    int [ ] [ ] matrix = { { 2,3,4 } , { 5,6,4 } };\n" +
                "\n" +
                "    display ( matrix );\n" +
                "\n" +
                "    int [ ] [ ] transpose = new int [ column ] [ row ] ;\n" +
                "        for(int i=0; i < row; i++) {\n" +
                "            for ( int j = 0; j<column; j ++) {\n" +
                "                transpose [ j ] [ i ] = matrix [ i ] [ j ] ;}\n" +
                "     }\n" +
                "\n" +
                "  display(transpose);\n" +
                "}\n" +
                "\n" +
                "public static void display(int[][] matrix) {\n" +
                "    System.out.println( \"The matrix is: \" ) ;\n" +
                "  for(int [ ] row : matrix)\n" +
                "  {\n" +
                "     for (int column : row) {\n" +
                "            System.out.print( column + \"    \") ;\n" +
                "        }\n" +
                "     System.out.println ();    }}";

        System.out.println(format(code));
    }
}
