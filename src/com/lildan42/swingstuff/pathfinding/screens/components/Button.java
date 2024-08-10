package com.lildan42.swingstuff.pathfinding.screens.components;

import com.lildan42.swingstuff.pathfinding.collision.SimpleRectangleCollider2D;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.interfaces.RectangularArea;
import com.lildan42.swingstuff.pathfinding.screens.Screen;
import com.lildan42.swingstuff.pathfinding.screens.ScreenComponent;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;

public class Button extends ScreenComponent implements RectangularArea {

    private static final String BUTTON_FONT_TYPE = "Arial";

    private static final Color BUTTON_COLOR_NO_HOVER = new Color(0, 120, 180);
    private static final Color BUTTON_COLOR_HOVER = new Color(0, 100, 160);

    private final ButtonClickHandler clickHandler;
    private final SimpleRectangleCollider2D collider;
    private final TextComponent textComponent;

    public Button(Screen screen, String text, Vec2 pos, Vec2 size, ButtonClickHandler clickHandler) {
        super(screen);

        this.clickHandler = clickHandler;
        this.collider = new SimpleRectangleCollider2D(pos, size);

        Vec2 center = pos.add(size.scale(0.5));

        this.textComponent = new TextComponent(screen, center, text,
                new Font(BUTTON_FONT_TYPE, Font.PLAIN, (int)(size.getY() / 1.5)), true);
    }

    @Override
    public void render(RenderingContext context) {
        Graphics graphics = context.graphics();
        Vec2 scale = context.screenScale();

        graphics.setColor(this.isHoverFlagSet() ? BUTTON_COLOR_HOVER : BUTTON_COLOR_NO_HOVER);

        Vec2 scaledPos = this.collider.getPosition().mul(scale);
        Vec2 scaledSize = this.collider.getSize().mul(scale);

        graphics.fillRect((int)scaledPos.getX(), (int)scaledPos.getY(), (int)scaledSize.getX(), (int)scaledSize.getY());

        this.textComponent.render(context);
    }

    @Override
    protected boolean isHoveredOver(Vec2 mousePos) {
        return this.collider.collides(mousePos);
    }

    @Override
    public void update() {
        this.textComponent.update();
    }

    @Override
    public void reset() {
        this.textComponent.update();
    }

    @Override
    public void exit() {
        this.textComponent.exit();
    }

    @Override
    public void onClick() {
        this.textComponent.onClick();
        this.clickHandler.handleButtonClick();
    }

    @Override
    public Vec2 getPosition() {
        return this.collider.getPosition();
    }

    @Override
    public Vec2 getSize() {
        return this.collider.getSize();
    }

    public interface ButtonClickHandler {
        void handleButtonClick();
    }
}
