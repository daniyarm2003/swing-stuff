package com.lildan42.swingstuff.pathfinding.graphics.renderers.animation;

public class SpriteAnimationRenderer implements AnimatedRenderer {

    private SpriteAnimation animation;

    private int frame = 0;
    private double elapsedTime = 0.0;

    public SpriteAnimationRenderer(SpriteAnimation animation) {
        this.animation = animation;
    }

    public boolean isAnimationFinished() {
        return this.frame >= this.animation.getFrameCount() - 1 && !this.animation.looping();
    }

    public SpriteAnimation.Frame getCurrentFrame() {
        return this.animation.frames().get(this.frame);
    }

    public void setAnimation(SpriteAnimation animation) {
        this.frame = 0;
        this.elapsedTime = 0.0;

        this.animation = animation;
    }

    public int getFrame() {
        return this.frame;
    }

    public void setFrame(int frame) {
        if(frame < 0 || frame >= this.animation.getFrameCount()) {
            throw new IndexOutOfBoundsException("animation does not have frame index %d".formatted(frame));
        }

        this.frame = frame;
        this.elapsedTime = 0.0;
    }

    public int getFrameCount() {
        return this.animation.getFrameCount();
    }

    @Override
    public void update(double speed, double dt) {
        if(this.isAnimationFinished()) {
            return;
        }

        this.elapsedTime += speed * dt;

        while(this.elapsedTime >= this.getCurrentFrame().duration() && !this.isAnimationFinished()) {
            this.elapsedTime -= this.getCurrentFrame().duration();
            this.frame = (this.frame + 1) % this.animation.getFrameCount();
        }
    }
}
