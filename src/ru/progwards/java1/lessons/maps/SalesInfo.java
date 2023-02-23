package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SalesInfo {
    private List<String> buyersData = new ArrayList<>();

    //  вернуть количество успешно загруженных строк.
    //  Если в строке более или менее 4-x полей, или количество и сумма не преобразуются в числа - эту строку не загружаем.
    public int loadOrders(String fileName) {
        int count = 0;
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                if (checkLine(currLine)) {
                    buyersData.add(currLine);
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static boolean checkLine(String line) {
        String[] account = line.split(",");
        if (account.length != 4) return false;
        try {
            if (Integer.valueOf(account[2].trim()).getClass().equals(Integer.class) && Double.valueOf(account[3].trim()).getClass().equals(Double.class))
                return true;
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    class Buy {
        String name;
        String good;
        int qty;
        double sum;

        Buy(String byr) {
            String[] buyer = byr.split(",");
            this.name = buyer[0].trim();
            this.good = buyer[1].trim();
            this.qty = Integer.valueOf(buyer[2].trim());
            this.sum = Double.valueOf(buyer[3].trim());
        }
    }

    // вернуть список товаров, отсортированный по наименованию товара.
    // В String - наименование товара, в Double - общая сумма продаж по товару
    public Map<String, Double> getGoods() {
        Map<String, Double> getGoods = new TreeMap<>();

        for (String b : buyersData) {
            Buy currByr = new Buy(b);
            if (getGoods.putIfAbsent(currByr.good, currByr.sum) != null)
                getGoods.replace(currByr.good, getGoods.get(currByr.good) + currByr.sum);
        }
        return getGoods;
    }

    //вернуть список покупателей, отсортированный по алфавиту.
    // В String  - ФИ, в Double - сумма всех покупок покупателя, в Integer - количество покупок
    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers = new TreeMap<>();
        Map<String, Double> nameAndSum = new TreeMap<>();//  имя покупателя + общая сумма его покупок
        Map<String, Integer> nameAndQty = new TreeMap<>();//  имя покупателя + общее количество его покупок

        for (String b : buyersData) {
            Buy currByr = new Buy(b);
            if (nameAndSum.putIfAbsent(currByr.name, currByr.sum) != null)
                nameAndSum.replace(currByr.name, nameAndSum.get(currByr.name) + currByr.sum);
            if (nameAndQty.putIfAbsent(currByr.name, currByr.qty) != null)
                nameAndQty.replace(currByr.name, nameAndQty.get(currByr.name) + currByr.qty);
        }
        for (Map.Entry<String,Double> entry : nameAndSum.entrySet()){
            String currName = entry.getKey();
            AbstractMap.SimpleEntry<Double,Integer> currEntry = new AbstractMap.SimpleEntry<>(nameAndSum.get(currName),nameAndQty.get(currName)) ;
            getCustomers.put(currName, currEntry);
        }
        return getCustomers;
    }

    public static void main(String[] args) {
        SalesInfo test = new SalesInfo();
        test.loadOrders("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\maps\\Sales.csv");
        for (Map.Entry e : test.getGoods().entrySet()) {
            System.out.println(e);
        }
    }
}

