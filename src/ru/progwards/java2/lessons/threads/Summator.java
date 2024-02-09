package ru.progwards.java2.lessons.threads;

import java.math.BigInteger;

public class Summator {
    private int count;
    BigInteger result = BigInteger.ZERO;

    public Summator(int count) {
        this.count = count;
    }

    public BigInteger sum(BigInteger number) throws InterruptedException {
        Thread[] threads = new Thread[count];
        BigInteger step = number.divide(BigInteger.valueOf(count));

        for (int i = 0; i < count; i++) {
            int j = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    BigInteger from = BigInteger.valueOf(j).multiply(step);
                    BigInteger to = (j == count - 1) ? number : from.add(step);
                    partSum(from.add(BigInteger.ONE), to);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < count; i++) threads[i].join();
        return result;
    }

    synchronized void partSum(BigInteger from, BigInteger to) {
        int n = to.subtract(from).intValue() + 1;
        for (int i = 0; i < n; i++) result = result.add(from.add(BigInteger.valueOf(i)));
    }

    public static void main(String[] args) throws InterruptedException {
        Summator summator = new Summator(7);
        System.out.println(summator.sum(BigInteger.valueOf(13)));
    }
}
