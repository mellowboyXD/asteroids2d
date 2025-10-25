package asteroids;

import java.awt.Polygon;

public class TypeB extends Asteroid{
    public TypeB(int x, int y, double scale) {
        super(x, y);
        setDefault();
        scalePolygon(scale);
    }

    @Override
    public void setDefault() {
        angle = 0;
        velocity = 0.58;
        deltaDegrees = 0.02;
        int[] xCords = {0,  1,  10, 20, 18, 16, 12, 13, 5, 3, 4};
        int[] yCords = {0, -5, -10, -6, -2,  5,  7,  2, 7, 6, 0};
        int n = xCords.length;
        baseShape = new Polygon(xCords, yCords, n);
    }
}
