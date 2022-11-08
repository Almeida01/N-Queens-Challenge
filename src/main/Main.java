package main;

import java.util.Scanner;

import main.models.Board;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Board b1 = new Board(sc.nextInt());
        System.out.println(b1.toString());
        sc.close();
    }
}