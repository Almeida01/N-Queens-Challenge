package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import main.models.Board;
import main.services.BestFirst;
import main.services.Ilayout;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Board b1 = new Board(sc.nextInt());
        System.out.println(Arrays.toString(b1.calculateFrontSlashCollisions(b1)));
//        Board b1 = new Board(sc.nextInt());
        /* for (int i = 4; i < 1000; i++) {
            BestFirst bf = new BestFirst();
            Board b1 = new Board(i);
            bf.solve(b1);
            System.out.println("Done: " + i);
        } */
        /*List<Ilayout> c = b1.children();
        for (int i = 0; i < c.size(); i++) {
            Board el = (Board) c.get(i);
            System.out.println("---- PAI " + i + " -----");
            System.out.println(el);
            System.out.println("---- FILHOS -----");
            List<Ilayout> x = el.children();
            x.forEach(System.out::println);
        }*/
        sc.close();
    }
}