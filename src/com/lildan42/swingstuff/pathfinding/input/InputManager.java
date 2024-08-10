package com.lildan42.swingstuff.pathfinding.input;

import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import javax.swing.*;

public class InputManager {
    private final KeyInputHandler<String> keyInputHandler = new KeyInputHandler<>();
    private final MouseButtonInputHandler<String> mouseButtonInputHandler = new MouseButtonInputHandler<>();
    private final MouseMotionInputHandler mouseMotionInputHandler = new MouseMotionInputHandler();

    public void registerListeners(JFrame frame, JPanel gamePanel) {
        frame.addKeyListener(this.keyInputHandler);

        gamePanel.addMouseListener(this.mouseButtonInputHandler);
        gamePanel.addMouseMotionListener(this.mouseMotionInputHandler);
    }

    public void setKeyMapping(String mappingName, int keyCode) {
        this.keyInputHandler.setButtonMapping(mappingName, keyCode);
    }

    public ButtonPressState getKeyMappingPressState(String mappingName) {
        return this.keyInputHandler.getMappingPressState(mappingName);
    }

    public void setMouseButtonMapping(String mappingName, int mouseButton) {
        this.mouseButtonInputHandler.setButtonMapping(mappingName, mouseButton);
    }

    public ButtonPressState getMouseButtonMappingPressState(String mappingName) {
        return this.mouseButtonInputHandler.getMappingPressState(mappingName);
    }

    public Vec2 getMousePosition(Vec2 scale) {
        return this.mouseMotionInputHandler.getMousePosition(scale);
    }

    public Vec2 getMouseDisplacement(Vec2 scale) {
        return this.mouseMotionInputHandler.getMouseDisplacement(scale);
    }

    public void update() {
        this.keyInputHandler.updateMappings();
        this.mouseButtonInputHandler.updateMappings();
    }
}
