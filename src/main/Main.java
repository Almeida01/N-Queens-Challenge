package main;

import java.util.Scanner;

import main.models.Board;
import main.services.AStar;
import main.services.BestFirst;
import main.services.Ilayout;


public class Main {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        Board b1 = new Board(sc.nextInt());
//        BestFirst bf = new BestFirst();
//        System.out.println(bf.solve(b1));

        for (int i = 4; i < 2080; i += 40) {
            BestFirst bf = new BestFirst();
//            AStar as = new AStar();
            Board b1 = new Board(i);
            bf.solve(b1);
            System.out.println(i);
            //System.out.println();
        }
        //sc.close();
    }
}