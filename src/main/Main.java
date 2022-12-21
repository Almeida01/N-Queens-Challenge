package main;

import main.models.Board;
import main.services.BestFirst;
import main.services.Stopwatch;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nQueens = sc.nextInt();
        BestFirst bf = new BestFirst();
        Stopwatch stopwatch = new Stopwatch();
        bf.solve(new Board(nQueens));
        System.out.println(stopwatch.elapsedTime() / 1000 + " segundos");
        System.out.println("done");
        sc.close();
//        test(nQueens, 1);
    }
}