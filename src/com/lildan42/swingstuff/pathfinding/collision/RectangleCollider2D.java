package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class RectangleCollider2D extends Collider2D implements RectangularArea {

    private final RectangularArea rect;

    public RectangleCollider2D(RectangularArea rect) {
        this.rect = rect;
    }

    @Override
    protected ColliderMTVVisitor2D initMTVVisitor() {
        return new MTVVisitor(this);
    }

    @Override
    public Vec2 getMTV(Vec2 point) {
        Vec2 topLeft = this.getPosition();
        Vec2 bottomRight = topLeft.add(this.getSize());

        if(point.getX() <= topLeft.getX() || point.getX() >= bottomRight.getX()
                || point.getY() <= topLeft.getY() || point.getY() >= bottomRight.getY()) {

            return null;
        }

        double left = point.getX() - topLeft.getX();
        double right = bottomRight.getX() - point.getX();

        double top = point.getY() - topLeft.getY();
        double bottom = bottomRight.getY() - point.getY();

        double[] distances = { left, right, top, bottom };
        Vec2[] normals = { Vec2.UNIT_X, Vec2.UNIT_X.neg(), Vec2.UNIT_Y, Vec2.UNIT_Y.neg() };

        double minDistance = distances[0];
        Vec2 minDistNormal = normals[0];

        for(int i = 1; i < 4; i++) {
            if(distances[i] < minDistance) {
                minDistNormal = normals[i];
                minDistance = distances[i];
            }
        }

        return minDistNormal.scale(minDistance);
    }

    @Override
    protected Vec2 acceptMTVVisitor(ColliderMTVVisitor2D mtvVisitor) {
        return mtvVisitor.getMTV(this);
    }

    public Vec2 getPosition() {
        return this.rect.getPosition();
    }

    public Vec2 getSize() {
        return this.rect.getSize();
    }

    private record MTVVisitor(RectangleCollider2D collider) implements ColliderMTVVisitor2D {

        @Override
        public Vec2 getMTV(RectangleCollider2D other) {
            Vec2 topLeft = this.collider.getPosition();
            Vec2 bottomRight = topLeft.add(this.collider.getSize());

            Vec2 otherTopLeft = other.getPosition();
            Vec2 otherBottomRight = otherTopLeft.add(other.getSize());

            if(topLeft.getX() >= otherBottomRight.getX() || bottomRight.getX() <= otherTopLeft.getX()
                    || topLeft.getY() >= otherBottomRight.getY() || bottomRight.getY() <= otherTopLeft.getY()) {

                return null;
            }

            double left = bottomRight.getX() - otherTopLeft.getX();
            double right = otherBottomRight.getX() - topLeft.getX();

            double top = bottomRight.getY() - otherTopLeft.getY();
            double bottom = otherBottomRight.getY() - topLeft.getY();

            double[] distances = { left, right, top, bottom };
            Vec2[] normals = { Vec2.UNIT_X.neg(), Vec2.UNIT_X, Vec2.UNIT_Y.neg(), Vec2.UNIT_Y };

            double minDistance = distances[0];
            Vec2 minDistNormal = normals[0];

            for(int i = 1; i < 4; i++) {
                if(distances[i] < minDistance) {
                    minDistNormal = normals[i];
                    minDistance = distances[i];
                }
            }

            return minDistNormal.scale(minDistance);
        }
    }
}
