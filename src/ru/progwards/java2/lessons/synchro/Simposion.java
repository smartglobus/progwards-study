package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Simposion {
    static final int philosofersNumber = 5;
    static final int forksNumber = 5;
    Philosopher[] philosophers = new Philosopher[philosofersNumber + 1]; // нулевой член не используется
    Fork[] forks = new Fork[forksNumber + 1]; // нулевой член не используется

    public Simposion(long reflectTime, long eatTime) {

        for (int i = 1; i <= forksNumber; i++) {
            forks[i] = new Fork("fork " + i);
        }
        for (int i = 1; i <= philosofersNumber; i++) {
            int j = (i > 1) ? (i - 1) : philosofersNumber;
            if (i != philosofersNumber)// последнему философу вилки поменяны местами
                philosophers[i] = new Philosopher("philosopher " + i, forks[j], forks[i], reflectTime, eatTime);
            else
                philosophers[i] = new Philosopher("philosopher " + i, forks[i], forks[j], reflectTime, eatTime);
        }
    }

    void start() {
        for (int i = 1; i <= philosofersNumber; i++) {
            philosophers[i].start();
        }
    }

    void stop() {
        for (int i = 1; i <= philosofersNumber; i++) {
            philosophers[i].interrupt();
        }
    }

    void print() {
        for (int i = 1; i <= philosofersNumber; i++)
            System.out.println("Философ " + philosophers[i].name + ", ел " +
                    philosophers[i].eatSum + ", размышлял " + philosophers[i].reflectSum);
    }

    public static void main(String[] args) throws InterruptedException {
        Simposion simposion = new Simposion(4000, 4800);
        simposion.start();
        Thread.sleep(100000);
        simposion.stop();
        simposion.print();
    }
}


class Philosopher extends Thread {
    String name;
    private Fork right;// - вилка справа
    private Fork left;// - вилка слева
    private long reflectTime;// - время, которое философ размышляет в мс
    private long eatTime;// - время, которое философ ест в мс
    long reflectSum;// - суммарное время, которое философ размышлял в мс
    long eatSum;// - суммарное время, которое философ ел в мс


    public Philosopher(String name, Fork left, Fork right, long reflectTime, long eatTime) {
        this.name = name;
        this.right = right;
        this.left = left;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    void reflect() throws InterruptedException {
        System.out.println("размышляет " + name);
        Thread.sleep(500);
    }

    void eat() throws InterruptedException {
        System.out.println("ест " + name);
        Thread.sleep(500);
    }

    public boolean isRightForkFree() {
        return right.free;
    }

    public boolean isLeftForkFree() {
        return left.free;
    }

    public void setRightForkFree(boolean free) {
        this.right.free = free;
    }

    public void setLeftForkFree(boolean free) {
        this.left.free = free;
    }

    @Override
    public void run() {
        Lock lockL = new ReentrantLock();
        Lock lockR = new ReentrantLock();

        while (!interrupted()) {
            if (isLeftForkFree()) {
                boolean letsEat = false;
                if (lockL.tryLock()) {
                    setLeftForkFree(false);
                    if (isRightForkFree()) {
                        Thread eating = new Eating();
                        try {
                            letsEat = lockR.tryLock(20L, TimeUnit.MILLISECONDS);
                            setRightForkFree(false);
                            eating.start();
                            eatSum += eatTime;
                            Thread.sleep(eatTime);
                            eating.interrupt();
                        } catch (InterruptedException e) {
                            eating.interrupt();
                            return;
                        }
                        setRightForkFree(true);
                        lockR.unlock();
                    }
                    setLeftForkFree(true);
                    lockL.unlock();

                    //если удалось перекусить, можно освободить вилки и порефлексировать. Иначе охота за вилками продолжается.
                    if (letsEat) {
                        Thread rerfecting = new Reflecting();
                        try {
                            rerfecting.start();
                            reflectSum += reflectTime;
                            Thread.sleep(reflectTime);
                            rerfecting.interrupt();
                        } catch (InterruptedException e) {
                            rerfecting.interrupt();
                            return;
                        }
                    }
                }
            }
        }
    }

    class Eating extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    eat();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    class Reflecting extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    reflect();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}

class Fork {
    String forkName;
    boolean free;

    public Fork(String forkName) {
        this.forkName = forkName;
        free = true;
    }
}



