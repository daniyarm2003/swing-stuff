package com.lildan42.swingstuff.pathfinding.tiling;

import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.utils.Rectangle;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public interface Tilemap {
    Tile getTile(int xIndex, int yIndex);

    default Tile getTile(Vec2 tilePos) {
        int xIndex = (int)(tilePos.getX() / Tile.TILE_SIZE);
        int yIndex = (int)(tilePos.getY() / Tile.TILE_SIZE);

        return this.getTile(xIndex, yIndex);
    }

    void setTile(Tile tile, int xIndex, int yIndex);

    default boolean isWithinTileRange(int xIndex, int yIndex) {
        return xIndex >= this.getMinTileIndexX() && xIndex <= this.getMaxTileIndexX()
                && yIndex >= this.getMinTileIndexY() && yIndex <= this.getMaxTileIndexY();
    }

    int getMinTileIndexX();
    int getMaxTileIndexX();

    int getMinTileIndexY();
    int getMaxTileIndexY();

    default RectangularArea getTilemapBoundary() {
        Vec2 topLeft = new Vec2(this.getMinTileIndexX() * Tile.TILE_SIZE, this.getMinTileIndexY() * Tile.TILE_SIZE);
        Vec2 bottomRight = new Vec2((this.getMaxTileIndexX() + 1) * Tile.TILE_SIZE, (this.getMaxTileIndexY() + 1) * Tile.TILE_SIZE);

        return Rectangle.fromPoints(topLeft, bottomRight);
    }
}
