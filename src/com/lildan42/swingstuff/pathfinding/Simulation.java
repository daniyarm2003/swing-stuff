package com.lildan42.swingstuff.pathfinding;

import com.lildan42.swingstuff.pathfinding.resources.ImageResourceManager;
import com.lildan42.swingstuff.pathfinding.graphics.RenderingContext;
import com.lildan42.swingstuff.pathfinding.graphics.WindowManager;
import com.lildan42.swingstuff.pathfinding.input.InputManager;
import com.lildan42.swingstuff.pathfinding.scenes.SingleScreenScene;
import com.lildan42.swingstuff.pathfinding.scenes.Scene;
import com.lildan42.swingstuff.pathfinding.screens.MainScreen;
import com.lildan42.swingstuff.pathfinding.states.HistoricalStateMachine;
import com.lildan42.swingstuff.pathfinding.states.StackStateMachine;
import com.lildan42.swingstuff.pathfinding.utils.ElapsedTimeCalculator;
import com.lildan42.swingstuff.pathfinding.utils.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Simulation {
    public static final Vec2 SIMULATION_DIMENSIONS
            = new Vec2(WindowManager.DEFAULT_WINDOW_SIZE.getWidth(), WindowManager.DEFAULT_WINDOW_SIZE.getHeight());

    private static Simulation instance;

    private final WindowManager windowManager;
    private final ElapsedTimeCalculator elapsedTimeCalculator;
    private final HistoricalStateMachine<Scene> sceneManager;
    private final InputManager inputManager;
    private final ImageResourceManager imageResourceManager;

    private Simulation() {
        this.windowManager = new WindowManager(this);
        this.elapsedTimeCalculator = new ElapsedTimeCalculator();
        this.sceneManager = new StackStateMachine<>(new SingleScreenScene(this, new MainScreen(this)));
        this.inputManager = this.initInputManager();
        this.imageResourceManager = this.initImageResourceManager();
    }

    public void start() {
        this.windowManager.showWindow();

        while(this.isRunning()) {
            this.tick();
        }
    }

    public boolean isRunning() {
        return this.windowManager.isRunning();
    }

    public HistoricalStateMachine<Scene> getSceneManager() {
        return this.sceneManager;
    }

    public Scene getCurrentScene() {
        return this.sceneManager.getCurrent();
    }

    public Vec2 getWindowScaleFactor() {
        return this.windowManager.getSizeScaleFactor();
    }

    public Vec2 getGameDimensions() {
        return this.windowManager.getGameDimensions();
    }

    public Vec2 getScaledGameDimensions() {
        return this.windowManager.getScaledGameDimensions();
    }

    private InputManager initInputManager() {
        InputManager inputManager = new InputManager();
        this.windowManager.registerInputManager(inputManager);

        inputManager.setKeyMapping("Test", KeyEvent.VK_SPACE);

        inputManager.setKeyMapping("Left", KeyEvent.VK_LEFT);
        inputManager.setKeyMapping("Right", KeyEvent.VK_RIGHT);
        inputManager.setKeyMapping("Jump", KeyEvent.VK_UP);

        inputManager.setMouseButtonMapping("Left Click", MouseEvent.BUTTON1);
        inputManager.setMouseButtonMapping("Right Click", MouseEvent.BUTTON3);

        return inputManager;
    }

    private ImageResourceManager initImageResourceManager() {
        ImageResourceManager resourceManager = new ImageResourceManager();

        resourceManager.loadResourceFromFile("test_object_image", "images/test_image.png");

        resourceManager.loadResourceFromFile("dirt", "images/dirt.png");
        resourceManager.loadResourceFromFile("grass_surface", "images/dirt_grass.png");
        resourceManager.loadResourceFromFile("grass_surface_side", "images/dirt_grass_side.png");

        resourceManager.loadResourceFromFile("test_background", "images/test_background.png");

        resourceManager.loadResourceFromFile("player_idle_1", "images/stickman/stickman_idle_1.png");
        resourceManager.loadResourceFromFile("player_idle_2", "images/stickman/stickman_idle_2.png");

        resourceManager.loadResourceFromFile("player_running_right_1", "images/stickman/stickman_running_right_1.png");
        resourceManager.loadResourceFromFile("player_running_right_2", "images/stickman/stickman_running_right_2.png");
        resourceManager.loadResourceFromFile("player_running_right_3", "images/stickman/stickman_running_right_3.png");

        resourceManager.loadResourceFromFile("player_running_left_1", "images/stickman/stickman_running_left_1.png");
        resourceManager.loadResourceFromFile("player_running_left_2", "images/stickman/stickman_running_left_2.png");
        resourceManager.loadResourceFromFile("player_running_left_3", "images/stickman/stickman_running_left_3.png");

        resourceManager.loadResourceFromFile("player_pushing_right_1", "images/stickman/stickman_pushing_right_1.png");
        resourceManager.loadResourceFromFile("player_pushing_right_2", "images/stickman/stickman_pushing_right_2.png");
        resourceManager.loadResourceFromFile("player_pushing_right_3", "images/stickman/stickman_pushing_right_3.png");

        resourceManager.loadResourceFromFile("player_pushing_left_1", "images/stickman/stickman_pushing_left_1.png");
        resourceManager.loadResourceFromFile("player_pushing_left_2", "images/stickman/stickman_pushing_left_2.png");
        resourceManager.loadResourceFromFile("player_pushing_left_3", "images/stickman/stickman_pushing_left_3.png");

        resourceManager.loadResourceFromFile("player_jumping_right_1", "images/stickman/stickman_jumping_right_1.png");
        resourceManager.loadResourceFromFile("player_jumping_right_2", "images/stickman/stickman_jumping_right_2.png");
        resourceManager.loadResourceFromFile("player_jumping_right_3", "images/stickman/stickman_jumping_right_3.png");

        resourceManager.loadResourceFromFile("player_jumping_left_1", "images/stickman/stickman_jumping_left_1.png");
        resourceManager.loadResourceFromFile("player_jumping_left_2", "images/stickman/stickman_jumping_left_2.png");
        resourceManager.loadResourceFromFile("player_jumping_left_3", "images/stickman/stickman_jumping_left_3.png");

        resourceManager.loadResourceFromFile("player_wall_sliding_left", "images/stickman/stickman_wall_sliding_left.png");
        resourceManager.loadResourceFromFile("player_wall_sliding_right", "images/stickman/stickman_wall_sliding_right.png");

        return resourceManager;
    }

    public InputManager getInputManager() {
        return this.inputManager;
    }

    private void tick() {
        this.inputManager.update();
        this.getCurrentScene().update(this.elapsedTimeCalculator.getDeltaTimeAsSeconds());

        this.windowManager.doWindowTick();
        this.elapsedTimeCalculator.update();
    }

    public static Simulation getInstance() {
        if(instance == null) {
            instance = new Simulation();
        }

        return instance;
    }

    public void render(Graphics graphics) {
        RenderingContext context = new RenderingContext(graphics, this.getCurrentScene().getCameraOffset(),
                this.getWindowScaleFactor(), this.imageResourceManager);

        this.getCurrentScene().render(context);
    }
}
