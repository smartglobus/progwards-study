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

// раскладываем строку на лексемы и все возможные знаки, включая пробелы (чтобы не потерять оригинальный формат фраз в кавычках и комментариев)
            StringTokenizer rawString = new StringTokenizer(codeStringsRaw[i], " \t\n\r\f.,!?|[]{}()\";:+-/*%=<>", true);
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

        for (String s : codeStringsFormated
        ) {
            System.out.println(s);
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
        String signList = "+-/%*=<>!&|:^?";
        if (signList.contains(lex)) {
            return true;
        }
        return false;
    }

    public static String singleStringFormat(String[] str) {
        String result = "";
        String lastLex = "";
        int firstWordNum = 0;
        for (int i = 0; i < str.length; ) {
            // пропускаем пробелы в начале строки, если они там есть
            if (str[0].equals(" ")) {
                while (str[i].equals(" ")) {
                    i++;
                }
            }
            result += str[i];// вписываем в начало строки первый значимый элемент
            lastLex = str[i];
            firstWordNum = i;// номер первого значимого слова в массиве
            break;
        }


// основной цикл фоматирования строки
// решение о вписывании пробела перед str[i] принимается по предшествующему контексту
        int strLength = str.length;
        for (int i = firstWordNum + 1; i < strLength; i++) {
//            while (i < strLength && !str[i].equals("\"")){   // || !(str[i].equals("/") && str[i + 1].equals("/"))
//                if (str[i].equals(" ")) {
//                i++;
//            }
//
//            }

            // если лексема кавычка, то дописываем строку без форматирования до следующей кавычки
            // вписываем пробел перед открывающими кавычками только в случаях, если перед ними '+', ',', '=' или 'return'
            if (str[i].equals("\"")) {
                if (lastLex.equals("+") || lastLex.equals(",") || lastLex.equals("=") || lastLex.equals("return")){
                    result += " \"";
//                    lastLex = str[i];
                }else {
                    result += "\"";
//                    lastLex = str[i];
                }
                do {
                    i++;
                    result += str[i];
//                    lastLex = str[i];

                } while (!str[i].equals("\""));
                lastLex = str[i];
                i++;
            }
            // комментарии дописываем без форматирования до конца строки
//            if (str[i].equals("/") && str[i + 1].equals("/")) {
//                while (true) {// ???????????????????????????????
//                    result += str[i];
//                    lastLex = str[i];
//                    i++;
//                }
//            }

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
            if (lastLex.equals("(") || lastLex.equals("{") || lastLex.equals("[")){
                result += str[i];
                lastLex = str[i];
                continue;
            }
            // все остальные варианты вписываются после пробела
            result += " " + str[i];
            lastLex = str[i];
        }
        return result;
        /*
            определять, является ли лексема ключевым словом. Если да, то после него пробел, за исключением случаев,
             когда сразу после стоит .;[()
             */
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
                "  }\n"+
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
                "    System.out.println(\"Reversed Number: \" + reversed);}\n"+
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
                "     System.out.println ();    }}\n";
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