package main;

import java.util.Scanner;

import main.models.Board;
import main.services.AStar;
import main.services.BestFirst;
import main.services.Ilayout;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        int nQueens = sc.nextInt();
        //System.out.println(b1);
        AStar bf = new AStar();
        Board result;

//        do {
//            Board b1 = new Board(nQueens);
//            System.out.println(b1);
//            result = (Board) bf.solve(b1);
//        }while (result == null);

//        System.out.println(result);
        for (int i = 4; i < 2080; i += 10) {
            long time = System.currentTimeMillis();
            do {
                Board b1 = new Board(i);
//                System.out.println(b1);
                result = (Board) bf.solve(b1);
            } while (result == null);
//            System.out.println(i);
            double totalTime = System.currentTimeMillis() - time;
            double totaltimeSec = (totalTime / 1000) % 60;
            System.out.println("i " + i + " /Time mili " + totalTime + "/Time Sec " + totaltimeSec);
        }

//            BestFirst bf = new BestFirst();
            /*AStar as = new AStar();
            Board b1 = new Board(i);
            if (as.solve(b1) == null) System.out.println("null");
            else System.out.println(i);
            *///System.out.println();
        //sc.close();
    }
}