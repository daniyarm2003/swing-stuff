package com.lildan42.swingstuff.pathfinding.scenes;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.Renderable;
import com.lildan42.swingstuff.pathfinding.states.State;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public abstract class Scene implements State {
    protected final Simulation simulation;

    public Scene(Simulation simulation) {
        this.simulation = simulation;
    }

    public Vec2 getCameraOffset() {
        return Vec2.ZERO;
    }
}
