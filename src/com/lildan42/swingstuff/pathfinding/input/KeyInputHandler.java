package com.lildan42.swingstuff.pathfinding.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler<MappingIdentifier> extends IntegerCodeButtonInputHandler<MappingIdentifier> implements KeyListener {

    private static final int KEY_CODE_COUNT = 0x1000;

    public KeyInputHandler() {
        super(KEY_CODE_COUNT);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.setButtonDownState(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.setButtonDownState(e.getKeyCode(), false);
    }
}
