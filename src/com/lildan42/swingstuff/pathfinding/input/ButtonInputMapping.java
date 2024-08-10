package com.lildan42.swingstuff.pathfinding.input;

public class ButtonInputMapping<ButtonType> {
    private ButtonType code;
    private ButtonPressState pressState = ButtonPressState.NOT_PRESSED;

    public ButtonInputMapping(ButtonType code) {
        this.code = code;
    }

    public ButtonType getCode() {
        return this.code;
    }

    public void setCode(ButtonType newCode) {
        this.code = newCode;
    }

    public ButtonPressState getPressState() {
        return this.pressState;
    }

    public void updatePressState(boolean isButtonDown) {
        if(this.pressState.isDown() != isButtonDown) {
            this.pressState = this.pressState.isDown() ? ButtonPressState.RELEASED : ButtonPressState.PRESSED;
        }
        else if(isButtonDown && this.pressState == ButtonPressState.PRESSED) {
            this.pressState = ButtonPressState.HELD;
        }
        else if(!isButtonDown && this.pressState == ButtonPressState.RELEASED) {
            this.pressState = ButtonPressState.NOT_PRESSED;
        }
    }
}
