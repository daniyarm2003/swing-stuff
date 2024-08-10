package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class CameraTrackingState implements CameraState {

    private final CameraTrackingStrategy trackingStrategy;

    public CameraTrackingState(CameraTrackingStrategy trackingStrategy) {
        this.trackingStrategy = trackingStrategy;
    }

    protected abstract Vec2 getTrackingPoint();

    @Override
    public void update(Camera camera, SimulationScene scene, double dt) {
        this.trackingStrategy.updateTracking(camera, this.getTrackingPoint().sub(Simulation.SIMULATION_DIMENSIONS.scale(0.5)), dt);
    }
}
