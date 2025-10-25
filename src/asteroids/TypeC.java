package asteroids;

import java.awt.Polygon;

public class TypeC extends Asteroid{
    public TypeC(int x, int y, double scale) {
        super(x, y);
        setDefault();
        scalePolygon(scale);
    }

    @Override
    public void setDefault() {
        angle = 0;
        velocity = 1.1;
        deltaDegrees = 0.02;
        int[] xCords = {0, 5, 9, 7,  5, -3, 4, -1,  3, -4};
        int[] yCords = {0, 1, 5, 8, 11,  9, 7,  6,  6,  3};
        int n = xCords.length;
        baseShape = new Polygon(xCords, yCords, n);
    }
}
