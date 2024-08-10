package com.lildan42.swingstuff.pathfinding.utils;

public class MathUtils {
    public static float lerp(float start, float end, float t) {
        return start + t * (end - start);
    }

    public static double lerp(double start, double end, double t) {
        return start + t * (end - start);
    }

    public static int clamp(int val, int min, int max) {
        return Math.min(Math.max(val, min), max);
    }

    public static long clamp(long val, long min, long max) {
        return Math.min(Math.max(val, min), max);
    }

    public static float clamp(float val, float min, float max) {
        return Math.min(Math.max(val, min), max);
    }

    public static double clamp(double val, double min, double max) {
        return Math.min(Math.max(val, min), max);
    }
}
