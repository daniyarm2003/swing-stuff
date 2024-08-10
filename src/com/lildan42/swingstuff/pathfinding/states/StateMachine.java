package com.lildan42.swingstuff.pathfinding.states;

public interface StateMachine<T extends State> {
    T getCurrent();
    T setCurrent(T newState);
}
