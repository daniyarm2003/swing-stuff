package com.lildan42.swingstuff.pathfinding.graphics.renderers.animation;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticImageRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;

public class RectangleSpriteAnimationRenderer extends SpriteAnimationRenderer implements AnimatedRectangleRenderer {

    private final StaticImageRectangleRenderer spriteRenderer = new StaticImageRectangleRenderer("");

    public RectangleSpriteAnimationRenderer(SpriteAnimation animation) {
        super(animation);
    }

    @Override
    public void render(RenderingContext context, RectangularArea rect) {
        this.spriteRenderer.setImageKey(this.getCurrentFrame().spriteKey());
        this.spriteRenderer.render(context, rect);
    }
}
