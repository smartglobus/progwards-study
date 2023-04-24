package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    List<Goods> listOfGoods;

    public void setGoods(List<Goods> list) {
        listOfGoods = new ArrayList<>(list);
    }

    //- вернуть список, отсортированный по наименованию
    public List<Goods> sortByName() {
        List<Goods> result = new ArrayList<>(listOfGoods);
        result.sort(Comparator.comparing(goods -> goods.name));
        return result;
    }

    // - вернуть список, отсортированный по артикулу, без учета регистра
    public List<Goods> sortByNumber() {
        List<Goods> result = new ArrayList<>(listOfGoods);
        result.sort(Comparator.comparing(goods -> goods.number.toUpperCase()));
        return result;
    }

    // - вернуть список, отсортированный по первым 3-м символам артикула, без учета регистра
    public List<Goods> sortByPartNumber() {
        List<Goods> result = new ArrayList<>(listOfGoods);
        result.sort(Comparator.comparing(goods -> goods.number.substring(0, 3).toUpperCase()));// случай с number меньше 3 символов не рассматривается
        return result;
    }

    // - вернуть список, отсортированный по количеству, а для одинакового количества, по артикулу, без учета регистра
    public List<Goods> sortByAvailabilityAndNumber() {
        List<Goods> result = new ArrayList<>(listOfGoods);
        result.sort((a, b) -> a.available == b.available ? a.number.compareToIgnoreCase(b.number) : Integer.compare(a.available, b.available));
        return result;
    }

    // - вернуть список, с товаром, который будет просрочен после указанной даты, отсортированный по дате годности
    public List<Goods> expiredAfter(Instant date) {
        return listOfGoods.stream().filter(g -> date.isAfter(g.expired)).sorted(Comparator.comparing(g -> g.expired)).collect(Collectors.toList());
    }

    // - вернуть список, с товаром, количество на складе которого меньше указанного, отсортированный по количеству
    public List<Goods> countLess(int count) {
        return listOfGoods.stream().filter(g -> g.available < count).sorted(Comparator.comparing(g -> g.available)).collect(Collectors.toList());
    }

    //  - вернуть список, с товаром, количество на складе которого больше count1 и меньше count2, отсортированный по количеству
    public List<Goods> countBetween(int count1, int count2) {
        return listOfGoods.stream().filter(g -> g.available > count1 && g.available < count2).sorted(Comparator.comparing(g -> g.available)).collect(Collectors.toList());
    }




    public static void main(String[] args) {
        List<Goods> test = new ArrayList<>();
        Instant Ins1 = Instant.from(ZonedDateTime.of(2023, 4, 5, 0, 0, 0, 0, ZoneId.systemDefault()));
        Instant Ins2 = Instant.from(ZonedDateTime.of(2023, 4, 6, 0, 0, 0, 0, ZoneId.systemDefault()));
        Instant Ins3 = Instant.from(ZonedDateTime.of(2023, 4, 7, 0, 0, 0, 0, ZoneId.systemDefault()));
        Instant Ins4 = Instant.from(ZonedDateTime.of(2023, 4, 8, 0, 0, 0, 0, ZoneId.systemDefault()));
        test.add(new Goods("pet", "number1", 4, 16.5, Instant.now()));
        test.add(new Goods("makarna", "Cake4", 5, 32.6, Ins1));
        test.add(new Goods("makarna", "Cake7", 555, 32.6, Ins2));
        test.add(new Goods("makarna", "cake1", 5, 32.6, Ins4));
        test.add(new Goods("flower", "pion", 25, 8, Instant.now()));
        GoodsWithLambda goodsWithLambda = new GoodsWithLambda();
        goodsWithLambda.setGoods(test);
        goodsWithLambda.sortByName().forEach(System.out::println);
        System.out.println();
        goodsWithLambda.sortByNumber().forEach(System.out::println);
        System.out.println();
        goodsWithLambda.sortByPartNumber().forEach(System.out::println);
        System.out.println();
        goodsWithLambda.sortByAvailabilityAndNumber().forEach(System.out::println);
        System.out.println();
        goodsWithLambda.expiredAfter(Ins3).forEach(System.out::println);
        System.out.println();
        goodsWithLambda.countLess(70).forEach(System.out::println);
        System.out.println();
        goodsWithLambda.countBetween(4,40).forEach(System.out::println);
        System.out.println();
    }

}

class Goods {
    String name;// - наименование
    String number; //- артикул
    int available; //- количество на складе
    double price; //- цена
    Instant expired; //- срок годности

    public Goods(String name, String number, int available, double price, Instant expired) {
        this.name = name;
        this.number = number;
        this.available = available;
        this.price = price;
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", available=" + available +
                ", price=" + price +
                ", expired=" + expired +
                '}';
    }
}