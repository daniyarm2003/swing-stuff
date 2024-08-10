package com.lildan42.swingstuff.pathfinding.utils;

public class Vec2 {

    public static final Vec2 ZERO = new Vec2();
    public static final Vec2 UNIT_X = new Vec2(1.0, 0.0);
    public static final Vec2 UNIT_Y = new Vec2(0.0, 1.0);

    private final double x, y;

    public Vec2() {
        this(0.0);
    }

    public Vec2(double compLength) {
        this(compLength, compLength);
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Vec2 copy() {
        return new Vec2(this.x, this.y);
    }

    public Vec2 copyAndChangeX(double x) {
        return new Vec2(x, this.y);
    }

    public Vec2 copyAndChangeY(double y) {
        return new Vec2(this.x, y);
    }

    public double sqrMag() {
        return this.x * this.x + this.y * this.y;
    }

    public double mag() {
        return Math.sqrt(this.sqrMag());
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 sub(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public Vec2 scale(double scalar) {
        return new Vec2(scalar * this.x, scalar * this.y);
    }

    public Vec2 neg() {
        return this.scale(-1.0);
    }

    public Vec2 mul(Vec2 other) {
        return new Vec2(this.x * other.x, this.y * other.y);
    }

    public Vec2 div(Vec2 other) {
        return new Vec2(this.x / other.x, this.y / other.y);
    }

    public Vec2 normal() {
        double mag = this.mag();

        if(mag == 0.0) {
            return new Vec2();
        }

        return this.scale(1.0 / mag);
    }

    public Vec2 perpendicular() {
        return new Vec2(-this.getY(), this.getX());
    }

    public double dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vec2 lerp(Vec2 other, double t) {
        return new Vec2(MathUtils.lerp(this.x, other.x, t), MathUtils.lerp(this.y, other.y, t));
    }

    @Override
    public String toString() {
        return "Vec2(x = %.3f, y = %.3f)".formatted(this.x, this.y);
    }
}
