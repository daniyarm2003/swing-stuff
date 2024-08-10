package com.lildan42.swingstuff.pathfinding.utils;

public class TimeIntervalTracker {
    private double interval;
    private double timeSinceInterval = 0;

    public TimeIntervalTracker(double interval) {
        this.interval = interval;
    }

    public double getTimeSinceInterval() {
        return this.timeSinceInterval % this.interval;
    }

    public double getInterval() {
        return this.interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public int update(double dt) {
        this.timeSinceInterval += dt;

        int intervalPasses = (int)(this.timeSinceInterval / this.interval);
        this.timeSinceInterval %= this.interval;

        return intervalPasses;
    }

    public void reset() {
        this.timeSinceInterval = 0.0;
    }
}
