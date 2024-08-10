package com.lildan42.swingstuff.pathfinding.utils;

import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;

public class Rectangle implements RectangularArea {

    private Vec2 pos, size;
    private boolean centered;

    public Rectangle(double x, double y, double width, double height) {
        this(new Vec2(x, y), new Vec2(width, height), false);
    }

    public Rectangle(double x, double y, double width, double height, boolean centered) {
        this(new Vec2(x, y), new Vec2(width, height), centered);
    }

    public Rectangle(Vec2 pos, Vec2 size) {
        this(pos, size, false);
    }

    public Rectangle(Vec2 pos, Vec2 size, boolean centered) {
        this.centered = centered;

        this.setSize(size);
        this.setPosition(pos);
    }

    @Override
    public Vec2 getPosition() {
        return this.pos;
    }

    @Override
    public Vec2 getSize() {
        return this.size;
    }

    public void setPosition(Vec2 pos) {
        this.pos = this.centered ? pos.sub(this.size.scale(0.5)) : pos;
    }

    public void setSize(Vec2 size) {
        this.size = size;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public static Rectangle fromPoints(Vec2 topLeft, Vec2 bottomRight) {
        return new Rectangle(topLeft, bottomRight.sub(topLeft));
    }
}
