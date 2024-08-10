package com.lildan42.swingstuff.pathfinding.utils;

public class PhysicsUtils {
    public static double getJumpVelocityYFromHeight(double height, double gravity) {
        return -Math.sqrt(2.0 * height * gravity);
    }
}
