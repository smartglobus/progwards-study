package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.util.*;

public class OrderProcessor {
    private Path allOrdersFolder;
    private List<Order> ordersList = new ArrayList<>(); // все заказы, загруженные через метод loadOrders
    private int faultCount = 0;

    public OrderProcessor(String startPath) {
        allOrdersFolder = Paths.get(startPath);
    }

    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*/???-??????-????.csv");
        try {
            Files.walkFileTree(allOrdersFolder, Collections.emptySet(), 2, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    LocalDateTime fileLastMdf = LocalDateTime.ofInstant(Files.getLastModifiedTime(file).toInstant(), ZoneId.systemDefault());
                    if (Files.isDirectory(file) && !folderMatchesTimePeriod(start, finish, fileLastMdf))//отсев папок, не содержащих искомых по дате создания заказов
                        return FileVisitResult.SKIP_SUBTREE;
                    if (pathMatcher.matches(allOrdersFolder.relativize(file))) {
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
                                        // проверка правильности формата строки с товаром. При ошибке извлечения данных - обработка исключения
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
                                } catch (NumberFormatException e) { // удаление order, соответствующего файлу с ошибочным содержимым
                                    ordersList.remove(order);
                                    faultCount++;
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
        return faultCount;
    }

    private boolean folderMatchesTimePeriod(LocalDate start, LocalDate finish, LocalDateTime folderLastModified) {

        if (start == null && finish == null) return true; // открытый временной диапазон
        if (start == null)
            if (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish))
                return true; // если дата создания папки не позже, чем finish
        if (finish == null)
            if (folderLastModified.toLocalDate().isAfter(start.minusWeeks(1)) || folderLastModified.toLocalDate().isEqual(start.minusWeeks(1)))
                return true; // если дата создания папки не раньше, чем на неделю от start
        if (start != null && finish != null)
            return (folderLastModified.toLocalDate().isAfter(start.minusWeeks(1)) || folderLastModified.toLocalDate().isEqual(start.minusWeeks(1))) &&
                    (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish));
        return false;
    }

    private boolean fileMatchesTimePeriod(LocalDate start, LocalDate finish, LocalDateTime folderLastModified) {

        if (start == null && finish == null) return true; // открытый временной диапазон
        if (start == null)
            if (folderLastModified.toLocalDate().isBefore(finish) || folderLastModified.toLocalDate().isEqual(finish))
                return true; // если дата создания заказа не позже, чем finish
        if (finish == null)
            if (folderLastModified.toLocalDate().isAfter(start) || folderLastModified.toLocalDate().isEqual(start))
                return true; // если дата создания заказа не раньше, чем start
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

    // - выдать информацию по объему продаж по дням (отсортированную по ключам): LocalDate - конкретный день, double - сумма стоимости всех проданных товаров в этот день
    public Map<LocalDate, Double> statisticsByDay() {
        Map<LocalDate, Double> statisticsByDay = new TreeMap<>();
        for (Order order : ordersList) {
            if (statisticsByDay.putIfAbsent(order.datetime.toLocalDate(), order.sum) != null) {
                Double newSum = statisticsByDay.get(order.datetime.toLocalDate()) + order.sum;
                statisticsByDay.replace(order.datetime.toLocalDate(), newSum);
            }
        }
        return statisticsByDay;
    }

    public static void main(String[] args) {
        OrderProcessor orderProcessor = new OrderProcessor("C:\\Users\\User\\Documents\\Progwards\\test folder");
        LocalDate finish = LocalDate.of(2022,8,9);
        System.out.println(orderProcessor.loadOrders(null, finish, null));
        for (Order o : orderProcessor.process(null))
            System.out.println(o.datetime);
        Path path = Paths.get("C:\\Users\\User\\Documents\\Progwards\\test folder\\folder 1\\S01-P01X02-0002.csv");
        try {
            System.out.println(Files.setLastModifiedTime(path, FileTime.from(Instant.ofEpochSecond(LocalDateTime.of(2020,2,2,0,2).toEpochSecond(ZoneOffset.UTC)))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Paths.get("C:\\Users\\User\\Documents\\Progwards\\test folder").relativize(Paths.get("C:\\Users\\User\\Documents\\Progwards\\test folder\\folder 1\\S01-P01X02-0002.csv")));
    }

}

class Order {
    public String shopId;// - идентификатор магазина
    public String orderId;// - идентификатор заказа
    public String customerId;// - идентификатор покупателя
    public LocalDateTime datetime;// - дата-время заказа (из атрибутов файла - дата последнего изменения)
    public List<OrderItem> items = new ArrayList<>();// - список позиций в заказе, отсортированный по наименованию товара
    public double sum;// - сумма стоимости всех позиций в заказе
}

class OrderItem {
    public String googsName; // - наименование товара
    public int count; // - количество
    public double price; // - цена за единицу
}
