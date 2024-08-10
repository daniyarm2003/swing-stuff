package com.lildan42.swingstuff.pathfinding.graphics;

import com.lildan42.swingstuff.pathfinding.resources.ImageResourceManager;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public record RenderingContext(Graphics graphics, Vec2 cameraPosition, Vec2 screenScale, ImageResourceManager imageResourceManager) {
    public Vec2 getWorldToViewPosition(Vec2 worldPosition) {
        return worldPosition.sub(this.cameraPosition).mul(this.screenScale);
    }
}
