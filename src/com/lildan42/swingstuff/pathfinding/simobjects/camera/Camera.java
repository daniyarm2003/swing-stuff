package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.collision.Collider2D;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.simobjects.SimulationObject;

public class Camera extends SimulationObject {

    private CameraState cameraState = null;

    public Camera(SimulationScene scene) {
        super(scene);
    }

    public void setCameraState(CameraState cameraState) {
        this.cameraState = cameraState;
    }

    @Override
    protected Collider2D getCollider() {
        return null;
    }

    @Override
    public void render(RenderingContext context) {

    }

    @Override
    public void update(double dt) {
        if(this.cameraState != null) {
            this.cameraState.update(this, this.scene, dt);
        }
    }
}
