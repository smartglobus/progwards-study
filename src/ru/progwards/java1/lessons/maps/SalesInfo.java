package ru.progwards.java1.lessons.maps;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SalesInfo {
    private List<String> buyersData = new ArrayList<>();
    private List<Buy> buys = new ArrayList<>();

    //  вернуть количество успешно загруженных строк.
    //  Если в строке более или менее 4-x полей, или количество и сумма не преобразуются в числа - эту строку не загружаем.
//    public int loadOrdersOld(String fileName) {
//        int count = 0;
//        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
//            while (scanner.hasNextLine()) {
//                String currLine = scanner.nextLine();
//                if (checkLine(currLine)) {
//                    buyersData.add(currLine);
//                    count++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

    public int loadOrders(String fileName) {
        int count = 0;
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                try {
                    buys.add(new Buy(currLine));
                    count++;
                } catch (Exception e) {
//                    continue;
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

        Buy(String byr) throws Exception{
            String[] buyer = byr.split(",");
            if (buyer.length != 4) throw new Exception();
            this.name = buyer[0].trim();
            this.good = buyer[1].trim();
            this.qty = Integer.parseInt(buyer[2].trim());
            this.sum = Double.parseDouble(buyer[3].trim());
        }
    }

    // вернуть список товаров, отсортированный по наименованию товара.
    // В String - наименование товара, в Double - общая сумма продаж по товару
    public Map<String, Double> getGoods() {
        Map<String, Double> getGoods = new TreeMap<>();

        for (Buy currBuy : buys) {
//            Buy currBuy = new Buy(b);
            if (getGoods.putIfAbsent(currBuy.good, currBuy.sum) != null)
                getGoods.replace(currBuy.good, getGoods.get(currBuy.good) + currBuy.sum);
        }
        return getGoods;
    }

    //вернуть список покупателей, отсортированный по алфавиту.
    // В String  - ФИ, в Double - сумма всех покупок покупателя, в Integer - количество покупок
//    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomersOld() {
//        Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers = new TreeMap<>();
//        Map<String, Double> nameAndSum = new TreeMap<>();//  имя покупателя + общая сумма его покупок
//        Map<String, Integer> nameAndQty = new TreeMap<>();//  имя покупателя + общее количество его покупок
//
//        for (String b : buyersData) {
//            Buy currBuy = new Buy(b);
//            if (nameAndSum.putIfAbsent(currBuy.name, currBuy.sum) != null)
//                nameAndSum.replace(currBuy.name, nameAndSum.get(currBuy.name) + currBuy.sum);
//            if (nameAndQty.putIfAbsent(currBuy.name, currBuy.qty) != null)
//                nameAndQty.replace(currBuy.name, nameAndQty.get(currBuy.name) + currBuy.qty);
//        }
//        for (Map.Entry<String,Double> entry : nameAndSum.entrySet()){
//            String currName = entry.getKey();
//            AbstractMap.SimpleEntry<Double,Integer> currEntry = new AbstractMap.SimpleEntry<>(nameAndSum.get(currName),nameAndQty.get(currName)) ;
//            getCustomers.put(currName, currEntry);
//        }
//        return getCustomers;
//    }

    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers = new TreeMap<>();
        for (Buy currBuy : buys) {
//            Buy currBuy = new Buy(b);
            if (getCustomers.putIfAbsent(currBuy.name, new AbstractMap.SimpleEntry<>(currBuy.sum, currBuy.qty)) != null){
                AbstractMap.SimpleEntry<Double,Integer> currEntry = getCustomers.get(currBuy.name);
                currEntry.setValue(currEntry.getValue() + currBuy.qty);
                AbstractMap.SimpleEntry<Double,Integer> newEntry = new AbstractMap.SimpleEntry<>((currEntry.getKey() + currBuy.sum), currEntry.getValue());
                getCustomers.replace(currBuy.name, newEntry);
            }
        }
        return getCustomers;
    }

    public static void main(String[] args) {
        SalesInfo test = new SalesInfo();
        System.out.println(test.loadOrders("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\maps\\Sales.csv"));

        for (Map.Entry e : test.getGoods().entrySet()) {
            System.out.println(e);
        }
    }
}

