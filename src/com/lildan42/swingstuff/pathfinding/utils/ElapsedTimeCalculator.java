package com.lildan42.swingstuff.pathfinding.utils;

public class ElapsedTimeCalculator {
    private static final double NANOS_PER_SECOND = 1e9;
    private static final long DEFAULT_ELAPSED_TIME = (long)((1.0 / 60.0) * NANOS_PER_SECOND);

    private long startTime;
    private long elapsedTime = DEFAULT_ELAPSED_TIME;

    public ElapsedTimeCalculator() {
        this.startTime = System.nanoTime();
    }

    public void update() {
        long curTime = System.nanoTime();

        this.elapsedTime = curTime - this.startTime;
        this.startTime = curTime;
    }

    public long getDeltaTimeAsNanos() {
        return this.elapsedTime;
    }

    public double getDeltaTimeAsSeconds() {
        return (double)this.elapsedTime / NANOS_PER_SECOND;
    }
}
