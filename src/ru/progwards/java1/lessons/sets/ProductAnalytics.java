package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAnalytics {

    List<Shop> shops;
    List<Product> products;

    public ProductAnalytics(List<Shop> shops, List<Product> products) {
        this.shops = shops;
        this.products = products;
    }

    public Set<Product> existInAll() { //  - товары из products, которые имеются во всех магазинах
        Set<Product> existInAll = new HashSet<>();
        for (Shop s : shops) existInAll.addAll(s.getProducts());
        existInAll.retainAll(products);
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
        for (Shop s : shops){
            notExistInShops.removeAll(s.getProducts());
        }
        return notExistInShops;
    }

    public Set<Product> existOnlyInOne() { // - товары из products, которые есть только в одном магазине
        Set<Product> existOnlyInOne = new HashSet<>();


        return existOnlyInOne;
    }

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