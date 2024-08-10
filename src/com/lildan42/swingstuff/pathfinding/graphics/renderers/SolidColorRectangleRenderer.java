package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class SolidColorRectangleRenderer implements RectangleRenderer {

    private Color color;

    public SolidColorRectangleRenderer(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(RenderingContext context, RectangularArea rect) {
        Vec2 pos = context.getWorldToViewPosition(rect.getPosition());
        Vec2 size = rect.getSize().mul(context.screenScale());

        context.graphics().setColor(this.getColor());

        context.graphics().fillRect((int) Math.round(pos.getX()), (int) Math.round(pos.getY()),
                (int) Math.round(size.getX()), (int) Math.round(size.getY()));
    }
}
