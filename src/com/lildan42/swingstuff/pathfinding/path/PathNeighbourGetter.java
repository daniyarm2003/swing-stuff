package com.lildan42.swingstuff.pathfinding.path;

import java.util.List;

public interface PathNeighbourGetter {
    List<PathNode> getNeighbours(PathNode start, PathNode node);
}
