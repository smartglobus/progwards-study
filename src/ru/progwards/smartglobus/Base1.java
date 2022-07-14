package ru.progwards.smartglobus;

public class Base1 {
    public static void printJava(){
        String deals = "Хорошо идут дела";
        String learn = "Изучаю Java я!";
        String space = " ";

        System.out.println(deals);
        System.out.println(learn);
        System.out.print(deals);
        System.out.print(space);
        System.out.println(learn);
        System.out.print(learn);
        System.out.print(space);
        System.out.println(deals);
    }
    public static void printJava(String java1, String java2){
        System.out.println(java1);
        System.out.println(java2 + "!");
        System.out.print(java1);
        System.out.print(", ");
        System.out.print(java2);
        System.out.println("!");
        System.out.print(java2);
        System.out.print(", ");
        System.out.print(java1);
        System.out.println("!");

    }
    public static String plusJava(String message){
        return ("Java - " + message + " язык программирования");
    }
    public static void main(String[] args){
        printJava();
        printJava("Чтобы Java понимать","Надо функции писать");
        printJava("Буду, буду программистом", "Код пишу я чисто, чисто");
        String str;
        str=plusJava("самый популярный");
        System.out.println(str);
        str=plusJava("объектно-ориентированный");
        System.out.println(str);
        System.out.println(plusJava("очень интересный"));
    }
}
