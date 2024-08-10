package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.utils.Rectangle;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class SimpleRectangleCollider2D extends RectangleCollider2D {

    private final Rectangle rectangle;

    public SimpleRectangleCollider2D(double x, double y, double width, double height) {
        this(new Vec2(x, y), new Vec2(width, height), false);
    }

    public SimpleRectangleCollider2D(double x, double y, double width, double height, boolean centered) {
        this(new Vec2(x, y), new Vec2(width, height), centered);
    }

    public SimpleRectangleCollider2D(Vec2 pos, Vec2 size) {
        this(pos, size, false);
    }

    public SimpleRectangleCollider2D(Vec2 pos, Vec2 size, boolean centered) {
        this(new Rectangle(pos, size, centered));
    }

    private SimpleRectangleCollider2D(Rectangle rectangle) {
        super(rectangle);
        this.rectangle = rectangle;
    }

    public void setPosition(Vec2 pos) {
        this.rectangle.setPosition(pos);
    }

    public void setSize(Vec2 size) {
        this.rectangle.setSize(size);
    }
}
