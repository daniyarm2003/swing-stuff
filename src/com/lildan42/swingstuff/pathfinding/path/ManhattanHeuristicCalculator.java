package com.lildan42.swingstuff.pathfinding.path;

public class ManhattanHeuristicCalculator implements PathHeuristicCalculator {

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public int getHeuristic(PathNode start, PathNode dest) {
        return Math.abs(dest.getX() - start.getX()) + Math.abs(dest.getY() - start.getY());
    }
}
