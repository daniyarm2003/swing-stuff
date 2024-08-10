package com.lildan42.swingstuff.pathfinding.collision.velocity;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class CollisionStopVelocity implements CollisionVelocityUpdater {

    @Override
    public void updateVelocity(SimulationObject object, Direction collisionDir, SurfaceType surfaceType) {
        Vec2 vel = object.getVelocity();
        Vec2 collisionVel = collisionDir.getNormal().scale(vel.dot(collisionDir.getNormal()));

        vel = vel.sub(collisionVel);
        object.setVelocity(vel);
    }
}
