package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GoodsWithLambda {
    List<Goods> list;

    public void setGoods(List<Goods> list) {

    }

    //- вернуть список, отсортированный по наименованию
    public List<Goods> sortByName() {
        List<Goods> result = new ArrayList<>(list);
        result.sort(Comparator.comparing(goods -> goods.name));
        return result;
    }

    // - вернуть список, отсортированный по артикулу, без учета регистра
    public List<Goods> sortByNumber() {

        return null;
    }

    // - вернуть список, отсортированный по первым 3-м символам артикула, без учета регистра
    public List<Goods> sortByPartNumber() {

        return null;
    }

    // - вернуть список, отсортированный по количеству, а для одинакового количества, по артикулу, без учета регистра
    public List<Goods> sortByAvailabilityAndNumber() {

        return null;
    }

    // - вернуть список, с товаром, который будет просрочен после указанной даты, отсортированный по дате годности
    public List<Goods> expiredAfter(Instant date) {

        return null;
    }

    // - вернуть список, с товаром, количество на складе которого меньше указанного, отсортированный по количеству
    public List<Goods> сountLess(int count) {

        return null;
    }

    //  - вернуть список, с товаром, количество на складе которого больше count1 и меньше count2, отсортированный по количеству
    public List<Goods> сountBetween(int count1, int count2) {

        return null;
    }

}

class Goods {
    String name;// - наименование
    String number; //- артикул
    int available; //- количество на складе
    double price; //- цена
    Instant expired; //- срок годности
}