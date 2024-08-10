package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionStopVelocity;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.simobjects.states.IdleState;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;

import java.util.List;
import java.util.Map;

public class PlayerIdleState extends IdleState<Player> {

    private static final SpriteAnimation IDLE_ANIMATION = SpriteAnimation.ofUniformDuration(List.of("player_idle_1", "player_idle_2"), 1.0, true);
    private final Map<String, SimulationObjectState<Player>> states;

    public PlayerIdleState(Player player, StateMachine<SimulationObjectState<Player>> stateMachine, Map<String, SimulationObjectState<Player>> states) {
        super(player, stateMachine, player.getCollisionTracker(), player, player.getMovementProps(), new CollisionStopVelocity());
        this.states = states;
    }

    @Override
    protected SimulationObjectState<Player> getRunningState() {
        return this.states.get("running");
    }

    @Override
    protected SimulationObjectState<Player> getAirState() {
        String stateKey = this.object.getVelocity().getY() < 0.0 ? "jumping" : "falling";
        return this.states.get(stateKey);
    }

    @Override
    public void update(double dt) {
        this.object.getRenderer().update(dt);
        super.update(dt);
    }

    @Override
    protected void updateCollision(double dt) {
        this.object.updateCollision(dt);
    }

    @Override
    public void enter() {
        super.enter();
        this.object.setAnimation(IDLE_ANIMATION);
    }

    @Override
    public void exit() {

    }

    @Override
    public void render(RenderingContext context) {
        this.object.getRenderer().render(context, this.object);
    }
}
