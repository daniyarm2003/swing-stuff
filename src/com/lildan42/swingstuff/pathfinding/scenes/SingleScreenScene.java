package com.lildan42.swingstuff.pathfinding.scenes;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.screens.Screen;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class SingleScreenScene extends Scene {

    private final Screen screen;

    public SingleScreenScene(Simulation simulation, Screen screen) {
        super(simulation);

        this.screen = screen;
        this.screen.initComponents();
    }

    @Override
    public void render(RenderingContext context) {
        this.screen.render(context);
    }

    @Override
    public void enter() {
        this.screen.reset();
    }

    @Override
    public void exit() {
        this.screen.exit();
    }

    @Override
    public void update(double deltaTime) {
        this.screen.update();
    }
}
