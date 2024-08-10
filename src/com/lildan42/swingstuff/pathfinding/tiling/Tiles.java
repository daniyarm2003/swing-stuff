package com.lildan42.swingstuff.pathfinding.tiling;

import com.lildan42.swingstuff.pathfinding.graphics.renderers.SimpleTileRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticImageRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.SurfaceTileRenderer;

public class Tiles {
    public static final Tile AIR = new AirTile();

    public static final Tile TEST =
            new BasicSolidTile(new SimpleTileRenderer(new StaticImageRectangleRenderer("test_object_image")));

    public static final Tile DIRT =
            new BasicSolidTile(SurfaceTileRenderer.fromImageKeys("dirt",
                    "grass_surface", "grass_surface_side"));
}
