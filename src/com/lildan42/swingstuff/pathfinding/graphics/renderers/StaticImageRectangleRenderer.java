package com.lildan42.swingstuff.pathfinding.graphics.renderers;

import com.lildan42.swingstuff.pathfinding.resources.ImageResourceManager;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class StaticImageRectangleRenderer implements RectangleRenderer {
    private static final Color NO_IMAGE_COLOR = new Color(180, 0, 255);

    private String imageKey;

    public StaticImageRectangleRenderer(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    @Override
    public void render(RenderingContext context, RectangularArea rect) {
        Vec2 pos = context.getWorldToViewPosition(rect.getPosition());
        Vec2 size = rect.getSize().mul(context.screenScale());

        ImageResourceManager imageResourceManager = context.imageResourceManager();

        if(!imageResourceManager.isResourceLoaded(this.getImageKey())) {
            context.graphics().setColor(NO_IMAGE_COLOR);
            context.graphics().fillRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY());

            return;
        }

        Image image = imageResourceManager.getLoadedResource(this.getImageKey());
        
        context.graphics().drawImage(image, (int) Math.round(pos.getX()), (int) Math.round(pos.getY()),
                (int) Math.round(size.getX()), (int) Math.round(size.getY()), null);
    }
}
