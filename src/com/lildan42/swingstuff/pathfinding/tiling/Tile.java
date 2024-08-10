package com.lildan42.swingstuff.pathfinding.tiling;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.Rectangle;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class Tile {
    public static final double TILE_SIZE = 40.0;

    private final boolean solid;

    public Tile(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public RectangularArea getTileArea(int xIndex, int yIndex) {
        Vec2 tilePos = new Vec2(xIndex * TILE_SIZE, yIndex * TILE_SIZE);
        Vec2 tileSize = new Vec2(TILE_SIZE);

        return new Rectangle(tilePos, tileSize);
    }

    public abstract SurfaceType getSurfaceType();

    public abstract void onCollide(SimulationScene scene, SimulationObject object, Direction dir, int xIndex, int yIndex);

    public abstract void render(RenderingContext context, Tilemap tileMap, int xIndex, int yIndex);
}
