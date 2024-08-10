package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.graphics.Renderable;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.path.PathNode;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.utils.Rectangle;

import java.awt.*;
import java.util.List;

public class PathDebugRenderer implements Renderable {

    private static final RectangleRenderer DEFAULT_PATH_RENDERER = new SolidColorRectangleRenderer(new Color(255, 0, 0, 128));

    private final RectangleRenderer nodeRenderer;
    private List<PathNode> path;

    public PathDebugRenderer(List<PathNode> path) {
        this(path, null);
    }

    public PathDebugRenderer(List<PathNode> path, RectangleRenderer nodeRenderer) {
        this.nodeRenderer = nodeRenderer != null ? nodeRenderer : DEFAULT_PATH_RENDERER;
        this.path = path;
    }

    public void setPath(List<PathNode> path) {
        this.path = path;
    }

    @Override
    public void render(RenderingContext context) {
        if(this.path == null) {
            return;
        }

        for(PathNode node : this.path) {
            RectangularArea rect = new Rectangle(node.getX() * Tile.TILE_SIZE, node.getY() * Tile.TILE_SIZE,
                    Tile.TILE_SIZE, Tile.TILE_SIZE);

            this.nodeRenderer.render(context, rect);
        }
    }
}
