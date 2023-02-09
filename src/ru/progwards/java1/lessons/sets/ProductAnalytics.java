package ru.progwards.java1.lessons.sets;

import java.util.*;

class Product {
    private String code;

    public Product(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

class Shop {
    private List<Product> products;

    public Shop(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}

public class ProductAnalytics {

    private List<Shop> shops;
    private List<Product> products;

    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.shops = shops;
        this.products = products;
    }

    public Set<Product> existInAll() { //  - товары из products, которые имеются во всех магазинах
        Set<Product> existInAll = new HashSet<>();
        for (Shop s : shops) existInAll.addAll(s.getProducts()); // добавили все имеющиеся в магазинах продукты
        for (Shop s : shops) existInAll.retainAll(s.getProducts()); // поочередно удалили все, кроме нужных
        return existInAll;
    }

    public Set<Product> existAtListInOne() { //  - товары из products, которые имеются хотя бы в одном магазине
        Set<Product> existAtListInOne = new HashSet<>();
        for (Shop s : shops) {
            Set<Product> oneShopAssortment = new HashSet<>(s.getProducts());
            oneShopAssortment.retainAll(products);
            existAtListInOne.addAll(oneShopAssortment);
        }
        return existAtListInOne;
    }

    public Set<Product> notExistInShops() { //  - товары из products, которых нет ни в одном магазине
        Set<Product> notExistInShops = new HashSet<>(products);
        for (Shop s : shops) notExistInShops.removeAll(s.getProducts());
        return notExistInShops;
    }

    public Set<Product> existOnlyInOne() { // - товары из products, которые есть только в одном магазине

        Iterator<Shop> shopIterator = shops.iterator();
        Set<Product> currShop = new HashSet<>(shopIterator.next().getProducts()); // ассортимент текущего магазина
        Set<Product> existOnlyInOne = new HashSet<>(currShop); //  результат
        Set<Product> currResult = new HashSet<>(currShop); //  текущий результат, а также буфер для обновления commonProd
        Set<Product> commonProd = new HashSet<>(); //  продукты, имеющиеся в двух и более магазинах

        while (shopIterator.hasNext()) {
            currShop = new HashSet<>(shopIterator.next().getProducts());

            currResult.retainAll(currShop); // !!! временно становится здесь списком текущих совпадений при обновлении commonProd
            commonProd.addAll(currResult);

            existOnlyInOne = symDiffProd(existOnlyInOne, currShop);
            existOnlyInOne.removeAll(commonProd);

            currResult.clear();
            currResult.addAll(existOnlyInOne);
        }
        existOnlyInOne.retainAll(products);
        return existOnlyInOne;
    }

    // вычисление симметричной разницы множеств Product
    public static Set<Product> symDiffProd(Set<Product> set1, Set<Product> set2) {
        Set<Product> symDiffSet = new HashSet<>(set1);
        symDiffSet.addAll(set2);
        Set<Product> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        symDiffSet.removeAll(intersection);
        return symDiffSet;
    }

    public static void main(String[] args) {
        List<Shop> shops = new ArrayList<>();
        List<Product> products = new ArrayList<>(); // sugar, meat, fish, eggs, cheese

        Product sugar = new Product("sugar");
        Product oil = new Product("oil");
        Product bread = new Product("bread");
        Product meat = new Product("meat");
        Product fish = new Product("fish");
        Product candy = new Product("candy");
        Product eggs = new Product("eggs");
        Product pan = new Product("pan");
        Product cup = new Product("cup");
        Product cheese = new Product("cheese");

        products.add(sugar);
        products.add(meat);
        products.add(fish);
        products.add(eggs);
        products.add(cheese);

        List<Product> sh1 = new ArrayList<>();
        List<Product> sh2 = new ArrayList<>();
        List<Product> sh3 = new ArrayList<>();
        List<Product> sh4 = new ArrayList<>();

        Shop shop1 = new Shop(sh1);
        Shop shop2 = new Shop(sh2);
        Shop shop3 = new Shop(sh3);
        Shop shop4 = new Shop(sh4);

        sh1.add(sugar);
        sh1.add(oil);
        sh1.add(bread);
        sh1.add(meat);

        sh2.add(sugar);
        sh2.add(fish);
        sh2.add(candy);
        sh2.add(eggs);
        sh2.add(meat);

        sh3.add(sugar);
        sh3.add(eggs);
        sh3.add(pan);
        sh3.add(cup);

        sh4.add(sugar);
        sh4.add(pan);
        sh4.add(cup);
        sh4.add(cheese);


        shops.add(shop1);
        shops.add(shop2);
        shops.add(shop3);
        shops.add(shop4);

        ProductAnalytics testPA = new ProductAnalytics(products, shops);
        Set<Product> set = testPA.existOnlyInOne();
        for (Product p : set) System.out.println(p.getCode());

    }
}


/*
2.1 Создать класс Product - товар,
2.2. Создать private String code - уникальный артикул товара
2.3 Создать конструктор public Product(String code)
2.4 Метод public String getCode()
2.5 Создать класс Shop - магазин
2.6 Создать private List<Product> products - товары имеющиеся в магазине
2.7 Создать конструктор public Shop(List<Product> products)
2.8 Создать метод public List<Product> getProducts()

2.9 Создать класс ProductAnalytics
2.10 Создать private List<Shop> shops - список магазинов
2.11 Создать private List<Product> products - список всех имеющихся в ассортименте товаров.
Все товары, присутствующие в магазинах, обязательно присутствуют в products, но так же тут могут быть и товары, которых нет в магазинах
2.12 Создать конструктор  public ProductAnalytics(List<Product> products, List<Shop> shops)
2.13 Создать функцию public Set<Product> existInAll() - товары из products, которые имеются во всех магазинах
2.14 Создать функцию public Set<Product> existAtListInOne() - товары из products, которые имеются хотя бы в одном магазине
2.15 Создать функцию public Set<Product> notExistInShops() - товары из products, которых нет ни в одном магазине
2.16 Создать функцию public Set<Product> existOnlyInOne() - товары из products, которые есть только в одном магазине
 */