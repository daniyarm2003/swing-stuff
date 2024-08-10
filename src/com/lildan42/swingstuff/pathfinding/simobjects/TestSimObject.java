package com.lildan42.swingstuff.pathfinding.simobjects;

import com.lildan42.swingstuff.pathfinding.collision.*;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionStopVelocity;
import com.lildan42.swingstuff.pathfinding.collision.velocity.CollisionVelocityUpdater;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.RectangleRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticImageRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.input.ButtonPressState;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.utils.BlockPlacingDebugHelper;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.tiling.Tiles;
import com.lildan42.swingstuff.pathfinding.utils.TimeIntervalTracker;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class TestSimObject extends SimulationObject implements RectangularArea {

    private final RectangleRenderer renderer = new StaticImageRectangleRenderer("test_object_image");
    private final RectangleCollider2D collider = new RectangleCollider2D(this);

    private final TimeIntervalTracker testTracker = new TimeIntervalTracker(2.0);

    private final CollisionTracker collisionTracker = new CollisionTracker();
    private final TilemapCollisionHandler tilemapCollisionHandler = new TilemapCollisionHandler(this);
    private final ObjectCollisionHandler objectCollisionHandler = new ObjectCollisionHandler(this);
    private final CollisionVelocityUpdater collisionVelocityUpdater = new CollisionStopVelocity();

    private final BlockPlacingDebugHelper blockPlacer;

    private final InputManager inputManager;

    private Vec2 size;

    public TestSimObject(SimulationScene scene, InputManager inputManager) {
        super(scene);
        this.inputManager = inputManager;
        this.size = new Vec2(50.0);

        this.blockPlacer = new BlockPlacingDebugHelper(this.scene, this.inputManager, "Right Click");

        this.setTangible(true);
    }

    @Override
    public void render(RenderingContext context) {
        this.renderer.render(context, this);
    }

    @Override
    protected Collider2D getCollider() {
        return this.collider;
    }

    @Override
    public Vec2 getSize() {
        return this.size;
    }

    public void setSize(Vec2 size) {
        this.size = size;
    }

    @Override
    public void update(double dt) {
        for(int i = 0; i < this.testTracker.update(dt); i++) {
            System.out.println("TWO SECONDS PASSED");
        }

        if(this.inputManager.getMouseButtonMappingPressState("Left Click").isDown()) {
            Vec2 moveDir = this.inputManager.getMousePosition(this.scene.getWindowScaleFactor())
                    .add(this.scene.getCamera().getPosition()).sub(this.getPosition().add(this.getSize().scale(0.5)));

            this.setAcceleration(moveDir);
        }
        else {
            this.setVelocity(this.getVelocity().scale(0.98));
        }

        this.blockPlacer.update();

        this.applyPhysics(dt);

        this.collisionTracker.reset();

        Vec2 pos = this.getPosition();
        Vec2 vel = this.getVelocity();

        Vec2 gameDimensions = this.scene.getTilemapBoundary().getSize();
        Vec2 bottomRight = pos.add(this.getSize());

        if(pos.getX() <= 0.0 && vel.getX() < 0.0) {
            this.setVelocity(vel.copyAndChangeX(-vel.getX()));
            this.setPosition(new Vec2(0.0, pos.getY()));
        }
        else if(bottomRight.getX() >= gameDimensions.getX() && vel.getX() > 0.0) {
            this.setVelocity(vel.copyAndChangeX(-vel.getX()));
            this.setPosition(new Vec2(gameDimensions.getX() - this.getSize().getX(), pos.getY()));
        }

        if(pos.getY() <= 0.0 && vel.getY() < 0.0) {
            this.setVelocity(vel.copyAndChangeY(-vel.getY()));
            this.setPosition(new Vec2(pos.getX(), 0.0));
        }
        else if(bottomRight.getY() >= gameDimensions.getY() && vel.getY() > 0.0) {
            this.setVelocity(vel.copyAndChangeY(-vel.getY()));
            this.setPosition(new Vec2(pos.getX(), gameDimensions.getY() - this.getSize().getY()));
        }

        this.tilemapCollisionHandler.handleCollision(this.collider, this.collisionTracker);
        this.objectCollisionHandler.handleObjectCollisions(this.collisionTracker);
        this.collisionTracker.updateVelocity(this, this.collisionVelocityUpdater);
    }
}
