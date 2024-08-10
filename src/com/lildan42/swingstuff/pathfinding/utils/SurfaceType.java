package com.lildan42.swingstuff.pathfinding.utils;

public enum SurfaceType {
    NONE(0.0), SOLID(0.1), ICE(0.02), OBJECT(0.1);

    private final double friction;

    SurfaceType(double friction) {
        this.friction = friction;
    }

    public double getFriction() {
        return this.friction;
    }
}
