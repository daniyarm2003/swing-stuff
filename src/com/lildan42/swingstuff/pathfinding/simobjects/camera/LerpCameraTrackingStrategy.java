package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class LerpCameraTrackingStrategy implements CameraTrackingStrategy {

    private static final double DEFAULT_LERP_FACTOR = 0.1;
    private final double lerpFactor;

    public LerpCameraTrackingStrategy() {
        this(DEFAULT_LERP_FACTOR);
    }

    public LerpCameraTrackingStrategy(double lerpFactor) {
        this.lerpFactor = lerpFactor;
    }

    @Override
    public void updateTracking(Camera camera, Vec2 trackingPoint, double dt) {
        camera.setPosition(camera.getPosition().lerp(trackingPoint, this.lerpFactor));
    }
}
