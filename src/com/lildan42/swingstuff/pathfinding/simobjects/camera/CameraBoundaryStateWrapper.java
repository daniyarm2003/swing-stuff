package com.lildan42.swingstuff.pathfinding.simobjects.camera;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.utils.Direction;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

public class CameraBoundaryStateWrapper implements CameraState {

    private final CameraState cameraState;
    private final RectangularArea boundary;

    public CameraBoundaryStateWrapper(CameraState cameraState, RectangularArea boundary) {
        this.cameraState = cameraState;
        this.boundary = boundary;
    }

    @Override
    public void update(Camera camera, SimulationScene scene, double dt) {
        this.cameraState.update(camera, scene, dt);

        Vec2 topLeft = camera.getPosition();
        Vec2 bottomRight = camera.getPosition().add(Simulation.SIMULATION_DIMENSIONS);

        Vec2 boundaryBottomRight = this.boundary.getPosition().add(this.boundary.getSize());

        double[] distances = new double[] {
                bottomRight.getX() - boundaryBottomRight.getX(),
                this.boundary.getPosition().getX() - topLeft.getX(),
                bottomRight.getY() - boundaryBottomRight.getY(),
                this.boundary.getPosition().getY() - topLeft.getY()
        };

        for(int i = 0; i < Direction.values().length; i++) {
            Direction dir = Direction.values()[i];

            if(distances[i] > 0.0) {
                camera.setPosition(camera.getPosition().add(dir.getNormal().scale(distances[i])));
            }
        }
    }
}
