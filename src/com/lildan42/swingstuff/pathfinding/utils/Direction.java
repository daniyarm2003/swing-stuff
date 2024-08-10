package com.lildan42.swingstuff.pathfinding.utils;

public enum Direction {
    LEFT(Vec2.UNIT_X.neg()), RIGHT(Vec2.UNIT_X), UP(Vec2.UNIT_Y.neg()), DOWN(Vec2.UNIT_Y);

    private final Vec2 normal;

    Direction(Vec2 normal) {
        this.normal = normal;
    }

    public Vec2 getNormal() {
        return this.normal;
    }
}
