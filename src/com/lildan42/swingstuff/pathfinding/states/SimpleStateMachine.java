package com.lildan42.swingstuff.pathfinding.states;

import java.util.Objects;

public class SimpleStateMachine<T extends State> implements StateMachine<T> {

    private T curState;

    public SimpleStateMachine(T defaultState) {
        this.curState = defaultState;
    }

    @Override
    public T getCurrent() {
        return this.curState;
    }

    @Override
    public T setCurrent(T newState) {
        Objects.requireNonNull(newState);

        T prevState = this.curState;

        if(prevState != null) {
            prevState.exit();
        }

        this.curState = newState;
        this.curState.enter();

        return prevState;
    }
}
