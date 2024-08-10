package com.lildan42.swingstuff.pathfinding.collision.velocity;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;

public interface CollisionVelocityUpdater {
    void updateVelocity(SimulationObject object, Direction collisionDir, SurfaceType surfaceType);
}
