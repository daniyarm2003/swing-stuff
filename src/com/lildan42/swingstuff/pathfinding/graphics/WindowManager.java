package com.lildan42.swingstuff.pathfinding.graphics;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowManager {
    public static final String WINDOW_TITLE = "Pathfinding Simulation";
    public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);

    private static final int FRAME_SLEEP_MILLIS = 10;

    private final JFrame frame;
    private final SimulationPanel panel;

    private boolean running = true;

    public WindowManager(Simulation simulation) {
        this.frame = new JFrame(WINDOW_TITLE);
        this.panel = new SimulationPanel(simulation);

        this.panel.setSize(DEFAULT_WINDOW_SIZE);
        this.panel.setPreferredSize(DEFAULT_WINDOW_SIZE);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.add(this.panel);
        this.frame.pack();

        this.frame.addWindowListener(new WindowManagerWindowListener(this));
    }

    public boolean isRunning() {
        return this.running;
    }

    public void stop() {
        this.running = false;
    }

    public void showWindow() {
        this.frame.setVisible(true);
    }

    public void registerInputManager(InputManager inputManager) {
        inputManager.registerListeners(this.frame, this.panel);
    }

    public void doWindowTick() {
        this.panel.renderSimulation();

        try {
            Thread.sleep(FRAME_SLEEP_MILLIS);
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public Vec2 getSizeScaleFactor() {
        Dimension frameSize = this.panel.getSize();

        return new Vec2(frameSize.getWidth() / DEFAULT_WINDOW_SIZE.getWidth(),
                frameSize.getHeight() / DEFAULT_WINDOW_SIZE.getHeight());
    }

    public Vec2 getGameDimensions() {
        return new Vec2(DEFAULT_WINDOW_SIZE.getWidth(), DEFAULT_WINDOW_SIZE.getHeight());
    }

    public Vec2 getScaledGameDimensions() {
        return new Vec2(this.panel.getWidth(), this.panel.getHeight());
    }

    private static class WindowManagerWindowListener extends WindowAdapter {
        private final WindowManager windowManager;

        public WindowManagerWindowListener(WindowManager windowManager) {
            this.windowManager = windowManager;
        }

        @Override
        public void windowClosed(WindowEvent e) {
            this.windowManager.stop();
        }
    }
}
