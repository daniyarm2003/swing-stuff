package com.lildan42.swingstuff.pathfinding.resources;

import javax.swing.*;
import java.awt.*;

public class ImageResourceManager extends MapResourceManager<String, Image> {
    public void loadResourceFromFile(String key, String filePath) {
        Image image = new ImageIcon(filePath).getImage();
        this.loadResource(key, image);
    }
}
