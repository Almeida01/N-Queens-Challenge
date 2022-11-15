package main.services;

import java.util.*;

public class AStar {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private static State first;

    public static class State {
        private Ilayout layout;
        private State father;
        private int g; // Cost from start state to current
        private int h; // Cost from current state to final state
        private int f; // Estimated cost

        public State(Ilayout l, State n) {
            layout = l;
            father = n;

            if (father == null) {
                first = this;
                g = layout.getG();
            } else {
                g = first.g;
                h = layout.getG();
            }

            f = g + h;
        }

        public String toString() {
            return layout.toString() + " " + f;
        }

        public double getG() {
            return this.f;
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
        /*System.out.println("---- PAI -----");
        System.out.println(n);
        System.out.println("---- FILHOS -----");*/
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
//                System.out.println(e);
                State nn = new State(e, n);
                //System.out.println(nn);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Ilayout solve(Ilayout s) {
        abertos = new PriorityQueue<>(10,
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
                        if (!fechados.containsKey(cpy.layout)) {
                            fechados.put(cpy.layout, cpy);
                            abertos.add(cpy);
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

