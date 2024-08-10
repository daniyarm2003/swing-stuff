package com.lildan42.swingstuff.pathfinding.states;

public interface HistoricalStateMachine<T extends State> extends StateMachine<T> {
    boolean hasPrevious();
    T getPrevious();

    T push(T newState);
    T pop();
}
