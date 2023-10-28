package ru.progwards.smartglobus;


import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class tempTest {
    public static void main(String[] args) {
        int x = 12420;

        String daysString;
        String remainderInfo = "Число оканчивается на ";

        int remainder100 = x % 100;
        if (remainder100 >= 11 && remainder100 <= 20) {
            daysString = "дней";
            remainderInfo += remainder100;
        } else {
            int remainder10 = x % 10;

            if (remainder10 == 1)
                daysString = "день";
            else if (remainder10 >= 2 && remainder10 <= 4)
                daysString = "дня";
            else daysString = "дней";

            remainderInfo += remainder10;
        }


    }


    static String textGrade(int grade) {
        if (grade == 0) return "не оценено";
        if (1 <= grade && grade <= 20) return "очень плохо";
        if (21 <= grade && grade <= 40) return "плохо";
        if (41 <= grade && grade <= 60) return "удовлетворительно";
        if (61 <= grade && grade <= 80) return "хорошо";
        if (81 <= grade && grade <= 100) return "отлично";
        return "не определено";
    }


    Num fifth = new Num(5);


    public static class precision {


        static double precision() {
            double pres = 1;
            double d = 1;
            while (d > 0) {
                pres = d;
                d /= 2;
            }
            return pres;
        }

        static String charArrayToString(char[] a) {
            String res = Character.toString(a[0]);
            for (int i = 1; i < a.length; i++) {
                res += Character.toString(a[i]);
            }
            return res;
        }

        public static void main(String[] args) {
            char[] digits = {'1', '2', '3', '4', '5'};

            System.out.println(charArrayToString(digits));
            System.out.println(precision());


        }

    }


    public  class Num implements MyComparable<Num> {
        int num;



        Num(int num) {
            this.num = num;
        }

        @Override
        public int compareTo(Num a) {
            return Integer.compare(num, a.num);
        }
    }

}

class Person{
    static class Child1{
        String hello(){
            return "привет";
        }
    }
    class Child2{
        String hello(){
            return "servus";
        }
    }
}

class TZ {
    Calendar birtday = new GregorianCalendar();
    Calendar calendar = Calendar.getInstance();
//calendar.clear();

    Date createDate() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(1986, 1, 28, 0, 0, 0);
        return new Date(c.getTimeInMillis());
    }


    public static int diffWithMoscow(String strtz) {
        TimeZone MSK = TimeZone.getTimeZone("Europe/Moscow");
        TimeZone ZoneX = TimeZone.getTimeZone(strtz);

        return MSK.getRawOffset() - ZoneX.getRawOffset();
    }

    public static String swapWords(String sentance) {
        StringTokenizer swapWd = new StringTokenizer(sentance, " .,-!\n");
        int n = swapWd.countTokens();
        String[] toSwap = new String[n];
        int iter = 0;
        while (swapWd.hasMoreTokens()) {
            toSwap[iter] = swapWd.nextToken();
            iter++;
        }
        String result = "";
        for (int i = 1; i < n; i += 2) {
            result += toSwap[i] + " " + toSwap[i - 1]+" ";
        }
        if (n % 2 == 1) {
            result += toSwap[n - 1];
        }
        return result;
    }


    public static void monthPlus(Calendar calendar) {
        Calendar tst = (Calendar) calendar.clone();

        while (tst.get(Calendar.YEAR) < 2025) {
            System.out.println(tst.get(Calendar.YEAR) + "  " + tst.get(Calendar.MONTH) + "  " + tst.getActualMaximum(Calendar.DAY_OF_MONTH));
            tst.set(Calendar.MONTH, tst.get(Calendar.MONTH) + 1);
//            if (tst.get(Calendar.YEAR)>2025){
//                break;
//            }
        }

    }

    class Person {
        public String name;
        public Person(String name) {
            this.name = name;
        }
    }
    abstract class PersonCompare {
        public int compare(Person p1, Person p2) {
            return 0;
        }
    }
    PersonCompare personCompare = new PersonCompare(){
        @Override
        public int compare(Person p1, Person p2) {
            return p1.name.compareTo(p2.name);
        }
    };

    static class Rectangle {

        Rectangle(BigDecimal a, BigDecimal b) {
            this.a = a;
            this.b = b;
        }
        public BigDecimal a;
        public BigDecimal b;
    }
    boolean rectCompare(Rectangle r1, Rectangle r2){
        return (r1.a.multiply(r1.b)).equals(r2.a.multiply(r2.b));
    }


    static boolean compareString(String a, String b){
        if (a.equals(b)){
            return true;
        }
        return  false;
    }

    public String test(String filename) throws IOException {
        if(filename == null){
            throw new IOException("File not found");
        }
        return "File processing";
    }

    private int lineCount(String filename) throws IOException {
        int count = 0;
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            try {
                while (scanner.hasNextLine()){
                   scanner.nextLine();
                    count++;
                }
            }finally {
                scanner.close();
            }
        }catch ( IOException e){
            throw new IOException("файл не найден");
        }
        return count;
    }

    public static void main(String[] args) {

//        System.out.println(Calendar.DAY_OF_MONTH + "  " + Calendar.DAY_OF_WEEK);
//        System.out.println(diffWithMoscow("America/New_York"));
//         System.out.println(Calendar.getInstance("Europe/Moscow", "ru", "RU",4,8));
        Calendar trash = Calendar.getInstance();
//        monthPlus(trash);
//        System.out.println(swapWords("Слово - серебро, молчание - золото! ещёфигня иещё"));
        String a = "public static void main(String[] args) {\n" +
                "    int row = 2, column = 3;\n" +
                "    int[][] matrix = {{2, 3, 4}, {5, 6, 4}};\n" +
                "\n" +
                "    display(matrix);\n" +
                "\n" +
                "    int[][] transpose = new int[column][row];\n" +
                "    for (int i = 0; i < row; i ++) {\n" +
                "        for (int j = 0; j < column; j ++) {\n" +
                "            transpose[j][i] = matrix[i][j];\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    display(transpose);\n" +
                "}\n" +
                "\n" +
                "public static void display(int[][] matrix) {\n" +
                "    System.out.println(\"The matrix is: \");\n" +
                "    for (int[] row : matrix) {\n" +
                "        for (int column : row) {\n" +
                "            System.out.print(column + \"    \");\n" +
                "        }\n" +
                "        System.out.println();\n" +
                "    }\n" +
                "}";
        String b = "public static void main(String[] args) {\n" +
                "    int row = 2, column = 3;\n" +
                "    int[][] matrix = {{2, 3, 4}, {5, 6, 4}};\n" +
                "\n" +
                "    display(matrix);\n" +
                "\n" +
                "    int[][] transpose = new int[column][row];\n" +
                "    for (int i = 0; i < row; i++) {\n" +
                "        for (int j = 0; j < column; j++) {\n" +
                "            transpose[j][i] = matrix[i][j];\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    display(transpose);\n" +
                "\n" +
                "\t\t}\n" +
                "public static void display(int[][] matrix) {\n" +
                "    System.out.println(\"The matrix is: \");\n" +
                "    for (int[] row : matrix) {\n" +
                "        for (int column : row) {\n" +
                "            System.out.print(column + \"    \");\n" +
                "        }\n" +
                "        System.out.println();\n" +
                "    }\n" +
                "}";
        System.out.println(compareString(a,b));
        System.out.println("------------");
        System.out.println("            transpose[j][i] = matrix[i][j];\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    display(transpose);\n" +
                "\n" +
                "\t\t}\n" +
                "public static void display(int[][] matrix) {\n" );
        System.out.println("------------");
    }
}