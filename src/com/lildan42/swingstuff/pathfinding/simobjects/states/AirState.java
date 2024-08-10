package com.lildan42.swingstuff.pathfinding.simobjects.states;

import com.lildan42.swingstuff.pathfinding.collision.CollisionTracker;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.HorizontalMovementHelper;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MoveControllable;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

public abstract class AirState<T extends SimulationObject> extends SimulationObjectState<T> {

    private final CollisionTracker collisionTracker;
    private final MoveControllable moveControllable;
    private final MovementProperties movementProperties;
    private final HorizontalMovementHelper movementHelper;
    private final CollisionVelocityUpdater collisionVelocityUpdater;

    public AirState(T object, StateMachine<SimulationObjectState<T>> stateMachine, CollisionTracker collisionTracker, MoveControllable moveControllable, MovementProperties movementProperties, HorizontalMovementHelper movementHelper, CollisionVelocityUpdater collisionVelocityUpdater) {
        super(object, stateMachine);

        this.collisionTracker = collisionTracker;
        this.moveControllable = moveControllable;
        this.movementProperties = movementProperties;
        this.movementHelper = movementHelper;
        this.collisionVelocityUpdater = collisionVelocityUpdater;
    }

    protected abstract SimulationObjectState<T> getGroundState();

    protected abstract void updateCollision(double dt);

    protected boolean canMove() {
        return true;
    }

    @Override
    public void update(double dt) {
        if(this.canMove()) {
            double friction = this.collisionTracker.getLastSolidSurfaceType(Direction.UP).getFriction();
            double topSpeed = this.movementProperties.getTopMoveSpeed();

            this.movementHelper.doHorizontalMovementStep(this.object, this.moveControllable, topSpeed, friction, dt);
        }

        this.object.applyGravity();
        this.object.applyPhysics(dt);

        this.updateCollision(dt);
        this.collisionTracker.updateVelocity(this.object, this.collisionVelocityUpdater);
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(this.collisionTracker.collidesDirection(Direction.UP)) {
            this.stateMachine.setCurrent(this.getGroundState());
            return true;
        }

        return false;
    }
}
