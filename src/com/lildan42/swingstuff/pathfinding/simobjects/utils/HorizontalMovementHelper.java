package com.lildan42.swingstuff.pathfinding.simobjects.utils;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;

public interface HorizontalMovementHelper {
    void doHorizontalMovementStep(SimulationObject object, MoveControllable moveControllable, double topSpeed, double friction, double dt);
}
