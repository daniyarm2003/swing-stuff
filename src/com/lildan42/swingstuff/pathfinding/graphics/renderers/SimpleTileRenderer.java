package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;

public class SimpleTileRenderer implements TileRenderer {

    private final RectangleRenderer tileRenderer;

    public SimpleTileRenderer(RectangleRenderer tileRenderer) {
        this.tileRenderer = tileRenderer;
    }

    @Override
    public void render(RenderingContext context, Tilemap tilemap, int xIndex, int yIndex) {
        this.tileRenderer.render(context, tilemap.getTile(xIndex, yIndex).getTileArea(xIndex, yIndex));
    }
}
