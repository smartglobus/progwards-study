package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class OrderProcessor {
    private Path allOrdersFolder;
    private List<Order> ordersList = new ArrayList<>(); // все заказы, загруженные через метод loadOrders
    int err = 0;

    public OrderProcessor(String startPath) {
        allOrdersFolder = Paths.get(startPath);
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        int faultCount = 0;
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/???-??????-????.csv");
        try {
            Files.walkFileTree(allOrdersFolder, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    LocalDateTime fileLastMdf = LocalDateTime.ofInstant(Files.getLastModifiedTime(file).toInstant(), ZoneId.systemDefault());
                    if (Files.isDirectory(file) && !folderMatchesTimePeriod(start, finish, fileLastMdf))//отсев папок, не содержащих искомых по дате создания заказов
                        return FileVisitResult.SKIP_SUBTREE;
                    if (pathMatcher.matches(file)) {
                        if (fileMatchesTimePeriod(start, finish, fileLastMdf)) {  // отсев заказов с датой создания вне периода start-finish
                            String[] orderFile = file.getFileName().toString().split("[-.]"); // вычленение из названия файла shopId, orderId и customerId
                            if (shopId == null || orderFile[0].equals(shopId)) {
                                Order order = new Order();
                                ordersList.add(order); // добавление заказа в общий список
                                order.shopId = orderFile[0];
                                order.orderId = orderFile[1];
                                order.customerId = orderFile[2];
                                order.datetime = fileLastMdf;
                                List<String> itemsList = Files.readAllLines(file);
                                try {
                                    for (String s : itemsList) {
                                        String[] itemLine = s.split(",");
                                        // проверка правильности формата строки с товаром. При ошибке обработка исключения
                                        if (itemLine.length != 3) throw new NumberFormatException();
                                        OrderItem orderItem = new OrderItem();
                                        orderItem.googsName = itemLine[0].trim();
                                        orderItem.count = Integer.parseInt(itemLine[1].trim());
                                        orderItem.price = Double.parseDouble(itemLine[2].trim());

                                        order.items.add(orderItem);
                                        order.items.sort(new Comparator<OrderItem>() {
                                            @Override
                                            public int compare(OrderItem o1, OrderItem o2) {
                                                return o1.googsName.compareTo(o2.googsName);
                                            }
                                        });
                                        order.sum += orderItem.price * orderItem.count;
                                    }
                                } catch (NumberFormatException e) {
                                    // очистить order и проигнорировать весь файл
                                    order.error = true; // ?????????????????????????????????????????????
                                    ordersList.remove(order);
                                    err++;
//                                    order.shopId = null;
//                                    order.orderId = null;
//                                    order.customerId = null;
//                                    order.datetime = null;
//                                    order.sum = 0;
//                                    order.items.clear();
                                    return FileVisitResult.CONTINUE;
                                }
                            }
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Order o : ordersList) {
            if (o.error) faultCount++;
        }
//        return faultCount;
        return err;
    }

    private boolean folderMatchesTimePeriod(LocalDate start, LocalDate finish, LocalDateTime folderLastModified) {

        if (start == null && finish == null) return true; // открытый временной диапазон
        if (start == null)
            if (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish))
                return true; // если время создания папки не позже, чем finish
        if (finish == null)
            if (folderLastModified.toLocalDate().isAfter(start.minusWeeks(1)) || folderLastModified.toLocalDate().isEqual(start.minusWeeks(1)))
                return true; // если время создания папки не раньше, чем на неделю от start
        if (start != null && finish != null)
            return (folderLastModified.toLocalDate().isAfter(start.minusWeeks(1)) || folderLastModified.toLocalDate().isEqual(start.minusWeeks(1))) &&
                    (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish));
        return false;
    }

    private boolean fileMatchesTimePeriod(LocalDate start, LocalDate finish, LocalDateTime folderLastModified) {

        if (start == null && finish == null) return true; // открытый временной диапазон
        if (start == null)
            if (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish))
                return true; // если время создания заказа не позже, чем finish
        if (finish == null)
            if (folderLastModified.toLocalDate().isAfter(start) || folderLastModified.toLocalDate().isEqual(start))
                return true; // если время создания заказа не раньше, чем start
        if (start != null && finish != null)
            return (folderLastModified.toLocalDate().isAfter(start) || folderLastModified.toLocalDate().isEqual(start)) &&
                    (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish));
        return false;
    }

    //выдать список заказов в порядке обработки (отсортированные по дате-времени), для заданного магазина. Если shopId == null, то для всех
    public List<Order> process(String shopId) {
        List<Order> result = new ArrayList<>();
        if (shopId != null) {
            for (Order order : ordersList) if (order.shopId.equals(shopId)) result.add(order);
        } else {
            result.addAll(ordersList);
        }
        result.sort(new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                if (o1.datetime.isBefore(o2.datetime)) return -1;
                if (o1.datetime.isAfter(o2.datetime)) return 1;
                return 0;
            }
        });
        return result;
    }

    // выдать информацию по объему продаж по магазинам (отсортированную по ключам): String - shopId, double - сумма стоимости всех проданных товаров в этом магазине
    public Map<String, Double> statisticsByShop() {
        Map<String, Double> statisticsByShop = new TreeMap<>();
        for (Order order : ordersList) {
            if (statisticsByShop.putIfAbsent(order.shopId, order.sum) != null) {
                Double newSum = statisticsByShop.get(order.shopId) + order.sum;
                statisticsByShop.replace(order.shopId, newSum);
            }
        }
        return statisticsByShop;
    }

    //выдать информацию по объему продаж по товарам (отсортированную по ключам): String - goodsName, double - сумма стоимости всех проданных товаров этого наименования
    public Map<String, Double> statisticsByGoods() {
        Map<String, Double> statisticsByGoods = new TreeMap<>();
        for (Order order : ordersList) {
            for (OrderItem orderItem : order.items) {
                if (statisticsByGoods.putIfAbsent(orderItem.googsName, orderItem.price * orderItem.count) != null) {
                    Double newSum = statisticsByGoods.get(orderItem.googsName) + orderItem.price * orderItem.count;
                    statisticsByGoods.replace(orderItem.googsName, newSum);
                }
            }
        }
        return statisticsByGoods;
    }

    public Map<LocalDate, Double> statisticsByDay() {

        return null;
    }

    public static void main(String[] args) {
        OrderProcessor orderProcessor = new OrderProcessor("C:\\Users\\User\\Documents\\Progwards\\test folder");
        System.out.println(orderProcessor.loadOrders(null, null, null));

        for (Order o : orderProcessor.process(null)) System.out.println(o.datetime);
    }
}

class Order {
    public String shopId;// - идентификатор магазина
    public String orderId;// - идентификатор заказа
    public String customerId;// - идентификатор покупателя
    public LocalDateTime datetime;// - дата-время заказа (из атрибутов файла - дата последнего изменения)
    public List<OrderItem> items = new ArrayList<>();// - список позиций в заказе, отсортированный по наименованию товара
    public double sum;// - сумма стоимости всех позиций в заказе
    boolean error;
}

class OrderItem {
    public String googsName; // - наименование товара
    public int count; // - количество
    public double price; // - цена за единицу
}
