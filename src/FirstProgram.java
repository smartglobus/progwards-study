public class FirstProgram {
    public static void printJava() {
        System.out.println("На Java работает");
        System.out.println("3 миллиарда смартфонов");
        System.out.println("125 миллионов телевизоров");
        System.out.println("все Blu-Ray проигрыватели");
    }
    public static void printRussia(){
        System.out.print("Площадь России ");
        System.out.print(17125191);
        System.out.println(" квадратных километров");
        System.out.print("Население России ");
        System.out.print(146780720);
        System.out.println(" человек");
        System.out.print("ВВП (ППС) России ");
        System.out.print(28797);
        System.out.println("$ на душу населения");
        System.out.print("ВВП (номинал) России ");
        System.out.print(11289);
        System.out.println("$ на душу населения");
    }
    public static void main(String[] args){
        //printJava();
       // printRussia();
        //printJava();
        myprint(5);
    }
    static void myprint(int x) {
        System.out.print("x = " + x);
       // System.out.println(x);
    }
}
