package com.lildan42.swingstuff.pathfinding.path;

import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.tiling.Tilemap;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.util.*;

public class PathFinder {
    private final PriorityQueue<PathNode> openQueue = new PriorityQueue<>(Comparator.comparingInt(PathNode::getF));

    private final Map<PathNode, PathNode> openSet = new HashMap<>();
    private final Set<PathNode> closedSet = new HashSet<>();

    private final Map<PathNode, Integer> heuristicCache = new HashMap<>();

    private final Tilemap tilemap;
    private PathHeuristicCalculator heuristicCalculator;
    private PathNeighbourGetter neighbourGetter;

    public PathFinder(Tilemap tilemap, PathHeuristicCalculator heuristicCalculator, PathNeighbourGetter neighbourGetter) {
        this.tilemap = tilemap;
        this.heuristicCalculator = heuristicCalculator;
        this.neighbourGetter = neighbourGetter;
    }

    public List<PathNode> findPath(Vec2 start, Vec2 end) {
        return this.findPath((int)(start.getX() / Tile.TILE_SIZE), (int)(start.getY() / Tile.TILE_SIZE),
                (int)(end.getX() / Tile.TILE_SIZE), (int)(end.getY() / Tile.TILE_SIZE));
    }

    public List<PathNode> findPath(int startX, int startY, int endX, int endY) {
        return this.findPath(new PathNode(startX, startY, 0, 0, null), new PathNode(endX, endY, 0, 0, null));
    }

    public void setHeuristicCalculator(PathHeuristicCalculator heuristicCalculator) {
        this.heuristicCalculator = heuristicCalculator;
    }

    public void setNeighbourGetter(PathNeighbourGetter neighbourGetter) {
        this.neighbourGetter = neighbourGetter;
    }

    private List<PathNode> findPath(PathNode start, PathNode end) {
        this.openQueue.clear();

        this.openSet.clear();
        this.closedSet.clear();

        this.heuristicCache.clear();

        if(this.tilemap.getTile(start.getX(), start.getY()).isSolid() || this.tilemap.getTile(end.getX(), end.getY()).isSolid()) {
            return null;
        }

        this.openQueue.add(start);
        this.openSet.put(start, start);

        while(!this.openQueue.isEmpty()) {
            PathNode cur = this.openQueue.remove();
            this.closedSet.add(cur);

            if(cur.equals(end)) {
                List<PathNode> path = new ArrayList<>();

                while(cur != null) {
                    path.add(cur);
                    cur = cur.getPrevious();
                }

                Collections.reverse(path);

                return path;
            }

            List<PathNode> neighbours = this.neighbourGetter.getNeighbours(start, cur);

            for(PathNode neighbour : neighbours) {
                if(this.closedSet.contains(neighbour)) {
                    continue;
                }

                neighbour.setG(cur.getG() + 1);

                if(this.heuristicCache.containsKey(neighbour)) {
                    neighbour.setH(this.heuristicCache.get(neighbour));
                }
                else {
                    int heuristic = this.heuristicCalculator.getHeuristic(start, neighbour);
                    neighbour.setH(heuristic);

                    if(this.heuristicCalculator.isCacheable()) {
                        this.heuristicCache.put(neighbour, heuristic);
                    }
                }

                if(!this.openSet.containsKey(neighbour)) {
                    this.openQueue.add(neighbour);
                    this.openSet.put(neighbour, neighbour);
                }
                else if(this.openSet.get(neighbour).getG() > neighbour.getG()) {
                    this.openQueue.remove(neighbour);

                    this.openQueue.add(neighbour);
                    this.openSet.put(neighbour, neighbour);
                }
            }
        }

        return null;
    }
}
