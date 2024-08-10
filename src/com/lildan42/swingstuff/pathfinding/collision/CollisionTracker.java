package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;

import java.util.HashMap;
import java.util.Map;

public class CollisionTracker {
    private final Map<Direction, SurfaceType> collidingSurfaces = new HashMap<>();
    private final Map<Direction, SurfaceType> prevSolidSurfaces = new HashMap<>();

    public CollisionTracker() {
        this.reset();
    }

    public void reset() {
        for(Direction dir : Direction.values()) {
            if(this.collidesDirection(dir)) {
                this.prevSolidSurfaces.put(dir, this.getSurfaceType(dir));
            }
        }

        for(Direction dir : Direction.values()) {
            this.collidingSurfaces.put(dir, SurfaceType.NONE);
        }
    }

    public void setDirectionSurfaceType(Direction dir, SurfaceType surfaceType) {
        this.collidingSurfaces.put(dir, surfaceType);
    }

    public boolean collidesDirection(Direction dir) {
        return this.collidingSurfaces.get(dir) != SurfaceType.NONE;
    }

    public SurfaceType getSurfaceType(Direction dir) {
        return this.collidingSurfaces.getOrDefault(dir, SurfaceType.NONE);
    }

    public SurfaceType getLastSolidSurfaceType(Direction dir) {
        return this.prevSolidSurfaces.get(dir);
    }

    public void updateVelocity(SimulationObject object, CollisionVelocityUpdater velocityUpdater) {
        for(Direction dir : Direction.values()) {
            if(this.collidesDirection(dir)) {
                velocityUpdater.updateVelocity(object, dir, this.getSurfaceType(dir));
            }
        }
    }
}
