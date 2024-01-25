package ru.progwards.java2.lessons.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintScan {
    private static Lock printLock = new ReentrantLock();
    private static Lock scanLock = new ReentrantLock();

    static void print(String name, int pages) throws InterruptedException {
        printLock.lock();
        for (int i = 1; i <= pages; i++) {
            System.out.println("print " + name + " page " + i);
            Thread.sleep(50);
        }
        printLock.unlock();
    }

    static void scan(String name, int pages) throws InterruptedException {
        scanLock.lock();
        for (int i = 1; i <= pages; i++) {
            System.out.println("             scan " + name + " page " + i);
            Thread.sleep(70);
        }
        scanLock.unlock();
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            String name = "docToPrint " + i;
            int pages = (int) (Math.random() * 5) + 1;

            Thread thread = new Thread(() -> {
                try {
                    print(name, pages);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        for (int i = 1; i <= 10; i++) {
            String name = "docToScan " + i;
            int pages = (int) (Math.random() * 5) + 1;

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        scan(name, pages);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
