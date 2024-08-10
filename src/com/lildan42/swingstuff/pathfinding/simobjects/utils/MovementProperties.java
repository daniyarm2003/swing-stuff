package com.lildan42.swingstuff.pathfinding.simobjects.utils;

public class MovementProperties {

    private double topMoveSpeed;
    private double jumpHeight;

    public MovementProperties(double jumpHeight, double topMoveSpeed) {
        this.jumpHeight = jumpHeight;
        this.topMoveSpeed = topMoveSpeed;
    }

    public double getTopMoveSpeed() {
        return this.topMoveSpeed;
    }

    public void setTopMoveSpeed(double topMoveSpeed) {
        this.topMoveSpeed = topMoveSpeed;
    }

    public double getJumpHeight() {
        return this.jumpHeight;
    }

    public void setJumpHeight(double jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
}
