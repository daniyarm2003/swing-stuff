package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class ObjectCollisionHandler {

    private final SimulationObject object;

    public ObjectCollisionHandler(SimulationObject object) {
        this.object = object;
    }

    public void handleObjectCollisions(CollisionTracker tracker) {
        SimulationScene scene = this.object.getScene();

        for(SimulationObject other : scene.getAllNearbyObjects(this.object)) {


            if(other == this.object || !other.isTangible()) {
                continue;
            }

            Vec2 mtv = this.object.getMTV(other);

            if(mtv == null) {
                continue;
            }

            this.object.setPosition(this.object.getPosition().add(mtv));

            Direction[] directions = Direction.values();

            Direction mtvDir = directions[0];
            double maxProj = directions[0].getNormal().dot(mtv);

            for(int i = 1; i < directions.length; i++) {
                double proj = directions[i].getNormal().dot(mtv);

                if(proj > maxProj) {
                    maxProj = proj;
                    mtvDir = directions[i];
                }
            }

            tracker.setDirectionSurfaceType(mtvDir, SurfaceType.OBJECT);
        }
    }
}
