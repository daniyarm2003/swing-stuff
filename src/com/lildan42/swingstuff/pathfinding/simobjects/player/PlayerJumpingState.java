package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

import java.util.List;
import java.util.Map;

public class PlayerJumpingState extends PlayerAirState {
    private static final double JUMPING_ANIMATION_FRAME_DURATION = 0.1;

    private static final SpriteAnimation JUMPING_LEFT =
            SpriteAnimation.ofUniformDuration(List.of("player_jumping_left_1", "player_jumping_left_2",
                    "player_jumping_left_3"), JUMPING_ANIMATION_FRAME_DURATION, false);

    private static final SpriteAnimation JUMPING_RIGHT =
            SpriteAnimation.ofUniformDuration(List.of("player_jumping_right_1", "player_jumping_right_2",
                    "player_jumping_right_3"), JUMPING_ANIMATION_FRAME_DURATION, false);

    public PlayerJumpingState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
        super(player, stateMachine, states);
    }

    @Override
    protected SpriteAnimation getCurrentAnimation(Direction direction) {
        return direction == Direction.LEFT ? JUMPING_LEFT : JUMPING_RIGHT;
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(this.object.getVelocity().getY() >= 0.0) {
            this.stateMachine.setCurrent(this.states.get("falling"));
            return true;
        }

        return super.handleStateChanges(dt);
    }
}
