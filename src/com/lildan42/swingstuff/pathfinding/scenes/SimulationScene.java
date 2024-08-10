package com.lildan42.swingstuff.pathfinding.scenes;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.BackgroundRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticBackgroundRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticImageRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.simobjects.TestSimObject;
import com.lildan42.swingstuff.pathfinding.simobjects.camera.Camera;
import com.lildan42.swingstuff.pathfinding.simobjects.camera.CameraBoundaryStateWrapper;
import com.lildan42.swingstuff.pathfinding.simobjects.camera.LerpCameraTrackingStrategy;
import com.lildan42.swingstuff.pathfinding.simobjects.camera.RectangleTrackingState;
import com.lildan42.swingstuff.pathfinding.simobjects.managers.DefaultSimulationObjectManager;
import com.lildan42.swingstuff.pathfinding.simobjects.managers.SimulationObjectManager;
import com.lildan42.swingstuff.pathfinding.simobjects.player.Player;
import com.lildan42.swingstuff.pathfinding.tiling.ArrayTilemap;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;
import com.lildan42.swingstuff.pathfinding.tiling.Tiles;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.util.List;

public class SimulationScene extends Scene implements Tilemap {

    private static final double GRAVITY = 9.8 * Tile.TILE_SIZE;

    private final SimulationObjectManager objectManager = new DefaultSimulationObjectManager();
    private final Tilemap tileMap = new ArrayTilemap(40, 30, 0, 0);

    private final Camera camera = new Camera(this);

    private BackgroundRenderer backgroundRenderer =
            new StaticBackgroundRenderer(new StaticImageRectangleRenderer("test_background"));

    public SimulationScene(Simulation simulation) {
        super(simulation);

        TestSimObject simObject = new TestSimObject(this, simulation.getInputManager());
        this.addObject(simObject);

        Player player = new Player(this, simulation.getInputManager(), simObject);
        this.camera.setCameraState(
                new CameraBoundaryStateWrapper(
                        new RectangleTrackingState(player, new LerpCameraTrackingStrategy()),
                        this.getTilemapBoundary()));

        this.addObject(player);

        for(int i = 0; i < 7; i++) {
            this.setTile(Tiles.DIRT, 2 * i + 3, this.getMaxTileIndexY() - Math.abs(i % 5 - 2) - 2);
        }

        for(int i = 0; i < 5; i++) {
            this.setTile(Tiles.DIRT, this.getMaxTileIndexX() - i, 3);
            this.setTile(Tiles.DIRT, this.getMaxTileIndexX() - i - 3, 4);
            this.setTile(Tiles.DIRT, this.getMaxTileIndexX() - i - 3, 5);
            this.setTile(Tiles.DIRT, this.getMaxTileIndexX() - i - 5, 6);
        }

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 5; j++) {
                this.setTile(Tiles.DIRT, i + 2, 10 + j);
            }
        }
    }

    @Override
    public Vec2 getCameraOffset() {
        return this.camera.getPosition();
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(double deltaTime) {
        Iterable<SimulationObject> objects = this.getAllObjects();

        for(SimulationObject object : objects) {
            object.update(deltaTime);
        }

        this.camera.update(deltaTime);
        this.objectManager.removeQueuedObjects();
    }

    @Override
    public void render(RenderingContext context) {
        this.backgroundRenderer.render(context);

        Iterable<SimulationObject> objects = this.getAllObjects();

        for(SimulationObject object : objects) {
            object.render(context);
        }

        this.renderTileMap(context);
    }

    private void renderTileMap(RenderingContext context) {
        Vec2 cameraPos = this.camera.getPosition();
        Vec2 cameraEndPos = cameraPos.add(Simulation.SIMULATION_DIMENSIONS);

        int xIndexStart = (int)(cameraPos.getX() / Tile.TILE_SIZE) - 2;
        int yIndexStart = (int)(cameraPos.getY() / Tile.TILE_SIZE) - 2;

        int xIndexEnd = (int)(cameraEndPos.getX() / Tile.TILE_SIZE) + 2;
        int yIndexEnd = (int)(cameraEndPos.getY() / Tile.TILE_SIZE) + 2;

        for(int yIndex = yIndexStart; yIndex < yIndexEnd; yIndex++) {
            for(int xIndex = xIndexStart; xIndex < xIndexEnd; xIndex++) {
                this.getTile(xIndex, yIndex).render(context, this, xIndex, yIndex);
            }
        }
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Vec2 getWindowScaleFactor() {
        return this.simulation.getWindowScaleFactor();
    }

    public Vec2 getGameDimensions() {
        return this.simulation.getGameDimensions();
    }

    public Vec2 getScaledGameDimensions() {
        return this.simulation.getScaledGameDimensions();
    }

    public Iterable<SimulationObject> getAllObjects() {
        return this.objectManager.getAllObjects();
    }

    public List<SimulationObject> getAllNearbyObjects(SimulationObject obj) {
        return this.objectManager.getAllNearbyObjects(obj);
    }

    public void addObject(SimulationObject obj) {
        this.objectManager.addObject(obj);
    }

    public void removeObject(SimulationObject obj) {
        this.objectManager.queueObjectRemoval(obj);
    }

    public void setBackgroundRenderer(BackgroundRenderer renderer) {
        this.backgroundRenderer = renderer;
    }

    @Override
    public Tile getTile(int xIndex, int yIndex) {
        return this.tileMap.getTile(xIndex, yIndex);
    }

    @Override
    public void setTile(Tile tile, int xIndex, int yIndex) {
        this.tileMap.setTile(tile, xIndex, yIndex);
    }

    @Override
    public int getMinTileIndexX() {
        return this.tileMap.getMinTileIndexX();
    }

    @Override
    public int getMaxTileIndexX() {
        return this.tileMap.getMaxTileIndexX();
    }

    @Override
    public int getMinTileIndexY() {
        return this.tileMap.getMinTileIndexY();
    }

    @Override
    public int getMaxTileIndexY() {
        return this.tileMap.getMaxTileIndexY();
    }

    public double getGravity() {
        return GRAVITY;
    }
}
