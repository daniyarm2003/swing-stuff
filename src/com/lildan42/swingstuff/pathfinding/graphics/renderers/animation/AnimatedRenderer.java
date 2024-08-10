package com.lildan42.swingstuff.pathfinding.graphics.renderers.animation;

public interface AnimatedRenderer {
    default void update(double dt) {
        this.update(1.0, dt);
    }

    void update(double speed, double dt);
}
