package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionStopVelocity;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.AirState;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.DefaultHorizontalMovementHelper;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

import java.util.Map;

public abstract class PlayerAirState extends AirState<Player> {

    protected final Map<String, SimulationObjectState<Player>> states;
    private Direction lastDirection;

    public PlayerAirState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
        super(player, stateMachine, player.getCollisionTracker(), player, player.getMovementProps(), new DefaultHorizontalMovementHelper(), new CollisionStopVelocity());
        this.states = states;
    }

    private Direction getCurrentDirection() {
        if(this.object.isLeftControlDown()) {
            return Direction.LEFT;
        }

        else if(this.object.isRightControlDown()) {
            return Direction.RIGHT;
        }

        return this.object.getVelocity().getX() < 0.0 ? Direction.LEFT : Direction.RIGHT;
    }

    protected abstract SpriteAnimation getCurrentAnimation(Direction direction);

    @Override
    public void enter() {
        Direction curDirection = this.getCurrentDirection();
        SpriteAnimation curAnimation = this.getCurrentAnimation(curDirection);

        this.object.setAnimation(curAnimation);
        this.lastDirection = curDirection;
    }

    @Override
    public void exit() {

    }

    @Override
    protected SimulationObjectState<Player> getGroundState() {
        return this.states.get("running");
    }

    @Override
    protected void updateCollision(double dt) {
        this.object.updateCollision(dt);
    }

    @Override
    public void update(double dt) {
        Direction curDirection = this.getCurrentDirection();

        if(curDirection != this.lastDirection) {
            this.lastDirection = curDirection;
            int curFrame = this.object.getAnimationFrame();

            this.object.setAnimation(this.getCurrentAnimation(curDirection));
            this.object.setAnimationFrame(curFrame);
        }

        this.object.getRenderer().update(dt);
        super.update(dt);
    }

    @Override
    public void render(RenderingContext context) {
        this.object.getRenderer().render(context, this.object);
    }
}
