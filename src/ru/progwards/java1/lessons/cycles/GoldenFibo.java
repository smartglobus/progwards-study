package ru.progwards.java1.lessons.cycles;

public class GoldenFibo {

    public static int fiboNumber(int n) {
        if (n == 1) return 1;

        int F = 0;
        int lastF = 1;
        int preLastF = 0;

        for (int i = 1; i < n; i++) {
            F = preLastF + lastF;
            preLastF = lastF;
            lastF = F;
        }
        return F;
    }

    public static boolean isGoldenTriangle(int a, int b, int c) {
        int base = 0;
        int side = 0;
        if (a == b || b == c || a == c) {
            if (a == b) {
                base = c;
                side = a;
            } else if (a == c) {
                base = b;
                side = a;
            } else {
                base = a;
                side = b;
            }

            if ((double) side / base > 1.61703 && (double) side / base < 1.61903) return true;
        }
        return false;
    }


    public static void main(String[] args) {
        for (int i = 1; i <= 15; i++) {
            System.out.println(fiboNumber(i));
        }
        int n = 1;
        while (fiboNumber(n + 1) <= 100) {
            if (isGoldenTriangle(fiboNumber(n), fiboNumber(n + 1), fiboNumber(n + 1)))
                System.out.println(fiboNumber(n) + "  " + fiboNumber(n + 1) + "  " + fiboNumber(n + 1));
            n++;
        }

    }
}
