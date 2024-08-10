package com.lildan42.swingstuff.pathfinding.screens;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.renderers.StaticImageRectangleRenderer;
import com.lildan42.swingstuff.pathfinding.scenes.SimulationScene;
import com.lildan42.swingstuff.pathfinding.screens.components.BackgroundScreenComponent;
import com.lildan42.swingstuff.pathfinding.screens.components.Button;
import com.lildan42.swingstuff.pathfinding.screens.components.TextComponent;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class MainScreen extends Screen {

    public MainScreen(Simulation simulation) {
        super(simulation);
    }

    @Override
    public void initComponents() {
        this.components.add(new BackgroundScreenComponent(this, new StaticImageRectangleRenderer("test_background")));

        this.components.add(new TextComponent(this, new Vec2(400.0, 10.0), "Pathfinding Simulation",
                new Font("Arial", Font.PLAIN, 30), true));

        this.components.add(new Button(this, "Play", new Vec2(350.0, 50.0),
                new Vec2(100.0, 35.0),
                () -> this.simulation.getSceneManager().setCurrent(new SimulationScene(this.simulation))));
    }
}
