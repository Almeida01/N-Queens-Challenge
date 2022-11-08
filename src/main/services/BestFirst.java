package main.services;

import java.util.*;

public class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    protected static Ilayout objective;

    public static class State {
        private Ilayout layout;
        private State father;
        private double g = -1;
        private int level = 0;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null) {
                level = father.level + 1;
                g = level;
            }
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return this.g;
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (this.getClass() != o.getClass())
                return false;
            State n = (State) o;
            return layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public int solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(20000,
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));

        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;
        try {

        } catch (OutOfMemoryError error) {
            System.out.println("error");
            System.exit(-1);
        }
        return -1;
    }
}
