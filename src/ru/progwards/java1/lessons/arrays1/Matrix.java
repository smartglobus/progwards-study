package ru.progwards.java1.lessons.arrays1;

public class Matrix {
    private int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int maxInRow(int num) {
        int maxIR = Integer.MIN_VALUE;

        for (int i = 0; i < matrix[num].length; i++) {
            if (matrix[num][i] > maxIR) {
                maxIR = matrix[num][i];
            }
        }
        return maxIR;
    }

    public int maxInCol(int num) {
        int maxIC = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length < num + 1) {
                continue;
            }
            if (matrix[i][num] > maxIC) {
                maxIC = matrix[i][num];
            }
        }
        return maxIC;
    }

    public int max() {
        int maxNum = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if (maxInRow(i) > maxNum) {
                maxNum = maxInRow(i);
            }
        }
        return maxNum;
    }

    public boolean isMatrix() {
        int rowLeigth = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != rowLeigth) {
                return false;
            }
        }
        return true;
    }

    public int[][] transposition() {
        if (!isMatrix()) {
            return null;
        }
        int[][] transMatrix = new int[matrix[0].length][matrix.length];
        System.out.println(transMatrix.length);
        System.out.println(transMatrix[0].length);
        System.out.println("");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
               transMatrix[j][i] = matrix[i][j];
            }
        }
        return transMatrix;
    }

    public static void main(String[] args) {
        int[][] abc = {{-1, 2, 36, 4, 555}, {4, 48, 0}, {65, 128, -45, -0, 30, 3, 4, 5}};
        Matrix multiTest = new Matrix(abc);
        //int[][] trueMatrix = new int[3][4];
        int[] a = {1, 2, 3, 4};
        int[] b = {5, 6, 7, 8};
        int[] c = {9, 10, 11, 12};
        int[][]   trueMatrix = {a,b,c};
        Matrix threeByFour = new Matrix(trueMatrix);

//        System.out.println(multiTest.maxInRow(0));
//        System.out.println(multiTest.maxInRow(1));
//        System.out.println(multiTest.maxInRow(2));
//        System.out.println(multiTest.maxInCol(5));
//        System.out.println(multiTest.maxInCol(1));
//        System.out.println(multiTest.max());
//        System.out.println(multiTest.isMatrix());
        System.out.println(threeByFour.isMatrix());

        System.out.println(trueMatrix.length);
        System.out.println(trueMatrix[0].length);
        System.out.println("");
        for (int[]row:threeByFour.getMatrix()
             ) {
            for (int value: row
                 ) {
                System.out.println(value);
            }
        }
        System.out.println("");
        threeByFour.transposition();
        for (int[]row:threeByFour.getMatrix()
        ) {
            for (int value: row
            ) {
                System.out.println(value);
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
