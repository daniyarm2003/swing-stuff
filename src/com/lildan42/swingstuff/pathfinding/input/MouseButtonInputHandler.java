package com.lildan42.swingstuff.pathfinding.input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseButtonInputHandler<MappingIdentifier> extends IntegerCodeButtonInputHandler<MappingIdentifier> implements MouseListener {

    public MouseButtonInputHandler() {
        super(MouseInfo.getNumberOfButtons() + 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setButtonDownState(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setButtonDownState(e.getButton(), false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
