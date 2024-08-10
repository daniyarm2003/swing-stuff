package com.lildan42.swingstuff.pathfinding.screens.components;

import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.screens.Screen;
import com.lildan42.swingstuff.pathfinding.screens.ScreenComponent;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class TextComponent extends ScreenComponent {

    private String text;
    private Font font;
    private Vec2 pos;

    private final boolean centered;

    public TextComponent(Screen screen, Vec2 pos, String text, Font font, boolean centered) {
        super(screen);

        this.text = text;
        this.font = font;
        this.centered = centered;
        this.pos = pos;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Vec2 getPosition() {
        return this.pos;
    }

    public void setPosition(Vec2 pos) {
        this.pos = pos;
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void render(RenderingContext context) {
        Graphics graphics = context.graphics();
        Vec2 scale = context.screenScale();

        graphics.setColor(Color.BLACK);

        float newFontSize = this.font.getSize2D() * (float)Math.min(scale.getX(), scale.getY());

        Font newFont = this.font.deriveFont(newFontSize);
        graphics.setFont(newFont);

        Vec2 drawPos = this.pos.mul(scale);

        int fontHeight = graphics.getFontMetrics().getHeight();
        drawPos = drawPos.add(Vec2.UNIT_Y.scale(fontHeight));

        if(this.centered) {
            int fontWidth = graphics.getFontMetrics().stringWidth(this.text);
            drawPos = drawPos.sub(new Vec2(fontWidth / 2.0, fontHeight / 2.0));
        }

        graphics.drawString(this.text, (int)drawPos.getX(), (int)drawPos.getY());
    }

    @Override
    protected boolean isHoveredOver(Vec2 mousePos) {
        return false;
    }

    @Override
    public void onClick() {

    }
}
