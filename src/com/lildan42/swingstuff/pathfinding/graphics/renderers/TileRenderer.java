package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;

public interface TileRenderer {
    void render(RenderingContext context, Tilemap tilemap, int xIndex, int yIndex);
}
