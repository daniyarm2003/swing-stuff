package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;

public interface CameraState {
    void update(Camera camera, SimulationScene scene, double dt);
}
