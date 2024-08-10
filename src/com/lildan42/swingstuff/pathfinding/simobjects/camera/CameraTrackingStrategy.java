package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public interface CameraTrackingStrategy {
    void updateTracking(Camera camera, Vec2 trackingPoint, double dt);
}
