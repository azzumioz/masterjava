package ru.javaops.masterjava.matrix;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * gkislin
 * 03.07.2016
 */
public class MainMatrix {
    private static final int MATRIX_SIZE = 1000;
    protected static final int THREAD_NUMBER = 10;

    private final static ExecutorService executor = Executors.newFixedThreadPool(MainMatrix.THREAD_NUMBER);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int[][] matrixA = MatrixUtil.create(MATRIX_SIZE);
        final int[][] matrixB = MatrixUtil.create(MATRIX_SIZE);

        double singleThreadSum = 0.;
        double singleOptimizeThreadSum = 0.;
        double concurrentThreadSum = 0.;
        int count = 1;
        while (count < 6) {
            System.out.println("Pass " + count);
            long start = System.currentTimeMillis();
            final int[][] matrixC = MatrixUtil.singleThreadMultiply(matrixA, matrixB);
            double duration = (System.currentTimeMillis() - start) / 1000.;
            out("Single1 thread time, sec: %.3f", duration);
            singleThreadSum += duration;

            start = System.currentTimeMillis();
            final int[][] matrixC2 = MatrixUtil.singleThreadMultiplyOptimize(matrixA, matrixB);
            duration = (System.currentTimeMillis() - start) / 1000.;
            out("Single2 thread time, sec: %.3f", duration);
            singleOptimizeThreadSum += duration;

            start = System.currentTimeMillis();
            final int[][] concurrentMatrixC = MatrixUtil.concurrentMultiply(matrixA, matrixB, executor);
            duration = (System.currentTimeMillis() - start) / 1000.;
            out("Concurrent thread time, sec: %.3f", duration);
            concurrentThreadSum += duration;

            if (!MatrixUtil.compare(matrixC, concurrentMatrixC) || !MatrixUtil.compare(matrixC, matrixC2)) {
                System.err.println("Comparison failed");
                break;
            }
            count++;
        }
        executor.shutdown();
        out("\nAverage single thread time, sec: %.3f", singleThreadSum / 5.);
        out("Average concurrent thread time, sec: %.3f", concurrentThreadSum / 5.);
        out("Average speed boost compares with single: %.3f", singleThreadSum/concurrentThreadSum);
        out("Average speed boost compares with singe optimize: %.3f", singleOptimizeThreadSum/concurrentThreadSum);
    }

    private static void out(String format, double ms) {
        System.out.println(String.format(format, ms));
    }

    private static void outMatrix (String name, int[][] matrix) {
        System.out.println(name);
        for (int[] row: matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

}
