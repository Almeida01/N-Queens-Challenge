package main.services;

public class Stopwatch {
    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        return System.currentTimeMillis() - start;
    }
}