package com.lildan42.swingstuff.pathfinding.graphics.renderers.animation;

import java.util.List;

public record SpriteAnimation(List<Frame> frames, boolean looping) {
    public int getFrameCount() {
        return this.frames.size();
    }

    public static SpriteAnimation ofUniformDuration(List<String> spriteKeys, double duration, boolean looping) {
        return new SpriteAnimation(spriteKeys.stream().map(key -> new Frame(key, duration)).toList(), looping);
    }

    public static SpriteAnimation ofSingleFrame(String spriteKey) {
        return new SpriteAnimation(List.of(new Frame(spriteKey, 0.0)), false);
    }

    public record Frame(String spriteKey, double duration) {

    }
}
