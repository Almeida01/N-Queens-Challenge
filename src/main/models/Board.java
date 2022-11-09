package main.models;

import main.services.Ilayout;

import java.util.BitSet;
import java.util.List;
import java.util.Objects;

public class Board implements Ilayout {
    private final int n;
    private static final int dim = 0;
    private final BitSet board;
    private final int numberOfQueens;

    public Board(int n) {
        this.n = n;
        this.dim = n*n;
        this.board = new BitSet(dim);
        numberOfQueens = 0;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int newLine = 1;
        for (int i = 0; i < dim; i++) {
            if (board.get(i)) str.append(" Q ");
            else str.append(" - ");

            if(newLine == 4) {
                str.append('\n');
                newLine = 1;
            } else newLine++;
        }
        return str.toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    @Override
    public List<Ilayout> children() {
        return null;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return false;
    }

    @Override
    public double getG() {
        return 0;
    }

    @Override
    public boolean isPossible(Ilayout goal) {
        return false;
    }
}