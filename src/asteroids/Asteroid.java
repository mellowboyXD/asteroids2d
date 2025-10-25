package asteroids;

import entity.Entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Asteroid extends Entity {

    public double velocity;
    public double deltaDegrees;

    public Point initialPos;

    public boolean isMini = false;

    public static double level = 1.0;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
        initialPos = new Point(x, y);
        setDefault();
        shape = getTransformedShape();
    }

    public void scalePolygon(double factor) {
        int n = baseShape.npoints;
        int[] sx = new int[n];
        int[] sy = new int[n];

        for (int i = 0; i < n; ++i) {
            double px = baseShape.xpoints[i];
            double py = baseShape.ypoints[i];

            sx[i] = (int) (factor * px);
            sy[i] = (int) (factor * py);
        }

        baseShape = new Polygon(sx, sy, n);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.white);
        g2.draw(shape);
    }

    @Override
    public void setDefault() {
        isMini = false;
    }

    @Override
    public void update() {
        shape = getTransformedShape();

        x -= Math.cos(Math.toRadians(angle)) * (velocity * level);
        y -= Math.sin(Math.toRadians(angle)) * (velocity * level);
    }
}
