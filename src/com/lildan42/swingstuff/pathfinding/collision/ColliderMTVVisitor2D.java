package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public interface ColliderMTVVisitor2D {
    Vec2 getMTV(RectangleCollider2D collider);
}
