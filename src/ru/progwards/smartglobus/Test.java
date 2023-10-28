package ru.progwards.smartglobus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    static String a = "3пролл  'о'ор'  оро";

    public static void main(String[] args) {
        long tt = 123;
        {
            String a = "2";
        }

        double d1 = Double.parseDouble("7.998786");
        Integer j = Integer.parseInt("8");
        int t = (int) 765.567;
//        System.out.println((short)2147478071);
        System.out.println(maxInt());
//         System.out.println(maxInt()+2147483647+2147483647+2147483647+4);
//        boolean t = Character.isLetter('h');
//        System.out.println((short) 256567);
        String twoInt = t + "" + t;
        int z = Integer.parseInt(twoInt);
        //System.out.println(addAsStrings(56,976));
        //getE();
        System.out.println(factorial(3));
//        Triangle t1 = new Triangle(5, 6, 7);
//        System.out.println(t1.a);
//        System.out.println(t1.b);
//        System.out.println(t1.c);
        char x = 'B';
        System.out.println((int) x);
        File trr = new File("C:/Users/User/Documents/Progwards/Материалы курса");
        System.out.println(trr.exists());
        System.out.println(trr.length());
        System.out.println(trr.isDirectory());
//        Test.scanLines();
        System.out.println(invertWords("Буря мглою небо кроет"));
        try {
            System.out.println(setStars("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\smartglobus\\test file.txt"));

        } catch (IOException e) {
            System.out.println(e);
//            e.printStackTrace();
        }

        Collection<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            numbers.add(i);

        numbers.add(3);
        for (Integer ar : numbers
        ) {
            System.out.println(ar);
        }
//        System.out.println((int)'9');
        Figure s = new Square(5);
        System.out.println(figDetect(s));
        System.out.println(Square.class);
        System.out.println(s.getClass());
//        System.out.println(createFolder("newFolder"));
//        System.out.println(folderCreate("novyFolder"));
        System.out.println(replaceF("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\smartglobus\\test file.txt"));
//        Predicate<Integer> prd = xx -> {System.out.println(xx);return trr.toString().contains("u");};
        System.out.println(reverseChars("главрыба"));
        String expr = "5 + 5 + 45 + 1 = 1 + 1 + ";
        ArrayList<Integer> ints = new ArrayList<>(List.of(50, 50, 45, 1, 1, 1));
//        String[] sAr = expr.split("[ ]+[=+]+[ ]");
//
//        for (String el:
//           sAr  ) {
//            System.out.print(el + ":");
//        }
//        System.out.println("\n"+ expr.substring(0,expr.length()-2) + "= ");

        List<Integer> intArr = new ArrayList<>(List.of(4, 4, 3, 3, 3, 1, 1, 1));
//Function<Integer,String> inTSt= i-> String.valueOf(i);
//List<String> intToStr = intArr.stream().map(i-> String.valueOf(i)).collect(Collectors.toList());
//        List<Integer> moreThanOne = intArr.stream().sorted().dropWhile(el -> el < 2).collect(Collectors.toList());
//        System.out.println(moreThanOne);
        int n = Optional.of(intArr.stream().sorted().dropWhile(el -> el < 2).min(Comparator.comparingInt(el -> el)).orElse(8)).get();
        System.out.println(intArr.lastIndexOf(n));
        expr = String.join("+", intArr.stream().map(i -> String.valueOf(i)).collect(Collectors.toList()));
        System.out.println(intArr);
//        System.out.println(intToStr);
        System.out.println(expr);
//       Arrays.stream(Locale.getAvailableLocales()).forEach(System.out::println);
        System.out.format(new Locale("ru", "RU"), "|%-10s|%2$td/%2$tm/%2$tY|%3$,10.2f|\n", "Vasya", new Date().getTime(), 26523.7);

    }


    void printPersons(Person[] persons) {
//        for(Person p : persons)
//            System.out.format(new Locale("ru","RU"),"|%-10s|%2$td/%2$tm/%2$tY|%3$,10.2f|\n", p.name, p.birth, p.salary);
    }


    void sortAndPrint(List<Person> list) {
        list.stream().sorted(Comparator.comparing(e -> e.age)).forEach(System.out::print);
    }

    class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + " " + age;
        }
    }

    //Напишите метод с сигнатурой String reverseChars(String str), который возвращает символы строки str в обратном порядке.
    // Т.е. если на входе "12345" на выходе должно быть "54321"
    //Задачу надо решить методом рекурсии, циклы использовать нельзя!!!
    static String reverseChars(String str) {
        if (str.length() <= 1) return str;
        char[] s = str.toCharArray();
        String firstChar = String.valueOf(s[0]);
        String minusFirst = String.valueOf(s, 1, s.length - 1);

        return reverseChars(minusFirst) + firstChar;
    }

    //Создайте метод с сигнатурой String createFolder(String name),
    // который создает каталог name (один уровень) в текущей папке и возвращает полный родителя текущего каталога
    static String createFolder(String name) {
        File file = new File(name);
        file.mkdir();
        Path path = Paths.get(file.getName()).toAbsolutePath().resolve("../..").normalize();
        return path.toString();
    }

    static String folderCreate(String name) { // another way :)
        try {
            Files.createDirectory(Paths.get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Paths.get(name).toAbsolutePath().getParent().getParent().toString();
    }

    //Реализовать метод с сигнатурой boolean replaceF(String name) который заменяет в файле все F на f, в случае ошибки вернуть false.
    // Для реализации пользоваться методами java.nio.file.Files.
    static boolean replaceF(String name) {
        Path path = Paths.get(name);
        try {
//           byte[] fileToBytes = Files.readAllBytes(path);
//           for (int i = 0 ; i < fileToBytes.length; i++){
//               if (fileToBytes[i] == (byte)'F') fileToBytes[i]=(byte)'f';
//           }
//           Files.write(path,fileToBytes);
            String fileToString = Files.readString(path);
            fileToString = fileToString.replace('F', 'f');
            Files.writeString(path, fileToString);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /*
    Определен класс

    class User {
        public Integer id;
        public String name;
        User (Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

Создайте метод с сигнатурой public TreeSet<User> createSet() который создает и возвращает TreeSet так, что бы пользователи оказались упорядочены по убыванию id

Подсказка 1
Нужно создать TreeSet, передав в конструктор Comparator, переопределив метод compare для класса User


Подсказка 2
Используйте Integer.compare, для сравнения id, только с небольшим дополнением
     */
    class User {
        public Integer id;
        public String name;

        User(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public TreeSet<User> createSet() {
        TreeSet<User> result = new TreeSet<>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return -Integer.compare(o1.id, o2.id);
            }
        });
        return result;
    }


    public List<Integer> listAction(List<Integer> list) {
        list.remove(Collections.min(list));
        list.add(0, list.size());
        list.add(2, Collections.max(list));

        return list;
    }

    public List<Integer> filter(List<Integer> list) {
        List<Integer> result = new ArrayList<>(list);
        int sum = 0;
        for (Integer a : list) {
            sum += a;
        }
        sum /= 100;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) > sum) {
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    /*
    суммирует значения всех элементов списка
    удаляет из списка элементы, значение которых больше суммы, деленной на 100 (целочисленное деление)
    возвращает результирующий список 
     */
    public static void scanLines() {
        try (Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {
                String keyboardIn = scanner.nextLine();
                if (keyboardIn.contains("/stop")) {
                    scanner.close();
                    break;
                }
                if (keyboardIn.contains("Привет")) {
                    System.out.println("Здравствуйте!");
                    continue;
                }
                if (keyboardIn.contains("как дела")) {
                    System.out.println("Хорошо");
                } else {
                    System.out.println(keyboardIn);
                }
            }
        }
    }

    static String invertWords(String sentence) {
        String result = "";
        String[] arr = sentence.split(" ");
        for (int i = arr.length - 1; i > 0; i--) {
            result += arr[i] + " ";
        }
        result += arr[0];
        result = result.replace(" ", ".");
        return result;
    }

    public static String setStars(String filename) throws IOException {
        String outBytes = "";
        try (RandomAccessFile starIns = new RandomAccessFile(filename, "rw")) {
            for (long i = 9; i < starIns.length(); i = i + 10) {
                starIns.seek(i);
                outBytes += (char) starIns.read();
                starIns.seek(i);
                starIns.write('*');
            }
        } catch (Exception e) {
            throw new IOException(e.getClass().getName());
        }
        return outBytes;
    }

    static int addAsStrings(int n1, int n2) {
        String twoInt = n1 + "" + n2;
        return Integer.parseInt(twoInt);
    }

    static void getE() {
        double sumIncrease = 0;
        for (int i = 0; i < 10; i++) {
            double currentSum = 1 + sumIncrease;
            sumIncrease += currentSum / 1.0;

            System.out.println(1 + sumIncrease);
        }
    }

    static long factorial(long n) {
        long f = 1;
        if (n == 0) return 1;
        for (long i = 1; i <= n; i++) {
            f = i * f;
        }
        return f;
    }

    static int maxInt() {
        return Integer.MAX_VALUE;
    }

    static int toInt(String str) {
        return Integer.parseInt(str);
    }

    static class Figure {

    }

    static class Square extends Figure {
        private double side;

        public Square(double side) {
            this.side = side;
        }

        public double getSide() {
            return side;
        }
    }

    static class Round extends Figure {
        private double diameter;

        public Round(double diameter) {
            this.diameter = diameter;
        }

        public double getDiameter() {
            return diameter;
        }
    }

    /*
    Напишите метод с сигнатурой String figDetect(Figure fig), который
     - для квадрата возвращает "Сторона квадрата "+.side
    - для круга возвращает "Диаметр круга "+.diameter
    - для всех остальных классов "Неизвестная фигура"
     */
    static String figDetect(Figure fig) {
        if (fig == null) return "Неизвестная фигура";
        if (fig.getClass().equals(Square.class)) {
            return "Сторона квадрата " + ((Square) fig).side;
        }
        if (fig.getClass().equals(Round.class)) {
            return "Диаметр круга " + ((Round) fig).diameter;
        }
        return "Неизвестная фигура";
    }


}

