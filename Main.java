import java.util.Scanner;

import models.Board;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Board b1 = new Board(sc.nextInt());
        b1.addQueen(0, 0);
        System.out.println(b1);

        sc.close();
    }
}