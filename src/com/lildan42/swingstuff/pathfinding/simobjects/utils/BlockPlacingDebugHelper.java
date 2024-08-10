package com.lildan42.swingstuff.pathfinding.simobjects.utils;

import com.lildan42.swingstuff.pathfinding.input.ButtonPressState;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.tiling.Tile;
import com.lildan42.swingstuff.pathfinding.tiling.Tiles;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class BlockPlacingDebugHelper {

    private final SimulationScene scene;
    private final InputManager inputManager;
    private final String placeMouseButtonMapping;

    private boolean inputPressed = false;
    private boolean drawMode = false;

    public BlockPlacingDebugHelper(SimulationScene scene, InputManager inputManager, String placeMouseButtonMapping) {
        this.scene = scene;
        this.inputManager = inputManager;
        this.placeMouseButtonMapping = placeMouseButtonMapping;
    }

    public void update() {
        ButtonPressState pressState = this.inputManager.getMouseButtonMappingPressState(this.placeMouseButtonMapping);
        Vec2 mousePos = this.inputManager.getMousePosition(this.scene.getWindowScaleFactor()).add(this.scene.getCameraOffset());

        int tileX = (int)(mousePos.getX() / Tile.TILE_SIZE);
        int tileY = (int)(mousePos.getY() / Tile.TILE_SIZE);

        if(!this.scene.isWithinTileRange(tileX, tileY)) {
            return;
        }

        if(pressState.isDown()) {
            if(!this.inputPressed) {
                this.inputPressed = true;
                this.drawMode = !this.scene.getTile(tileX, tileY).isSolid();
            }

            Tile newTile = this.drawMode ? Tiles.DIRT : Tiles.AIR;
            this.scene.setTile(newTile, tileX, tileY);
        }
        else {
            this.inputPressed = false;
        }
    }
}
