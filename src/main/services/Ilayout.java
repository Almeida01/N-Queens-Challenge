package main.services;

import java.util.List;

public interface Ilayout {
    /**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    /**
     * @return true if the receiver equals the argument l;
     * <p>
     * return false otherwise.
     */

    boolean isGoal();

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    int getG();

    /**
     * @return if is possible to achieve goal from the current state
     */
    boolean isPossible(Ilayout goal);
}
