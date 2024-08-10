package com.lildan42.swingstuff.pathfinding.simobjects.utils;

import com.lildan42.swingstuff.pathfinding.collision.CollisionTracker;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

public interface MoveControllable {
    boolean isLeftControlDown();

    boolean isRightControlDown();

    boolean isJumpControlDown();

    default boolean isPushing(CollisionTracker collisionTracker, Direction dir) {
        if(dir != Direction.LEFT && dir != Direction.RIGHT) {
            throw new IllegalArgumentException("direction must be horizontal");
        }

        boolean controlDown = dir == Direction.LEFT ? this.isLeftControlDown() : this.isRightControlDown();
        boolean collidesSurface = collisionTracker.collidesDirection(dir == Direction.LEFT ? Direction.RIGHT : Direction.LEFT);

        return controlDown && collidesSurface;
    }

    default boolean isPushing(CollisionTracker collisionTracker) {
        return this.isPushing(collisionTracker, Direction.LEFT) || this.isPushing(collisionTracker, Direction.RIGHT);
    }
}
