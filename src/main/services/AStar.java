package main.services;

import java.util.*;

/**
 * A* algorithm (BestFirst variant) implementation following the Strategy design pattern and ILayout interface.<br>
 */
public class AStar {
    /**
     * Queue of open states (states to be explored).
     */
    protected Queue<State> abertos;
    /**
     * Map of closed states (states that have been explored). <br>
     * Avoid exploring states that have been previously explored.
     */
    private Map<Ilayout, State> fechados;
    /**
     * Current state being evaluated.
     */
    private State actual;

    /**
     * The head of the state search tree.
     */
    private static State first;

    /**
     * State class. <br>
     * Each state represents a node in the search tree, containing an {@link Ilayout} context object and a father state.
     */
    public static class State {
        /**
         * A variable representing the context which our algorithm is solving to.<br>
         * The context must implement the {@link Ilayout} interface, in order to be solvable.
         */
        private Ilayout layout;
        /**
         * Father state.
         */
        private State father;
        /**
         * Heuristic value representing cost from start state to current.
         */
        private int g; // Cost from start state to current
        /**
         * Heuristic value representing cost from current state to final.
         */
        private int h; // Cost from current state to final state
        private int f; // Estimated cost

        public State(Ilayout l, State n) {
            layout = l;
            father = n;

            if (father == null) {
                first = this;
                g = layout.getG();
            } else {
                g = first.g - layout.getG();
                h = layout.getG();
            }

            f = g + h;
        }

        public String toString() {
            return layout.toString() + " " + f;
        }

        public double getF() {
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
        abertos = new PriorityQueue<>(1000,
                (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));

        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;
        try {
            while (true) {
                if (abertos.isEmpty()) return null;
                actual = abertos.poll(); // Poll retrieves and removes the head of the list
                if (actual.layout.isGoal()) return actual.layout;
                else {
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

