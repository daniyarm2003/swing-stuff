package com.lildan42.swingstuff.pathfinding.input;

public enum ButtonPressState {
    NOT_PRESSED(false), RELEASED(false), PRESSED(true), HELD(true);

    private final boolean isDown;

    ButtonPressState(boolean isDown) {
        this.isDown = isDown;
    }

    public boolean isDown() {
        return this.isDown;
    }
}
