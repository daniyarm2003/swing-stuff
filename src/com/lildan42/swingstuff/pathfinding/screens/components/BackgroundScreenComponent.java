package com.lildan42.swingstuff.pathfinding.screens.components;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.BackgroundRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.RectangleRenderer;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticBackgroundRenderer;
import com.lildan42.swingstuff.pathfinding.screens.Screen;
import com.lildan42.swingstuff.pathfinding.screens.ScreenComponent;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class BackgroundScreenComponent extends ScreenComponent {

    private final BackgroundRenderer backgroundRenderer;

    public BackgroundScreenComponent(Screen screen, RectangleRenderer backgroundRectRenderer) {
        super(screen);
        this.backgroundRenderer = new StaticBackgroundRenderer(backgroundRectRenderer);
    }

    @Override
    public void render(RenderingContext context) {
        this.backgroundRenderer.render(context);
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void exit() {

    }

    @Override
    protected boolean isHoveredOver(Vec2 mousePos) {
        return false;
    }

    @Override
    public void onClick() {

    }
}
