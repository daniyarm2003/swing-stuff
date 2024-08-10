package com.lildan42.swingstuff.pathfinding.input;

public class IntegerCodeButtonInputHandler<MappingIdentifier> extends ButtonInputHandler<MappingIdentifier, Integer> {

    private final boolean[] buttonsDown;

    public IntegerCodeButtonInputHandler(int buttonCount) {
        this.buttonsDown = new boolean[buttonCount];
    }

    protected void setButtonDownState(int code, boolean buttonDown) {
        this.buttonsDown[code] = buttonDown;
    }

    @Override
    protected boolean isButtonDown(Integer code) {
        return this.buttonsDown[code];
    }
}
