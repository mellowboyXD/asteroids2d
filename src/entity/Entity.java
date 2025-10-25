package entity;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;

public abstract class Entity {
    public double x;
    public double y;
    public double angle;
    public double acceleration;

    public abstract void draw(Graphics2D g2);
    public abstract void setDefault();
    public abstract void update();

    public Polygon baseShape;
    public Polygon shape;

    public Polygon getTransformedShape() {
        int n = baseShape.npoints;
        int[] tx = new int[n];
        int[] ty = new int[n];
        double radians = Math.toRadians(angle);

        for(int i = 0; i < n; ++i) {
            double px = baseShape.xpoints[i];
            double py = baseShape.ypoints[i];

            double rx = px * Math.cos(radians) - py * Math.sin(radians);
            double ry = px * Math.sin(radians) + py * Math.cos(radians);

            tx[i] = (int) (rx + x);
            ty[i] = (int) (ry + y);
        }
        return new Polygon(tx, ty, n);
    }

    public Polygon getTransformedShape(Polygon baseShape, double angle, double x, double y) {
        int n = baseShape.npoints;
        int[] tx = new int[n];
        int[] ty = new int[n];
        double radians = Math.toRadians(angle);

        for(int i = 0; i < n; ++i) {
            double px = baseShape.xpoints[i];
            double py = baseShape.ypoints[i];

            double rx = px * Math.cos(radians) - py * Math.sin(radians);
            double ry = px * Math.sin(radians) + py * Math.cos(radians);

            tx[i] = (int) (rx + x);
            ty[i] = (int) (ry + y);
        }
        return new Polygon(tx, ty, n);
    }
}
