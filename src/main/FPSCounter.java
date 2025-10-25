package main;

public class FPSCounter {

    private long oldTime;
    private short frames;
    private short currentFPS;

    public FPSCounter() {
        frames = 0;
        oldTime = System.nanoTime();
    }

    public void update() {
        long newTime = System.nanoTime();
        ++frames;
        if (newTime - oldTime >= 1_000_000_000) {
            currentFPS = frames;
            frames = 0;
            oldTime = System.nanoTime();
        }
    }

    public int getFPS() {
        return (int) currentFPS;
    }
}
