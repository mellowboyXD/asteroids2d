package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    private final Font small = new Font("Roboto", Font.PLAIN, 10);
    private final Font normal = new Font("Roboto", Font.PLAIN, 18);
    private final Font header = new Font("Roboto", Font.PLAIN, 48);

    private final GamePanel gp;
    private Graphics2D g2;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    private int getCenterX(String text, int x) {
        int w = g2.getFontMetrics().stringWidth(text);
        return x - w / 2;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(small);
        g2.setColor(Color.white);
        g2.drawString("FPS: " + gp.fpsCounter.getFPS(), 6, gp.getHeight() - 6);

        switch (gp.gameState) {
            case TITLE_SCREEN -> drawTitleScreen();
            case PLAY -> drawPlayScreen();
            case PAUSE -> drawPauseScreen();
            case STOP -> drawStopScreen();
        }
    }

    public void drawTitleScreen() {
        String title = "ASTEROIDS";
        g2.setFont(header);
        int x = getCenterX(title, gp.getWidth() / 2);
        g2.drawString(title, x, gp.getHeight() / 2);

        g2.setFont(normal);

        String text = "Press <Enter> to start";
        x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2 + 32);
    }

    public void drawPlayScreen() {
        int padding = 24;

        g2.setFont(normal);
        String text = "Ammo: " + gp.player.bulletCounts + "/" + gp.player.maxBullets;
        int w = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, gp.getWidth() - w - padding, padding);

        text = "Score: " + gp.player.score;
        g2.drawString(text, padding, padding);

        w = g2.getFontMetrics().stringWidth(text);
        text = "Life: " + gp.player.life;
        g2.drawString(text, (2 * padding) + w, padding);
    }

    public void drawPauseScreen() {
        int margin = 32;

        String text = "Press <ESC> to continue";
        g2.setFont(small);
        int x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, (margin * 2));

        text = "PAUSED";
        g2.setFont(normal);
        x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2 - margin);

        text = "Current Score: " + gp.player.score + "  Life: " + gp.player.life;
        x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2 + margin);
    }

    public void drawStopScreen() {
        String text = "Game Over!";
        g2.setFont(header);
        int x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2);

        text = "Your score is " + gp.player.score;
        x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2 + 64);

        text = "Press <Enter> to continue";
        g2.setFont(normal);
        x = getCenterX(text, gp.getWidth() / 2);
        g2.drawString(text, x, gp.getHeight() / 2 + 96);
    }
}
