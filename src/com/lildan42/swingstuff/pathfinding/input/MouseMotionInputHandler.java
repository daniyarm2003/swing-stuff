package com.lildan42.swingstuff.pathfinding.input;

import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInputHandler implements MouseMotionListener {

    private Vec2 lastMousePos = new Vec2();
    private Vec2 curMousePos = new Vec2();

    private boolean firstMoveDone = false;

    private void handleMouseMove(Vec2 mousePos) {
        if(!this.firstMoveDone) {
            this.firstMoveDone = true;
            this.lastMousePos = mousePos;
        }
        else {
            this.lastMousePos = this.curMousePos;
        }

        this.curMousePos = mousePos;
    }

    public Vec2 getMousePosition(Vec2 scale) {
        return this.curMousePos.div(scale);
    }

    public Vec2 getMouseDisplacement(Vec2 scale) {
        return this.curMousePos.sub(this.lastMousePos).div(scale);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        Vec2 mousePos = new Vec2(point.getX(), point.getY());

        this.handleMouseMove(mousePos);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        Vec2 mousePos = new Vec2(point.getX(), point.getY());

        this.handleMouseMove(mousePos);
    }
}
