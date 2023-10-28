package ru.progwards.smartglobus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ExploreGenerics<T> {


    //    private byte lastDescriptorByte() {
//        int result = 0;
//        for (boolean bl : linkDescript) {
//            result <<= 1;
//            result += bl ? 1 : 0;
//        }
//        result <<= (8 - linkDescript.size());
//
//        return (byte) result;
//    }
    public static String sayHello(String hello) {
        return "Hello".equals(hello) ? "Привет" : "Good evening".equals(hello) ? "Добрый вечер" : "Добрый день";
    }

    private static byte descriptorByte() {
        List<Boolean> linkDescript = new ArrayList<>(List.of(true, true, true, true, true, true, true, true));

        int result = 0;
        for (boolean bl : linkDescript) {
            result <<= 1;
            result += bl ? 1 : 0;
        }
        result <<= (8 - linkDescript.size()); // сделано для последнего descriptorByte для п-ти < 8 байтов
        linkDescript.clear();
        return (byte) result;
    }

    public static void main(String[] args) {
//        System.out.println(descriptorByte());
//
//        Path path = Paths.get("C:\\Users\\User\\Pictures\\testingArc 3_98Mb.bmp.arch");
//        Path parent = path.getParent();
//        String inputFileName = path.getFileName().toString();
//        String[] fileStr = inputFileName.split("\\.arch");
//        for (String s:fileStr) System.out.println(s);
//        String[] initialFileName = fileStr[0].split("\\.");
//        initialFileName[initialFileName.length-2] += "(unpacked).";
//        String newName="";
//        for (String s : initialFileName) newName +=s;
//        String outFile = parent.resolve(newName).toString();
//        System.out.println(Integer.toBinaryString(31));
//        System.out.println(System.getProperty("file.encoding"));
//List<Integer> list = new ArrayList<>();
//        System.out.println("abcde".substring(2,3));
//        System.out.println(Integer.MIN_VALUE - 1);
//        System.out.println(Integer.MAX_VALUE);

//Scanner scanner = new Scanner(System.in);
//try {
//    System.out.println("Enter a :");
//    int aR = Integer.parseInt(scanner.nextLine().trim());
//    System.out.println("Enter b :");
//    int bR = Integer.parseInt(scanner.nextLine().trim());
//    System.out.println("a + b = " + (aR + bR));
//} catch (NumberFormatException e) {
//    System.out.println("NaN");
//    scanner.close();
//}


//        List<Integer> lst = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
//        for (Integer i : lst) {
//            if (i == 5) lst.remove(5);
//            break;// if no break - Exception in thread "main" java.util.ConcurrentModificationException !!!
//        }
//        List<Integer> randList = new ArrayList<>();
//        for (int i = 0; i < 1000000; i++) {
//            randList.add((int) (Math.random() * 1000000));
//        }
//        for (int i = 0; i < 5; i++) {
//            long start = System.currentTimeMillis();
//            randList.sort(Comparator.comparing(integer -> integer));
//            System.out.println("Sort #" + (i + 1) + ":  " + (System.currentTimeMillis() - start));
//        }
        for (int i = 0; i < 450; i++) {
            System.out.print((byte)i + "_");
        }

    }


}
