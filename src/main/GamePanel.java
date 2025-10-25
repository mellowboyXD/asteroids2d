package main;

import asteroids.Asteroid;
import entity.Player;
import particle.ParticleManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    private final int tileSize = 64;
    int cols = 10;
    public int initWidth = cols * tileSize;
    int rows = 12;
    public int initHeight = rows * tileSize;

    private Thread gameThread;

    public final int TARGET_FPS = 60;
    public FPSCounter fpsCounter = new FPSCounter();
    public UI ui = new UI(this);

    public KeyHandler keyHandler = new KeyHandler(this);
    public Player player = new Player(this, keyHandler);
    public Asteroid[][] asteroids;

    public CollisionDetection collisionDetection = new CollisionDetection(this);

    public ObjectSetter objectSetter = new ObjectSetter(this);

    public SoundManager soundManager = new SoundManager();
    public ParticleManager particle = new ParticleManager();

    public GameState gameState;

    public GamePanel() {
        setPreferredSize(new Dimension(initWidth, initHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        addKeyListener(keyHandler);

        setup();

        setFocusable(true);
    }

    public void setup() {
        gameState = GameState.SETUP;
        particle.setSpreadFactor(500);
        particle.setColor(80, 100, 105);
        player.setDefault();
        objectSetter.setup();
        objectSetter.setObjects();
        keyHandler.setDefaults();
        gameState = GameState.TITLE_SCREEN;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        particle.update();
        if (keyHandler.isPaused)
            gameState = GameState.PAUSE;

        if (gameState != GameState.PAUSE)
            objectSetter.update();

        if (gameState == GameState.PLAY) {
            player.update();
            collisionDetection.checkPlayer();
            collisionDetection.checkBullet();

            if (player.score > 1000)
                Asteroid.level = 1.5;
            if (player.score > 2000)
                Asteroid.level = 2.0;
            if (player.score > 3000)
                Asteroid.level = 2.5;
            if (player.score > 4000)
                Asteroid.level = 3.0;
            if (player.score > 5000)
                Asteroid.level = 3.5;

            if (player.life <= 0) {
                playSoundEffect(SoundManager.GAME_OVER);
                gameState = GameState.STOP;
            }
        }
    }

    public void playSoundEffect(int i) {
        soundManager.setFile(i);
        soundManager.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        particle.draw(g2);

        objectSetter.draw(g2);

        if (gameState == GameState.PLAY) {
            player.draw(g2);

        }

        ui.draw(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / TARGET_FPS;
        double deltaTime = 0.0;
        long lastTime = System.nanoTime();

        while(gameThread != null) {
            long currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();
                repaint();

                --deltaTime;
                fpsCounter.update();
            }
        }
    }
}
