package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import main.models.Board;
import main.services.AStar;
import main.services.BestFirst;
import main.services.Ilayout;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Board b1 = new Board(sc.nextInt());
        //BestFirst bf = new BestFirst();
        AStar as = new AStar();
        System.out.println(as.solve(b1));

        /*for (int i = 4; i < 1000; i++) {
            BestFirst bf = new BestFirst();
            Board b1 = new Board(i);
            System.out.println(b1);
            bf.solve(b1);
        }*/
        sc.close();
    }
}