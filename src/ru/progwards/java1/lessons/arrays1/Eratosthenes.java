package ru.progwards.java1.lessons.arrays1;

public class Eratosthenes {
    private boolean[] sieve;

    public Eratosthenes(int N) {
        this.sieve = new boolean[N];
        for (int i = 0; i < sieve.length; i++) {
            sieve[i] = true;
        }
        sift();
    }

    private void sift() {
        for (int i = 2; i < sieve.length; i++) {
            for (int j = 2; i * j < sieve.length; j++) {
                sieve[i * j] = false;
            }
            while (i < sieve.length - 1 && !sieve[i + 1]) {
                i++;
            }
        }
    }

    public boolean isSimple(int n) {
        return sieve[n];
    }

    public static void main(String[] args) {
        Eratosthenes test100 = new Eratosthenes(100);
        for (int count = 0; count < test100.getSieve().length; count++) {

            System.out.print(count+"  ");

            System.out.println(test100.getSieve()[count]);
        }
        System.out.println("\n" + test100.isSimple(13));
    }

    public boolean[] getSieve() {
        return sieve;
    }
}
