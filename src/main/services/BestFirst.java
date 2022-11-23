package main.services;

import java.util.*;

/**
 * BestFirst algorithm implementation following the Strategy design pattern, following ILayout interface.
 */
public class BestFirst {
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
     * State class. <br>
     * Each state represents a node in the search tree, containing a layout (context object) and a father state.
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
         * Heuristic value aka 'weight'
         */
        private int g;

        /**
         * Constructor class
         * @param l Context object
         * @param n Father state
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            g = layout.getG();
        }

        /**
         * Convert the state into a string. <br>
         * Current uses {@link #layout} toString's implementation.
         * @return String representing the state.
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Getter method of {@link #g}.
         * @return {@link #g}
         */
        public double getG() {
            return this.g;
        }

        /**
         * @return Returns a hashCode value for this object.
         */
        public int hashCode() {
            return toString().hashCode();
        }

        /**
         * Check if two states are equal based on the {@link #layout} only. <br>
         * In order to check if two states are equal, {@link #layout} equals method must be implemented.
         * @param o object to be compared.
         * @return True if {@code o} is equal to current state, false otherwise.
         */
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (this.getClass() != o.getClass())
                return false;
            State n = (State) o;
            return layout.equals(n.layout);
        }
    }

    /**
     * Generate all successors based on a given state. <br>
     * In order to do so, the {@link Ilayout#children()} method of the {@link State#layout} object is called.
     * @param n State from which we want to generate successors.
     * @return List <{@link State}> containing all the successors of the passed state {@code n};
     */
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

    /**
     * This method is called whenever we want to solve a problem.
     * @param s Context object that implements {@link Ilayout} interface.
     * @return Returns a {@link Ilayout} representing a context object that matches the solution, or {@code null} in case no solution was found.
     * @throws OutOfMemoryError when state search space exceeds memory limits.
     */
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
