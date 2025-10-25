package entity;

import main.GamePanel;
import main.KeyHandler;
import particle.ParticleManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyHandler;

    public Bullet[] ammo;
    public int bulletCounts;
    public final int maxBullets = 7;

    public int life;
    public long score;

    public Polygon shape;

    double angleOfRotation;
    double velocityX;
    double velocityY;

    final double friction = 0.985;

    public final int width = 35;
    public final int height = 25;

    public ParticleManager thrustersParticle = new ParticleManager();

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefault();
        shape = getTransformedShape();

        thrustersParticle.setColor(10, 10, 10);
        thrustersParticle.setSpreadFactor(15);
    }

    public Point getHead() {
        int x = shape.xpoints[1];
        int y = shape.ypoints[1];

        return new Point(x, y);
    }

    public Point getTail() {
        double x = (shape.xpoints[0] + shape.xpoints[2]) / 2.0;
        double y = (shape.ypoints[0] + shape.ypoints[2]) / 2.0;

        return new Point((int) x, (int) y);
    }

    public void resetVelocity() {
        velocityX = 0.0;
        velocityY = 0.0;
    }

    public void rotate(double deltaDegrees) {
        angle += deltaDegrees;
    }

    private void move(PlayerDirection direction) {
        double radians = Math.toRadians(angle);
        switch (direction) {
            case UP -> {
                velocityX += Math.cos(radians) * acceleration;
                velocityY += Math.sin(radians) * acceleration;
            }
            case RIGHT -> {
                rotate(angleOfRotation);
            }
            case DOWN -> {
                velocityX -= Math.cos(radians) * acceleration;
                velocityY -= Math.sin(radians) * acceleration;
            }
            case LEFT -> {
                rotate(-angleOfRotation);
            }
        }
    }

    public void shootBullets() {
        if (bulletCounts > 0) {
            for (int i = 0; i < maxBullets; ++i) {
                if (ammo[i] == null) {
                    ammo[i] = new Bullet(gp);
                    ammo[i].shoot(getHead().x, getHead().y, angle);
                    bulletCounts--;
                    return;
                }
            }
        }
    }

    public void updateShape() {
        shape = getTransformedShape();
    }

    private void drawThruster(Graphics2D g2) {
        int flicker = (int)(Math.random() * 4);
        int[] yCords = {0, 1 + flicker, 2 + flicker, 3 + flicker, 4 + flicker, 5 + flicker, 6};
        int[] xCords = {0, -4, -3, -7, -3, -4, 0};
        int n = xCords.length;


        Polygon base = new Polygon(xCords, yCords, n);

        thrustersParticle.emit(getTail().x, getTail().y, angle + 180, 10);

        base = getTransformedShape(base, this.angle, getTail().x, getTail().y);
        g2.setColor(Color.orange);
        g2.drawPolygon(base);
    }

    @Override
    public void setDefault() {
        int[] xCords = new int[] {-width / 2, width / 2, -width / 2};
        int[] yCords = new int[] {-height/2, 0, height / 2};
        baseShape = new Polygon(xCords, yCords, 3);
        acceleration = 0.25;
        x = (double) gp.getWidth() / 2;
        y = (double) gp.getHeight() / 2;
        angle = -90.0;
        angleOfRotation = 4.5;
        resetVelocity();
        bulletCounts = maxBullets;
        ammo = new Bullet[maxBullets];
        life = 3;
        score = 0;
    }

    @Override
    public void update() {
        shape = getTransformedShape();
        thrustersParticle.update();
        if (keyHandler.isUp)
            move(PlayerDirection.UP);
        if (keyHandler.isRight)
            move(PlayerDirection.RIGHT);
        if (keyHandler.isDown)
            move(PlayerDirection.DOWN);
        if (keyHandler.isLeft)
            move(PlayerDirection.LEFT);
        if (keyHandler.isShoot) {
            shootBullets();
            keyHandler.isShoot = false;
        }

        x += velocityX;
        y += velocityY;
        velocityX *= friction;
        velocityY *= friction;

        if (x < 0)
            x = gp.getWidth();
        if (x > gp.getWidth())
            x = 0;
        if (y < 0)
            y = gp.getHeight();
        if (y > gp.getHeight())
            y = 0;

        for (int i = 0; i < maxBullets; ++i) {
            if (ammo[i] != null) {
                ammo[i].update();
                if (ammo[i].x < -gp.getTileSize() || ammo[i].x > gp.getWidth() + gp.getTileSize() ||
                        ammo[i].y < -gp.getTileSize() || ammo[i].y > gp.getHeight() + gp.getTileSize()) {
                    ammo[i] = null;
                    bulletCounts++;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        thrustersParticle.draw(g2);
        g2.setColor(Color.white);

        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(shape);

        if (keyHandler.isUp)
            drawThruster(g2);

        for (Bullet bullet: ammo) {
            if (bullet != null) {
                bullet.draw(g2);
            }
        }
    }
}
