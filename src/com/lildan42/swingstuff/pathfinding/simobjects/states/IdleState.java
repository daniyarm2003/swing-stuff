package com.lildan42.swingstuff.pathfinding.simobjects.states;

import com.lildan42.swingstuff.pathfinding.collision.CollisionTracker;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MoveControllable;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class IdleState<T extends SimulationObject> extends SimulationObjectState<T> {

    private final CollisionTracker collisionTracker;
    private final MoveControllable moveControllable;
    private final MovementProperties movementProperties;
    private final CollisionVelocityUpdater collisionVelocityUpdater;

    public IdleState(T object, StateMachine<SimulationObjectState<T>> stateMachine, CollisionTracker collisionTracker, MoveControllable moveControllable, MovementProperties movementProperties, CollisionVelocityUpdater collisionVelocityUpdater) {
        super(object, stateMachine);

        this.collisionTracker = collisionTracker;
        this.moveControllable = moveControllable;
        this.movementProperties = movementProperties;
        this.collisionVelocityUpdater = collisionVelocityUpdater;
    }

    protected abstract SimulationObjectState<T> getRunningState();
    protected abstract SimulationObjectState<T> getAirState();

    protected abstract void updateCollision(double dt);

    @Override
    public void enter() {
        Vec2 vel = this.object.getVelocity();
        this.object.setVelocity(new Vec2(0.0, vel.getY()));
    }

    @Override
    public void update(double dt) {
        this.object.applyGravity();
        this.object.applyPhysics(dt);

        this.updateCollision(dt);
        this.collisionTracker.updateVelocity(this.object, this.collisionVelocityUpdater);
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(this.moveControllable.isLeftControlDown() || this.moveControllable.isRightControlDown()) {
            this.stateMachine.setCurrent(this.getRunningState());
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
