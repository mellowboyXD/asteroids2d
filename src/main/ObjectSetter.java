package main;

import asteroids.Asteroid;
import asteroids.TypeA;
import asteroids.TypeB;
import asteroids.TypeC;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

public class ObjectSetter {

    GamePanel gp;
    int objCount;
    final int maxParentAsteroids = 5;
    final int maxChildAsteroid = 2;
    final int offset = 72;

    private final String[] asteroid_types = {"Type A", "Type B", "Type C"};

    private Point[] initialPos = new Point[10];

    public HashSet<Integer> cache = HashSet.newHashSet(maxParentAsteroids);

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
    }

    public Point[] generateOffScreenAsteroidPositions(int numAsteroids) {
        Random rand = new Random();
        int width = Math.max(gp.initWidth, gp.getWidth());
        int height = Math.max(gp.initHeight, gp.getHeight());

        Point[] positions = new Point[numAsteroids];

        for (int i = 0; i < numAsteroids; i++) {
            int side = rand.nextInt(4); // 0=top, 1=right, 2=bottom, 3=left
            int x = 0, y = 0;

            switch (side) {
                case 0 -> { x = rand.nextInt(width); y = -offset; }           // top
                case 1 -> { x = width + offset; y = rand.nextInt(height); }   // right
                case 2 -> { x = rand.nextInt(width); y = height + offset; }   // bottom
                case 3 -> { x = -offset; y = rand.nextInt(height); }          // left
            }

            positions[i] = new Point(x, y);
        }

        return positions;
    }

    public void setup() {
        initialPos = generateOffScreenAsteroidPositions(initialPos.length);
        objCount = 0;
        gp.asteroids = new Asteroid[maxParentAsteroids][maxChildAsteroid];
        cache = HashSet.newHashSet(maxParentAsteroids);
    }

    public void setObjects() {
        while (objCount < maxParentAsteroids) {
            createRandomAsteroid(objCount);
            objCount++;
        }
    }

    public void createMiniAsteroid(int index) {
        double currentX = gp.asteroids[index][0].x;
        double currentY = gp.asteroids[index][0].y;
        double currentAngle = gp.asteroids[index][0].angle;
        double currentVelocity = gp.asteroids[index][0].velocity;
        cache.remove(getIdx(initialPos, gp.asteroids[index][0].initialPos));

        int miniAsteroidOffset = 8;
        double scale = 3.5;
        int x = (int) currentX - miniAsteroidOffset;
        int y = (int) currentY - miniAsteroidOffset;

        Asteroid a1 = switch (asteroid_types[getRandomIdx(asteroid_types)]) {
            case "Type A" -> new TypeA(x, y, scale);
            case "Type C" -> new TypeC(x, y, scale);
            default -> new TypeB(x, y, scale);
        };

        x = (int) currentX + miniAsteroidOffset;
        y = (int) currentY + miniAsteroidOffset;
        Asteroid a2 = switch (asteroid_types[getRandomIdx(asteroid_types)]) {
            case "Type A" -> new TypeA(x, y, scale);
            case "Type C" -> new TypeC(x, y, scale);
            default -> new TypeB(x, y, scale);
        };

        a1.isMini = true;
        a1.angle = currentAngle + 190;
        a1.velocity = currentVelocity;

        a2.isMini = true;
        a2.angle = currentAngle + 10;
        a2.velocity = currentVelocity;

        gp.asteroids[index][0] = a1;
        gp.asteroids[index][1] = a2;
    }

    public void createRandomAsteroid(int index) {
        int idx = getRandomIdx(initialPos);
        while (cache.contains(idx)) {
            idx = getRandomIdx(initialPos);
        }
        cache.add(idx);

        Point pos = initialPos[idx];

        switch (asteroid_types[getRandomIdx(asteroid_types)]) {
            case "Type A" -> gp.asteroids[index][0] = new TypeA(pos.x, pos.y, 6);
            case "Type B" -> gp.asteroids[index][0] = new TypeB(pos.x, pos.y, 8);
            case "Type C" -> gp.asteroids[index][0] = new TypeC(pos.x, pos.y, 7);
        }

        double dx = pos.x - gp.player.x;
        double dy = pos.y - gp.player.y;
        double angleToPlayer = Math.atan2(dy, dx);
        gp.asteroids[index][0].angle = Math.toDegrees(angleToPlayer);
        gp.asteroids[index][0].initialPos = pos;
    }

    public int getIdx(Point[] arr, Point val) {
        for (int i = 0; i < arr.length; ++i) {
            if (val.x == arr[i].x && val.y == arr[i].y) {
                return i;
            }
        }
        return -1;
    }

    public void resetAsteroid(int i, int j) {
        if (gp.asteroids[i][j] == null) return;

        if (!gp.asteroids[i][j].isMini)
            cache.remove(getIdx(initialPos, gp.asteroids[i][0].initialPos));

        gp.asteroids[i][j] = null;
        if (j == 0)
            createRandomAsteroid(i);
    }

    public void update() {
        for (int i = 0; i < gp.asteroids.length; ++i) {
            if (gp.asteroids[i] == null) continue;

            for (int j = 0; j < gp.asteroids[i].length; ++j) {
                if (gp.asteroids[i][j] == null) continue;

                gp.asteroids[i][j].update();

                double x = gp.asteroids[i][j].x;
                double y = gp.asteroids[i][j].y;
                if (x < -offset || x > gp.getWidth() + offset || y < -offset || y > gp.getHeight() + offset) {
                    resetAsteroid(i, j);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        Asteroid[][] asteroids = gp.asteroids;
        for (Asteroid[] asteroid : asteroids) {
            if (asteroid == null) continue;

            for (Asteroid a: asteroid) {
                if (a == null) continue;
                a.draw(g2);
            }
        }
    }

    public <T> int getRandomIdx(T[] arr) {
        Random random = new Random();
        return random.nextInt(arr.length);
    }
}
