package com.lildan42.swingstuff.pathfinding.graphics;

import com.lildan42.swingstuff.pathfinding.Simulation;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    private final Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        this.simulation = simulation;
    }

    public void renderSimulation() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.simulation.render(g);
    }
}
