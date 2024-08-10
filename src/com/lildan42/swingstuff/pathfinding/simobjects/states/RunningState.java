package com.lildan42.swingstuff.pathfinding.simobjects.states;

import com.lildan42.swingstuff.pathfinding.collision.CollisionTracker;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.HorizontalMovementHelper;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MoveControllable;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

public abstract class RunningState<T extends SimulationObject> extends SimulationObjectState<T> {

    private static final double IDLE_VELOCITY_THRESHOLD = 10.0;

    private final CollisionTracker collisionTracker;
    private final MoveControllable moveControllable;
    private final MovementProperties movementProperties;
    private final HorizontalMovementHelper movementHelper;
    private final CollisionVelocityUpdater collisionVelocityUpdater;

    public RunningState(T object, StateMachine<SimulationObjectState<T>> stateMachine, CollisionTracker collisionTracker, MoveControllable moveControllable, MovementProperties movementProperties, HorizontalMovementHelper movementHelper, CollisionVelocityUpdater collisionVelocityUpdater) {
        super(object, stateMachine);

        this.collisionTracker = collisionTracker;
        this.moveControllable = moveControllable;
        this.movementProperties = movementProperties;
        this.movementHelper = movementHelper;
        this.collisionVelocityUpdater = collisionVelocityUpdater;
    }

    protected abstract SimulationObjectState<T> getIdleState();
    protected abstract SimulationObjectState<T> getAirState();

    protected abstract void updateCollision(double dt);

    public boolean isPushing() {
        return this.moveControllable.isPushing(this.collisionTracker);
    }

    protected double getSpeedRatio(double topSpeed, Direction direction) {
        return Math.max(this.object.getVelocity().getX() / (topSpeed * direction.getNormal().getX()), 0.0);
    }

    @Override
    public void update(double dt) {
        double friction = this.collisionTracker.getSurfaceType(Direction.UP).getFriction();
        double topSpeed = this.movementProperties.getTopMoveSpeed();

        this.movementHelper.doHorizontalMovementStep(this.object, this.moveControllable, topSpeed, friction, dt);

        this.object.applyGravity();
        this.object.applyPhysics(dt);

        this.updateCollision(dt);
        this.collisionTracker.updateVelocity(this.object, this.collisionVelocityUpdater);
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(Math.abs(this.object.getVelocity().getX()) < IDLE_VELOCITY_THRESHOLD
                && !this.moveControllable.isLeftControlDown() && !this.moveControllable.isRightControlDown()) {

            this.stateMachine.setCurrent(this.getIdleState());
            return true;
        }
        else if(this.collisionTracker.collidesDirection(Direction.UP) && this.moveControllable.isJumpControlDown()) {
            this.object.jump(this.movementProperties);
            this.stateMachine.setCurrent(this.getAirState());

            return true;
        }
        else if(!this.collisionTracker.collidesDirection(Direction.UP)) {
            this.stateMachine.setCurrent(this.getAirState());
            return true;
        }

        return false;
    }
}
