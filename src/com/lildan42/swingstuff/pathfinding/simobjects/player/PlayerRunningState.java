package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionStopVelocity;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.RunningState;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.DefaultHorizontalMovementHelper;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

import java.util.List;
import java.util.Map;

public class PlayerRunningState extends RunningState<Player> {
    private static final double RUNNING_ANIMATION_FRAME_DURATION = 0.10;

    private static final SpriteAnimation RUNNING_LEFT =
            SpriteAnimation.ofUniformDuration(List.of("player_running_left_1", "player_running_left_2",
                    "player_running_left_3", "player_running_left_2"), RUNNING_ANIMATION_FRAME_DURATION, true);

    private static final SpriteAnimation RUNNING_RIGHT =
            SpriteAnimation.ofUniformDuration(List.of("player_running_right_1", "player_running_right_2",
                    "player_running_right_3", "player_running_right_2"), RUNNING_ANIMATION_FRAME_DURATION, true);

    private static final SpriteAnimation PUSHING_LEFT =
            SpriteAnimation.ofUniformDuration(List.of("player_pushing_left_1", "player_pushing_left_2",
                    "player_pushing_left_3", "player_pushing_left_2"), RUNNING_ANIMATION_FRAME_DURATION, true);

    private static final SpriteAnimation PUSHING_RIGHT =
            SpriteAnimation.ofUniformDuration(List.of("player_pushing_right_1", "player_pushing_right_2",
                    "player_pushing_right_3", "player_pushing_right_2"), RUNNING_ANIMATION_FRAME_DURATION, true);

    private final Map<String, SimulationObjectState<Player>> states;

    private Direction lastDirection;
    private boolean lastPushState;

    public PlayerRunningState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
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

    private SpriteAnimation getCurrentAnimation() {
        if(this.getCurrentDirection() == Direction.LEFT) {
            return this.isPushing() ? PUSHING_LEFT : RUNNING_LEFT;
        }

        return this.isPushing() ? PUSHING_RIGHT : RUNNING_RIGHT;
    }

    @Override
    public void enter() {
        SpriteAnimation curAnimation = this.getCurrentAnimation();

        this.object.setAnimation(curAnimation);

        this.lastDirection = this.getCurrentDirection();
        this.lastPushState = this.isPushing();
    }

    @Override
    public void exit() {

    }

    @Override
    protected SimulationObjectState<Player> getIdleState() {
        return this.states.get("idle");
    }

    @Override
    protected SimulationObjectState<Player> getAirState() {
        String stateKey = this.object.getVelocity().getY() < 0.0 ? "jumping" : "falling";
        return this.states.get(stateKey);
    }

    @Override
    protected void updateCollision(double dt) {
        this.object.updateCollision(dt);
    }

    @Override
    public void update(double dt) {
        Direction curDirection = this.getCurrentDirection();
        boolean pushing = this.isPushing();

        if(curDirection != this.lastDirection || pushing != this.lastPushState) {
            this.object.setAnimation(this.getCurrentAnimation());

            this.lastDirection = this.getCurrentDirection();
            this.lastPushState = this.isPushing();
        }

        double animSpeed = this.isPushing() ? 1.0 : this.getSpeedRatio(this.object.getMovementProps().getTopMoveSpeed(), curDirection);

        this.object.getRenderer().update(animSpeed, dt);
        super.update(dt);
    }

    @Override
    public void render(RenderingContext context) {
        this.object.getRenderer().render(context, this.object);
    }
}
