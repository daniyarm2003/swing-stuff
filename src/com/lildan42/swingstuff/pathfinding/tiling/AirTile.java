package com.lildan42.swingstuff.pathfinding.tiling;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.SurfaceType;

public class AirTile extends Tile {

    public AirTile() {
        super(false);
    }

    @Override
    public SurfaceType getSurfaceType() {
        return SurfaceType.NONE;
    }

    @Override
    public void onCollide(SimulationScene scene, SimulationObject object, Direction dir, int xIndex, int yIndex) {

    }

    @Override
    public void render(RenderingContext context, Tilemap tileMap, int xIndex, int yIndex) {

    }
}
