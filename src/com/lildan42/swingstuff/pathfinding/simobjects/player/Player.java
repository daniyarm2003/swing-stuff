package com.lildan42.swingstuff.pathfinding.simobjects.player;

import com.lildan42.swingstuff.pathfinding.collision.CollisionTracker;
import com.lildan42.swingstuff.pathfinding.collision.ObjectCollisionHandler;
import com.lildan42.swingstuff.pathfinding.collision.RectangleCollider2D;
import com.lildan42.swingstuff.pathfinding.collision.TilemapCollisionHandler;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.PathDebugRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.AnimatedRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.RectangleSpriteAnimationRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.animation.SpriteAnimation;
import com.lildan42.swingstuff.pathfinding.input.ButtonPressState;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.path.*;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.simobjects.states.SimulationObjectState;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MoveControllable;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.states.SimpleStateMachine;
import com.lildan42.swingstuff.pathfinding.states.StateMachine;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.utils.TimeIntervalTracker;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.util.HashMap;
import java.util.Map;

public class Player extends SimulationObject implements RectangularArea, MoveControllable {

    private final RectangleCollider2D collider = new RectangleCollider2D(this);
    private final CollisionTracker collisionTracker = new CollisionTracker();

    private final TilemapCollisionHandler tilemapCollisionHandler = new TilemapCollisionHandler(this);
    private final ObjectCollisionHandler objectCollisionHandler = new ObjectCollisionHandler(this);

    private final StateMachine<SimulationObjectState<Player>> stateMachine;
    private final Map<String, SimulationObjectState<Player>> states = new HashMap<>();

    private final PathFinder pathFinder;

    private final TimeIntervalTracker intervalTracker = new TimeIntervalTracker(1.0);

    private final MovementProperties movementProps = new MovementProperties(3.0 * Tile.TILE_SIZE,
            3.0 * Tile.TILE_SIZE);

    private final InputManager inputManager;
    private final SimulationObject target;

    private Vec2 size = new Vec2(30.0, 50.0);

    private final RectangleSpriteAnimationRenderer renderer = new RectangleSpriteAnimationRenderer(null);
    private final PathDebugRenderer pathRenderer = new PathDebugRenderer(null);

    public Player(SimulationScene scene, InputManager inputManager, SimulationObject target) {
        super(scene);
        this.inputManager = inputManager;
        this.stateMachine = this.initStates();

        this.target = target;

        this.setPosition(new Vec2(200.0));
        this.setTangible(true);

        this.pathFinder = new PathFinder(this.scene,
                new GroundPrioritizingHeuristicCalculator(new ManhattanHeuristicCalculator(), this.scene, 50, 100, 1),
                new TilePathNeighbourGetter(this.scene, 50, 50));
    }

    private StateMachine<SimulationObjectState<Player>> initStates() {
        StateMachine<SimulationObjectState<Player>> stateMachine = new SimpleStateMachine<>(null);

        SimulationObjectState<Player> initial = new PlayerIdleState(this, stateMachine, this.states);
        stateMachine.setCurrent(initial);

        this.states.put("idle", initial);
        this.states.put("running", new PlayerRunningState(this, stateMachine, this.states));
        this.states.put("jumping", new PlayerJumpingState(this, stateMachine, this.states));
        this.states.put("falling", new PlayerFallingState(this, stateMachine, this.states));
        this.states.put("wall_sliding", new PlayerWallSlidingState(this, stateMachine, this.states));

        return stateMachine;
    }

    @Override
    public Vec2 getSize() {
        return this.size;
    }

    public void setSize(Vec2 size) {
        this.size = size;
    }

    public AnimatedRectangleRenderer getRenderer() {
        return this.renderer;
    }

    public int getAnimationFrame() {
        return this.renderer.getFrame();
    }

    public void setAnimationFrame(int frame) {
        this.renderer.setFrame(frame);
    }

    public int getAnimationFrameCount() {
        return this.renderer.getFrameCount();
    }

    public void setAnimation(SpriteAnimation animation) {
        this.renderer.setAnimation(animation);
    }

    @Override
    public void render(RenderingContext context) {
        this.stateMachine.getCurrent().render(context);
        this.pathRenderer.render(context);
    }

    @Override
    protected RectangleCollider2D getCollider() {
        return this.collider;
    }

    public CollisionTracker getCollisionTracker() {
        return this.collisionTracker;
    }

    @Override
    public void update(double dt) {
        this.stateMachine.getCurrent().update(dt);
        this.stateMachine.getCurrent().handleStateChanges(dt);

        for(int i = 0; i < this.intervalTracker.update(dt); i++) {
            this.pathRenderer.setPath(this.pathFinder.findPath(this.getPosition(), this.target.getPosition()));
        }
    }

    public void updateCollision(double dt) {
        this.collisionTracker.reset();
        this.tilemapCollisionHandler.handleCollision(this.getCollider(), this.getCollisionTracker());
        this.objectCollisionHandler.handleObjectCollisions(this.getCollisionTracker());
    }

    public double getJumpHeight() {
        return this.movementProps.getJumpHeight();
    }

    public void setJumpHeight(double jumpHeight) {
        this.movementProps.setJumpHeight(jumpHeight);
    }

    @Override
    public boolean isLeftControlDown() {
        return this.inputManager.getKeyMappingPressState("Left").isDown();
    }

    @Override
    public boolean isRightControlDown() {
        return this.inputManager.getKeyMappingPressState("Right").isDown();
    }

    @Override
    public boolean isJumpControlDown() {
        return this.inputManager.getKeyMappingPressState("Jump") == ButtonPressState.PRESSED;
    }

    public boolean isPushing() {
        return this.isPushing(this.getCollisionTracker());
    }

    public MovementProperties getMovementProps() {
        return this.movementProps;
    }
}
