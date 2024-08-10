package com.lildan42.swingstuff.pathfinding.collision;

import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class TilemapCollisionHandler {
    private final SimulationObject object;

    public TilemapCollisionHandler(SimulationObject object) {
        this.object = object;
    }

    public void handleCollision(RectangleCollider2D collider, CollisionTracker collisionTracker) {
        SimulationScene scene = this.object.getScene();

        Vec2 topLeft = collider.getPosition();
        Vec2 bottomRight = topLeft.add(collider.getSize());

        Direction[] directions = Direction.values();

        for(double y = topLeft.getY(); y < bottomRight.getY() + Tile.TILE_SIZE; y += Tile.TILE_SIZE) {
            for(double x = topLeft.getX(); x < bottomRight.getX() + Tile.TILE_SIZE; x += Tile.TILE_SIZE) {
                Vec2 objPos = new Vec2(Math.min(x, bottomRight.getX()), Math.min(y, bottomRight.getY()));

                if(!scene.getTile(objPos).isSolid()) {
                    continue;
                }

                int tileX = (int)(objPos.getX() / Tile.TILE_SIZE);
                int tileY = (int)(objPos.getY() / Tile.TILE_SIZE);

                Vec2 tileTopLeft = new Vec2(Tile.TILE_SIZE * tileX, Tile.TILE_SIZE * tileY);
                Vec2 tileBottomRight = tileTopLeft.add(new Vec2(Tile.TILE_SIZE));

                Direction translateDir = null;
                double translateAmount = Double.POSITIVE_INFINITY;

                for(Direction dir : directions) {
                    Vec2 dirNormal = dir.getNormal();

                    if(scene.getTile(tileX + (int)dirNormal.getX(), tileY + (int)dirNormal.getY()).isSolid()) {
                        continue;
                    }

                    double curTranslation = switch(dir) {
                        case LEFT -> bottomRight.getX() - tileTopLeft.getX();
                        case RIGHT -> tileBottomRight.getX() - topLeft.getX();
                        case UP -> bottomRight.getY() - tileTopLeft.getY();
                        case DOWN -> tileBottomRight.getY() - topLeft.getY();
                    };

                    if(curTranslation < translateAmount) {
                        translateDir = dir;
                        translateAmount = curTranslation;
                    }
                }

                Vec2 center = topLeft.add(collider.getSize().scale(0.5));
                Vec2 tileCenter = tileTopLeft.add(new Vec2(Tile.TILE_SIZE / 2.0));

                Vec2 dirFromTileCenter = center.sub(tileCenter);

                if(translateDir != null && dirFromTileCenter.dot(translateDir.getNormal()) > 0.0) {
                    Tile tile = scene.getTile(tileX, tileY);

                    if(!collisionTracker.collidesDirection(translateDir)) {
                        collisionTracker.setDirectionSurfaceType(translateDir, tile.getSurfaceType());
                    }

                    Vec2 translation = translateDir.getNormal().scale(translateAmount);
                    this.object.setPosition(this.object.getPosition().add(translation));

                    topLeft = topLeft.add(translation);
                    bottomRight = bottomRight.add(translation);

                    tile.onCollide(scene, this.object, translateDir, tileX, tileY);
                }
            }
        }
    }
}
