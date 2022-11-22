package main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class IterativeDeepening {

    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private boolean cutoff;
    private static State first;

    public static class State {
        private Ilayout layout;
        private State father;
        int depth;
        private int g; // Cost from start state to current
        private int h; // Cost from current state to final state
        private int f; // Estimated cost

        public State(Ilayout l, State n, int depth) {
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
                //System.out.println(e);
                State nn = new State(e, n, n.depth + 1);
                //System.out.println(nn);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    private Ilayout solve(Ilayout problem) {
        int depth = 0;
        while (true) {
            Ilayout result = DLS(new State(problem, null, 0), problem, depth);
            if (!cutoff) return result;
        }
    }

    private Ilayout DLS(State state, Ilayout problem, int limit) {
        cutoff = false;
        if (state.layout.isGoal()) return problem;
        else if (state.depth != limit) return null;
        else {
            for (State suc : sucessores(state)) {
                Ilayout result = DLS(suc, problem, limit);
//                if (result == cutoff) cutoff = true;
            }
        }
        return null;
    }

}
