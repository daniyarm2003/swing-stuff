package com.lildan42.swingstuff.pathfinding.simobjects.states;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.states.State;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;

public abstract class SimulationObjectState<T extends SimulationObject> implements State {
    protected final T object;
    protected final StateMachine<SimulationObjectState<T>> stateMachine;

    public SimulationObjectState(T object, StateMachine<SimulationObjectState<T>> stateMachine) {
        this.object = object;
        this.stateMachine = stateMachine;
    }

    public abstract boolean handleStateChanges(double dt);
}
