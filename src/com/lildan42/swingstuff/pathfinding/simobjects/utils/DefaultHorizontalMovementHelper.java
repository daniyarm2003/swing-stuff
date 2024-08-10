package com.lildan42.swingstuff.pathfinding.simobjects.utils;

import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class DefaultHorizontalMovementHelper implements HorizontalMovementHelper {
    public double getAcceleration(double topSpeed, double friction, double dt) {
        return topSpeed * friction / dt;
    }

    @Override
    public void doHorizontalMovementStep(SimulationObject object, MoveControllable moveControllable, double topSpeed, double friction, double dt) {
        double moveAcc = this.getAcceleration(topSpeed, friction, dt);

        if(moveControllable.isLeftControlDown() && object.getVelocity().getX() > -topSpeed) {
            object.applyForce(Vec2.UNIT_X.scale(-moveAcc));
        }
        else if(moveControllable.isRightControlDown() && object.getVelocity().getX() < topSpeed) {
            object.applyForce(Vec2.UNIT_X.scale(moveAcc));
        }
        else {
            Vec2 vel = object.getVelocity();
            double frictionMultiplier = 1.0 - friction;

            object.setVelocity(new Vec2(vel.getX() * frictionMultiplier, vel.getY()));
        }
    }
}
