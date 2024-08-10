package com.lildan42.swingstuff.pathfinding.screens;

import com.lildan42.swingstuff.pathfinding.Simulation;
import com.lildan42.swingstuff.pathfinding.graphics.Renderable;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.input.ButtonPressState;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Screen implements Renderable {
    private static final String COMPONENT_CLICK_MAPPING = "Left Click";

    protected final Simulation simulation;
    protected final List<ScreenComponent> components = new ArrayList<>();

    public Screen(Simulation simulation) {
        this.simulation = simulation;
    }

    public abstract void initComponents();

    public void reset() {
        for(ScreenComponent component : this.components) {
            component.reset();
        }
    }

    public void exit() {
        for(ScreenComponent component : this.components) {
            component.exit();
        }
    }

    public void update() {
        InputManager inputManager = this.simulation.getInputManager();

        Vec2 mousePos = inputManager.getMousePosition(this.simulation.getWindowScaleFactor());
        boolean mouseClicked = inputManager.getMouseButtonMappingPressState(COMPONENT_CLICK_MAPPING) == ButtonPressState.PRESSED;

        for(ScreenComponent component : this.components) {
            component.updateHoverState(mousePos);

            if(component.isHoverFlagSet() && mouseClicked) {
                component.onClick();
            }

            component.update();
        }
    }

    @Override
    public void render(RenderingContext context) {
        for(ScreenComponent component : this.components) {
            component.render(context);
        }
    }
}
