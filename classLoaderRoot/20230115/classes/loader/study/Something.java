package classes.loader.study;

public class Something {
    static {
        System.out.println("инициализирован Something class");
    }
    public static void main(String[] args) {
        System.out.println("Something class");
    }
}
