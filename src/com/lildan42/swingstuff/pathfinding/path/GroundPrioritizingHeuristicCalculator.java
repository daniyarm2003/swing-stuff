package com.lildan42.swingstuff.pathfinding.path;

import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;
import com.lildan42.swingstuff.pathfinding.utils.MathUtils;

public class GroundPrioritizingHeuristicCalculator implements PathHeuristicCalculator {

    private final PathHeuristicCalculator wrappedCalculator;
    private final Tilemap tilemap;

    private final int maxGroundDist;
    private final int groundDistThreshold;
    private final int hPerGroundDist;

    public GroundPrioritizingHeuristicCalculator(PathHeuristicCalculator wrappedCalculator, Tilemap tilemap, int hPerGroundDist, int maxGroundDist, int groundDistThreshold) {
        this.wrappedCalculator = wrappedCalculator;
        this.hPerGroundDist = hPerGroundDist;
        this.tilemap = tilemap;

        this.maxGroundDist = maxGroundDist;
        this.groundDistThreshold = groundDistThreshold;
    }

    @Override
    public boolean isCacheable() {
        return this.wrappedCalculator.isCacheable();
    }

    @Override
    public int getHeuristic(PathNode start, PathNode dest) {
        boolean foundGround = false;
        int y;

        for(y = dest.getY() + 1; y <= this.tilemap.getMaxTileIndexY(); y++) {
            if(this.tilemap.getTile(dest.getX(), y).isSolid()) {
                foundGround = true;
                break;
            }
        }

        int groundDist = y - dest.getY() - 1;

        if(!foundGround) {
            groundDist = this.maxGroundDist;
        }

        groundDist = MathUtils.clamp(groundDist - this.groundDistThreshold, 0, this.maxGroundDist);

        return this.wrappedCalculator.getHeuristic(start, dest) + this.hPerGroundDist * groundDist;
    }
}
