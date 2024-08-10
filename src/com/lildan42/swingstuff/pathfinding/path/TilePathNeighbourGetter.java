package com.lildan42.swingstuff.pathfinding.path;

import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;

import java.util.ArrayList;
import java.util.List;

public class TilePathNeighbourGetter implements PathNeighbourGetter {

    private final Tilemap tilemap;
    private int xRange;
    private int yRange;

    public TilePathNeighbourGetter(Tilemap tilemap, int xRange, int yRange) {
        this.tilemap = tilemap;

        this.xRange = xRange;
        this.yRange = yRange;
    }

    public int getXRange() {
        return xRange;
    }

    public void setXRange(int xRange) {
        this.xRange = xRange;
    }

    public int getYRange() {
        return yRange;
    }

    public void setYRange(int yRange) {
        this.yRange = yRange;
    }

    @Override
    public List<PathNode> getNeighbours(PathNode start, PathNode node) {
        List<PathNode> neighbours = new ArrayList<>();

        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                if(Math.abs(x) == Math.abs(y)) {
                    continue;
                }

                int tileX = node.getX() + x;
                int tileY = node.getY() + y;

                if(tileX < start.getX() - this.xRange || tileX > start.getX() + this.xRange
                        || tileY < start.getY() - this.yRange || tileY > start.getY() + this.yRange
                        || this.tilemap.getTile(tileX, tileY).isSolid()) {

                    continue;
                }

                neighbours.add(new PathNode(tileX, tileY, 0, 0, node));
            }
        }

        return neighbours;
    }
}
