package entity;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Bullet extends Entity{

    GamePanel gp;

    int width = 6;
    int height = 8;

    public Bullet(GamePanel gp) {
        this.gp = gp;
        setDefault();
        shape = getTransformedShape();
    }

    public void shoot(double x, double y, double angle) {
        this.angle = angle;
        this.x = x;
        this.y = y;
        acceleration = 8;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillPolygon(shape);
    }

    @Override
    public void setDefault() {
        int[] yCords = {-width/2, width/2, width/2, -width/2};
        int[] xCords = {height/2, height/2, -height/2, -height/2};
        baseShape = new Polygon(xCords, yCords, 4);
        acceleration = 0;
    }

    @Override
    public void update() {
        double radians = Math.toRadians(angle);
        x += Math.cos(radians) * acceleration;
        y += Math.sin(radians) * acceleration;
        shape = getTransformedShape();
    }
}
