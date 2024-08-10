package com.lildan42.swingstuff.pathfinding.path;

import com.lildan42.swingstuff.pathfinding.simobjects.utils.MovementProperties;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GroundPathNeighbourGetter implements PathNeighbourGetter {

    private static final int MAX_HORIZONTAL_DIST = 3;
    private static final int MAX_DROP_PER_STEP = 2;

    private final MovementProperties movementProperties;
    private final Tilemap tilemap;
    private final Supplier<Double> heightGetter;

    private int xRange;
    private int yRange;

    public GroundPathNeighbourGetter(Tilemap tilemap, MovementProperties movementProperties, Supplier<Double> heightGetter, int xRange, int yRange) {
        this.tilemap = tilemap;
        this.movementProperties = movementProperties;
        this.heightGetter = heightGetter;

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

    private boolean isWalkable(int x, int y) {
        int airHeight = (int)Math.ceil(this.heightGetter.get() / Tile.TILE_SIZE);

        if(!this.tilemap.getTile(x, y + 1).isSolid()) {
            return false;
        }

        for(int i = 0; i < airHeight; i++) {
            if(this.tilemap.getTile(x, y - i).isSolid()) {
                return false;
            }
        }

        return true;
    }

    private void addNeighbours(PathNode node, List<PathNode> neighbours, int direction) {
        boolean nodeFound = false;

        for(int i = 1; i <= MAX_HORIZONTAL_DIST; i++) {
            if(nodeFound) {
                break;
            }

            int x = node.getX() + direction * i;

            if(x < node.getX() - this.xRange || x > node.getX() + this.xRange) {
                break;
            }

            int jumpHeightInTiles = (int)(this.movementProperties.getJumpHeight() / Tile.TILE_SIZE);

            for(int y = node.getY() + i * MAX_DROP_PER_STEP; y >= node.getY() - jumpHeightInTiles + 1 + (i - 1) * MAX_DROP_PER_STEP; y--) {
                if(y >= node.getY() - this.yRange && y <= node.getY() + this.yRange && this.isWalkable(x, y)) {
                    neighbours.add(new PathNode(x, y, 0, 0, node));
                    nodeFound = true;
                }
            }
        }
    }

    @Override
    public List<PathNode> getNeighbours(PathNode start, PathNode node) {
        List<PathNode> neighbours = new ArrayList<>();

        this.addNeighbours(node, neighbours, -1);
        this.addNeighbours(node, neighbours, 1);

        return neighbours;
    }
}
