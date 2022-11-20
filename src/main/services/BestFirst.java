package main.services;

import java.util.*;

public class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;

    public static class State {
        private Ilayout layout;
        private State father;
        private int g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            g = layout.getG();
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
//        System.out.println("---- PAI -----");
//        System.out.println(n);
//        System.out.println("---- FILHOS -----");
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Ilayout solve(Ilayout s) {
        abertos = new PriorityQueue<>(100,
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));

        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;
        try {
            while (true) {
                if (abertos.isEmpty()) return null;
                actual = abertos.poll(); // Poll retrieves and removes the head of the list
                if (actual.layout.isGoal()) {
                    return actual.layout;
                } else {
                    fechados.put(actual.layout, actual);
                    sucs = sucessores(actual);
                    for (State cpy : sucs) {
//                        System.out.println(cpy);
                        if (!fechados.containsKey(cpy.layout)) {
                            abertos.add(cpy);
                            fechados.put(cpy.layout, cpy);
                        }
                    }
                }
            }
        } catch (OutOfMemoryError error) {
            System.out.println("error");
            System.exit(-1);
        }
        return null;
    }
}
