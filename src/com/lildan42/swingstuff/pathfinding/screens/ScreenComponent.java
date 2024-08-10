package com.lildan42.swingstuff.pathfinding.screens;

import com.lildan42.swingstuff.pathfinding.graphics.Renderable;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public abstract class ScreenComponent implements Renderable {
    protected final Screen screen;
    private boolean hovered = false;

    public ScreenComponent(Screen screen) {
        this.screen = screen;
    }

    public abstract void update();
    public abstract void reset();
    public abstract void exit();

    public void updateHoverState(Vec2 mousePos) {
        this.hovered = this.isHoveredOver(mousePos);
    }

    protected abstract boolean isHoveredOver(Vec2 mousePos);

    public boolean isHoverFlagSet() {
        return this.hovered;
    }

    public abstract void onClick();
}
