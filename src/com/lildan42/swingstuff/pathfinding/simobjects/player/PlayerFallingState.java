package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.utils.Direction;

import java.util.Map;

public class PlayerFallingState extends PlayerAirState {
    private static final SpriteAnimation FALLING_LEFT = SpriteAnimation.ofSingleFrame("player_jumping_left_2");
    private static final SpriteAnimation FALLING_RIGHT = SpriteAnimation.ofSingleFrame("player_jumping_right_2");

    public PlayerFallingState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
        super(player, stateMachine, states);
    }

    @Override
    protected SpriteAnimation getCurrentAnimation(Direction direction) {
        return direction == Direction.LEFT ? FALLING_LEFT : FALLING_RIGHT;
    }

    @Override
    public boolean handleStateChanges(double dt) {
        if(super.handleStateChanges(dt)) {
            return true;
        }

        else if(this.object.isPushing()) {
            this.stateMachine.setCurrent(this.states.get("wall_sliding"));
            return true;
        }

        return false;
    }
}
