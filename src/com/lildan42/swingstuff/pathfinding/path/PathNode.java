package com.lildan42.swingstuff.pathfinding.path;

import java.util.Objects;

public class PathNode {

    private final int x, y;
    private int g, h;

    private PathNode prev;

    public PathNode(int x, int y, int g, int h, PathNode prev) {
        this.x = x;
        this.y = y;

        this.g = g;
        this.h = h;

        this.prev = prev;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return this.getG() + this.getH();
    }

    public PathNode getPrevious() {
        return this.prev;
    }

    public void setPrevious(PathNode prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PathNode other)) {
            return false;
        }

        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "Node(x = %d, y = %d, g = %d, h = %d)".formatted(this.x, this.y, this.g, this.h);
    }
}
