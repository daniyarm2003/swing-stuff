package com.lildan42.swingstuff.pathfinding.path;

public interface PathHeuristicCalculator {
    boolean isCacheable();
    int getHeuristic(PathNode start, PathNode dest);
}
