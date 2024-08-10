package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class RectangleTrackingState extends CameraTrackingState {

    private final RectangularArea rect;

    public RectangleTrackingState(RectangularArea rect, CameraTrackingStrategy trackingStrategy) {
        super(trackingStrategy);
        this.rect = rect;
    }

    @Override
    protected Vec2 getTrackingPoint() {
        return this.rect.getPosition().add(this.rect.getSize().scale(0.5));
    }
}
