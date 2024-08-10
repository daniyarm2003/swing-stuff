package com.lildan42.swingstuff.pathfinding.tiling;

public class ArrayTilemap implements Tilemap {
    private final Tile[][] tiles;
    private final int xShift, yShift;

    public ArrayTilemap(int xCount, int yCount, int xShift, int yShift) {
        if(xCount <= 0 || yCount <= 0) {
            throw new IllegalArgumentException("tile rows and columns must be non empty");
        }

        this.tiles = new Tile[yCount][xCount];

        for(int y = 0; y < yCount; y++) {
            for(int x = 0; x < xCount; x++) {
                this.tiles[y][x] = Tiles.AIR;
            }
        }

        this.xShift = xShift;
        this.yShift = yShift;
    }

    @Override
    public Tile getTile(int xIndex, int yIndex) {
        if(!this.isWithinTileRange(xIndex, yIndex)) {
            return Tiles.AIR;
        }

        return this.tiles[yIndex - this.yShift][xIndex - this.xShift];
    }

    @Override
    public void setTile(Tile tile, int xIndex, int yIndex) {
        if(!this.isWithinTileRange(xIndex, yIndex)) {
            throw new IllegalArgumentException("tile coordinates must be in range");
        }

        this.tiles[yIndex - this.yShift][xIndex - this.xShift] = tile;
    }

    @Override
    public int getMinTileIndexX() {
        return this.xShift;
    }

    @Override
    public int getMaxTileIndexX() {
        return this.tiles[0].length - 1 + this.xShift;
    }

    @Override
    public int getMinTileIndexY() {
        return this.yShift;
    }

    @Override
    public int getMaxTileIndexY() {
        return this.tiles.length - 1 + this.yShift;
    }
}
