package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Rectangle;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class StaticBackgroundRenderer implements BackgroundRenderer {

    private final RectangleRenderer rectangleRenderer;

    public StaticBackgroundRenderer(RectangleRenderer rectangleRenderer) {
        this.rectangleRenderer = rectangleRenderer;
    }

    @Override
    public void render(RenderingContext context) {
        RectangularArea backgroundRect = new Rectangle(Vec2.ZERO, Simulation.SIMULATION_DIMENSIONS);

        RenderingContext noCameraContext = new RenderingContext(context.graphics(), Vec2.ZERO,
                context.screenScale(), context.imageResourceManager());

        this.rectangleRenderer.render(noCameraContext, backgroundRect);
    }
}
