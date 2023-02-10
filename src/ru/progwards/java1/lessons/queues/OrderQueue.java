package ru.progwards.java1.lessons.queues;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class OrderQueue {

    Queue<Order> queue = new PriorityQueue<>(new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            int result = Integer.compare(o1.getOrderClass(), o2.getOrderClass());
            if (result == 0) {
                return Integer.compare(o1.getNum(), o2.getNum());
            }
            return result;
        }
    });


    public void add(Order order) {
        queue.offer(order);
    }

    public Order get(){

        return queue.poll();
    }

    public static void main(String[] args) {
        Order first = new Order(25);
        Order second = new Order(17);
        Order third = new Order(77);
        System.out.println(first.getNum());
        System.out.println(second.getNum());
        System.out.println(third.getNum());
    }
}

class Order {
    private double sum;
    private int num;
    private static int orderNum; // счётчик заказов
    private int orderClass;

    public Order(double sum) {
        this.sum = sum;
        this.num = ++orderNum;

        if (sum <= 10000) {
            this.orderClass = 1;
        } else if (sum > 20000) {
            this.orderClass = 3;
        } else {
            this.orderClass = 2;
        }


    }

    public double getSum() {
        return sum;
    }

    public int getOrderClass() {
        return orderClass;
    }

    public int getNum() {
        return num;
    }
}