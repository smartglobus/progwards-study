package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SalesInfo {
    List<String> buyersData = new ArrayList<>();


//  вернуть количество успешно загруженных строк.
//  Если в строке более или менее 4-x полей, или количество и сумма не преобразуются в числа - эту строку не загружаем.
    public int loadOrders(String fileName) {
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                if (checkLine(currLine)) buyersData.add(currLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static boolean checkLine(String line) {
        String[] account = line.split(",");
        if (account.length != 4) return false;
        return Integer.valueOf(account[2]).getClass().equals(Number.class) && Double.valueOf(account[3]).getClass().equals(Number.class);
    }

    class Buyer {
        String name;
        String good;
        int qty;
        double price;

        Buyer(String byr) {
            String[] buyer = byr.split(",");
            this.name = buyer[0];
            this.good = buyer[1];
            this.qty = Integer.valueOf(buyer[2]);
            this.price = Double.valueOf(buyer[4]);
        }
    }

    // вернуть список товаров, отсортированный по наименованию товара.
    // В String - наименование товара, в Double - общая сумма продаж по товару
    public Map<String, Double> getGoods() {
        Map<String, Double> getGoods = new TreeMap<>();

        for (String b : buyersData) {
            Buyer currByr = new Buyer(b);
            if (getGoods.putIfAbsent(currByr.good, currByr.price) != null)
                getGoods.replace(currByr.good, getGoods.get(currByr.good) + currByr.price);
        }
        return getGoods;
    }
}
