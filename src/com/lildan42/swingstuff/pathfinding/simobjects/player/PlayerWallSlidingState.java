package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionStopVelocity;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.PhysicsUtils;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.util.Map;

public class PlayerWallSlidingState extends SimulationObjectState<Player> {
    private static final SpriteAnimation SLIDING_LEFT = SpriteAnimation.ofSingleFrame("player_wall_sliding_left");
    private static final SpriteAnimation SLIDING_RIGHT = SpriteAnimation.ofSingleFrame("player_wall_sliding_right");

    private static final double MAX_DOWN_VELOCITY = Tile.TILE_SIZE;
    private static final Vec2 WALL_JUMP_SCALE = new Vec2(1.0, 0.5);

    private final Map<String, SimulationObjectState<Player>> states;

    private Direction wallCollisionDir;
    private final CollisionVelocityUpdater velocityUpdater = new CollisionStopVelocity();

    public PlayerWallSlidingState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
        super(player, stateMachine);
        this.states = states;
    }

    @Override
    public void enter() {
        this.wallCollisionDir = this.object.isLeftControlDown() ? Direction.RIGHT : Direction.LEFT;
        this.object.setAnimation(this.wallCollisionDir == Direction.LEFT ? SLIDING_RIGHT : SLIDING_LEFT);
    }

    @Override
    public void exit() {

    }

    @Override
    public void update(double dt) {
        Vec2 vel = this.object.getVelocity();
        this.object.setVelocity(new Vec2(-this.wallCollisionDir.getNormal().getX(), Math.min(vel.getY(), MAX_DOWN_VELOCITY)));

        this.object.applyGravity();
        this.object.applyPhysics(dt);

        this.object.updateCollision(dt);
        this.object.getCollisionTracker().updateVelocity(this.object, this.velocityUpdater);
    }

    @Override
    public void render(RenderingContext context) {
        this.object.getRenderer().render(context, this.object);
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(!this.object.isPushing()) {
            this.stateMachine.setCurrent(this.states.get("falling"));
            return true;
        }
        else if(this.object.isJumpControlDown()) {
            double jumpHeight = this.object.getJumpHeight();
            double jumpVelY = PhysicsUtils.getJumpVelocityYFromHeight(jumpHeight, this.object.getScene().getGravity());

            Vec2 wallJumpVel = new Vec2(-this.wallCollisionDir.getNormal().getX() * jumpVelY * WALL_JUMP_SCALE.getX(),
                    jumpVelY * WALL_JUMP_SCALE.getY());

            this.object.setVelocity(wallJumpVel);
            this.stateMachine.setCurrent(this.states.get("jumping"));

            return true;
        }
        else if(this.object.getCollisionTracker().collidesDirection(Direction.UP)) {
            this.stateMachine.setCurrent(this.states.get("running"));
        }

        return false;
    }
}
