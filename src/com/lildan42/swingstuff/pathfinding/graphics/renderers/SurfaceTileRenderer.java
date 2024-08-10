package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.Rectangle;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;
import java.util.EnumSet;

public class SurfaceTileRenderer implements TileRenderer {

    private static final double SURFACE_Y_SCALE = 1.0 / 5.0;

    private final RectangleRenderer tileRenderer;
    private final RectangleRenderer surfaceRenderer;
    private final RectangleRenderer sideSurfaceRenderer;
    private final EnumSet<Direction> surfaceDirections;

    public SurfaceTileRenderer(RectangleRenderer tileRenderer, RectangleRenderer surfaceRenderer, RectangleRenderer sideSurfaceRenderer) {
        this(tileRenderer, surfaceRenderer, sideSurfaceRenderer, EnumSet.allOf(Direction.class));
    }

    public SurfaceTileRenderer(RectangleRenderer tileRenderer, RectangleRenderer surfaceRenderer, RectangleRenderer sideSurfaceRenderer, EnumSet<Direction> surfaceDirections) {
        this.tileRenderer = tileRenderer;
        this.surfaceRenderer = surfaceRenderer;
        this.sideSurfaceRenderer = sideSurfaceRenderer;
        this.surfaceDirections = surfaceDirections;
    }

    public static SurfaceTileRenderer fromImageKeys(String tileRendererKey, String surfaceRendererKey, String sideSurfaceRendererKey) {
        return fromImageKeys(tileRendererKey, surfaceRendererKey, sideSurfaceRendererKey, EnumSet.allOf(Direction.class));
    }

    public static SurfaceTileRenderer fromImageKeys(String tileRendererKey, String surfaceRendererKey, String sideSurfaceRendererKey, EnumSet<Direction> surfaceDirections) {
        return new SurfaceTileRenderer(new StaticImageRectangleRenderer(tileRendererKey),
                new StaticImageRectangleRenderer(surfaceRendererKey),
                new StaticImageRectangleRenderer(sideSurfaceRendererKey), surfaceDirections);
    }

    @Override
    public void render(RenderingContext context, Tilemap tilemap, int xIndex, int yIndex) {
        RectangularArea tileRect = tilemap.getTile(xIndex, yIndex).getTileArea(xIndex, yIndex);

        RectangularArea surfaceRect = new Rectangle(tileRect.getPosition(), tileRect.getSize().mul(new Vec2(1.0, SURFACE_Y_SCALE)));

        RectangularArea sideSurfaceRect = new Rectangle(surfaceRect.getPosition(),
                new Vec2(surfaceRect.getSize().getY(), surfaceRect.getSize().getX()));

        Vec2 rectCenter = context.getWorldToViewPosition(tileRect.getPosition().add(tileRect.getSize().scale(0.5)));

        Graphics2D graphics = (Graphics2D) context.graphics();

        this.tileRenderer.render(context, tileRect);

        for(Direction dir : this.surfaceDirections) {
            if(tilemap.getTile(xIndex + (int)dir.getNormal().getX(), yIndex + (int)dir.getNormal().getY()).isSolid()) {
                continue;
            }

            double rotTheta = dir == Direction.UP || dir == Direction.LEFT ? 0.0 : Math.PI;

            RectangleRenderer renderer = dir == Direction.UP || dir == Direction.DOWN ? this.surfaceRenderer : this.sideSurfaceRenderer;
            RectangularArea rect = dir == Direction.UP || dir == Direction.DOWN ? surfaceRect : sideSurfaceRect;

            graphics.rotate(rotTheta, rectCenter.getX(), rectCenter.getY());

            renderer.render(context, rect);

            graphics.rotate(-rotTheta, rectCenter.getX(), rectCenter.getY());
        }
    }
}
