package asteroids;

import java.awt.Polygon;

public class TypeA extends Asteroid{
    public TypeA(int x, int y, double scale) {
        super(x, y);
        setDefault();
        scalePolygon(scale);
    }

    @Override
    public void setDefault() {
        angle = 0;
        velocity = 0.78;
        deltaDegrees = 0.08;
        int[] xCords = {0, 10, 10, 15, 10,  5,  0,  0};
        int[] yCords = {0, 0,   7, 12,  16, 14, 12, 7};
        int n = xCords.length;
        baseShape = new Polygon(xCords, yCords, n);
    }
}
