package main;

import entity.PlayerDirection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {

    private final GamePanel gp;

    public boolean isUp, isRight, isDown, isLeft, isShoot, isPaused;

    public void setDefaults() {
        isPaused = false;
        isShoot = false;
        isDown = false;
        isUp = false;
        isRight = false;
        isLeft = false;
    }

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    private void handleTitleScreen(int keycode) {
        if (keycode == KeyEvent.VK_ENTER) {
            gp.setup();
            gp.gameState = GameState.PLAY;
            gp.playSoundEffect(SoundManager.WHOOSH);
        }
    }

    private void handlePlay(int keycode, boolean value) {
        switch (keycode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                gp.playSoundEffect(SoundManager.THRUST);
                isUp = value;
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> isRight = value;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> isDown = value;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> isLeft = value;
            case KeyEvent.VK_SPACE -> {
                isShoot = value;
                gp.playSoundEffect(SoundManager.FIRE);
            }
            case KeyEvent.VK_ESCAPE -> isPaused = value;
        }
    }

    private void handlePause(int keycode) {
        if (keycode == KeyEvent.VK_ESCAPE) {
            gp.gameState = GameState.PLAY;
            isPaused = false;
        }
    }

    private void handleStop(int keycode) {
        if (keycode == KeyEvent.VK_ENTER) {
            gp.gameState = GameState.TITLE_SCREEN;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (gp.gameState) {
            case TITLE_SCREEN -> handleTitleScreen(code);
            case PLAY -> handlePlay(code, true);
            case PAUSE -> handlePause(code);
            case STOP -> handleStop(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (Objects.requireNonNull(gp.gameState) == GameState.PLAY) {
            handlePlay(code, false);
        }
    }
}
