package main;

import java.lang.management.ManagementFactory;
import java.util.Scanner;
import java.lang.management.ThreadMXBean;

import main.models.Board;
import main.services.BestFirst;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nQueens = sc.nextInt();
//        BestFirst bf = new BestFirst();

        test(4, 30);
        /*Board b1;
        Board result;
        for (int i = 4; i < 2080; i += 10) {
            long time = System.currentTimeMillis();
            do {
                b1 = new Board(i);
//                System.out.println(b1);
                result = (Board) bf.solve(b1);
            } while (result == null);
            double totalTime = System.currentTimeMillis() - time;
            double totaltimeSec = (totalTime / 1000) % 60;
            System.out.println("i " + i + " /Time mili " + totalTime + "/Time Sec " + totaltimeSec);
        }*/
        sc.close();
    }

    private static void test(int n, int trials) {

        Board b1;
        Board result;
        long startTime;
        long stopTime;
        long elapsedCPU = 0;
        double previousTime = 0;
        double newTime = 0;
        double doublingRatio = 0;
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] allThreadIds = threadMXBean.getAllThreadIds();


        int iteration = 1;
        for (int i = n; i < 2080; i *= 2) {
            for (int j = 0; j < trials; j++) {
                BestFirst bf = new BestFirst();
                long time = System.currentTimeMillis();
                startTime = getCPUTime(threadMXBean, allThreadIds);
                solveBoard(i);
                stopTime = getCPUTime(threadMXBean, allThreadIds);
                elapsedCPU += stopTime - startTime;
            }
            long totalTime = elapsedCPU / trials;
            long totaltimeSec = (totalTime / 1000) % 60;
            newTime = totalTime;

            if (previousTime > 0) doublingRatio = newTime / previousTime;
            else doublingRatio = 0;

            previousTime = newTime;
//            System.out.println("i " + i + " /Time mili " + totalTime + "/Time Sec " + totaltimeSec);
            System.out.println((iteration++) + "\t" + String.format("%05d", i) + "\t" + String.format("%08.4f", (newTime / 1E6)) + "\t" + String.format("%.3f", doublingRatio));
        }
    }

    private static long getCPUTime(ThreadMXBean threadMXBean, long[] allThreadIds) {
        long nano = 0;
        for (long id : allThreadIds) {
            nano += threadMXBean.getThreadCpuTime(id);
        }
        return nano;
    }

    private static void solveBoard(int n) {
        Board b1, result;
        BestFirst bf = new BestFirst();
        do {
            b1 = new Board(n);
//                System.out.println(b1);
            result = (Board) bf.solve(b1);
        } while (result == null);
    }
}