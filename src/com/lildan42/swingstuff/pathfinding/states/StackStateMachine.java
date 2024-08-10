package com.lildan42.swingstuff.pathfinding.states;

import java.util.Objects;
import java.util.Stack;

public class StackStateMachine<T extends State> implements HistoricalStateMachine<T> {

    private final Stack<T> prevStates = new Stack<>();
    private T curState;

    public StackStateMachine(T defaultState) {
        this.curState = defaultState;
    }

    @Override
    public boolean hasPrevious() {
        return !this.prevStates.isEmpty();
    }

    @Override
    public T getPrevious() {
        return this.prevStates.peek();
    }

    @Override
    public T push(T newState) {
        T prevState = this.setCurrent(newState);
        this.prevStates.push(prevState);

        return prevState;
    }

    @Override
    public T pop() {
        if(!this.hasPrevious()) {
            throw new IllegalStateException("previous state does not exist");
        }

        return this.setCurrent(this.prevStates.pop());
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
