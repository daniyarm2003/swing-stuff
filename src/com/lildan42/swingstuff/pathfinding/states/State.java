package com.lildan42.swingstuff.pathfinding.states;

import com.lildan42.swingstuff.pathfinding.graphics.Renderable;

public interface State extends Renderable {
    void enter();
    void exit();

    void update(double dt);
}
