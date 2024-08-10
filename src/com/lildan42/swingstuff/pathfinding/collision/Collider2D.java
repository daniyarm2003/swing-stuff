package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class Collider2D {
    private final ColliderMTVVisitor2D mtvVisitor;

    protected Collider2D() {
        this.mtvVisitor = this.initMTVVisitor();
    }

    protected abstract ColliderMTVVisitor2D initMTVVisitor();

    public abstract Vec2 getMTV(Vec2 point);

    public boolean collides(Vec2 point) {
        return this.getMTV(point) != null;
    }

    protected abstract Vec2 acceptMTVVisitor(ColliderMTVVisitor2D mtvVisitor);

    public Vec2 getMTV(Collider2D other) {
        return other.acceptMTVVisitor(this.mtvVisitor);
    }

    public boolean collides(Collider2D other) {
        return this.getMTV(other) != null;
    }

    public Vec2 getOppositeMTV(Collider2D other) {
        return other.getMTV(this).neg();
    }
}
