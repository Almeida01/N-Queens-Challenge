package main;

import java.util.Scanner;

import main.models.Board;
import main.services.AStar;
import main.services.BestFirst;
import main.services.Ilayout;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nQueens = sc.nextInt();
        Board b1 = new Board(nQueens);
        //System.out.println(b1);
        AStar bf = new AStar();
        System.out.println(bf.solve(b1));
//        while (true) {
//            Board b1 = new Board(nQueens);
//            //System.out.println(b1);
//            AStar bf = new AStar();
//            System.out.println(bf.solve(b1));
//
//        }

        /*for (int i = 6; i < 2080; i += 6) {
//            BestFirst bf = new BestFirst();
            AStar as = new AStar();
            Board b1 = new Board(i);
            if (as.solve(b1) == null) System.out.println("null");
            else System.out.println(i);
            //System.out.println();
        }*/
        //sc.close();
    }
}