package main;

import asteroids.Asteroid;
import entity.Bullet;
import particle.ParticleManager;

import java.awt.geom.Area;
import java.util.Random;

public class CollisionDetection {
    GamePanel gp;

    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }

    public void checkPlayer() {
        Area playerArea = new Area(gp.player.shape);
        for (int j = 0; j < gp.asteroids.length; ++j) {
            for (int k = 0; k < gp.asteroids[j].length; ++k) {
                Asteroid asteroidObj = gp.asteroids[j][k];
                if (asteroidObj == null) continue;

                Area asteroidArea = new Area(asteroidObj.shape);

                Area testArea = new Area(playerArea);
                testArea.intersect(asteroidArea);

                if (!testArea.isEmpty()) {
                    Random random = new Random();

                    int attemps = 0;
                    int maxAttempts = 1000;
                    gp.player.resetVelocity();
                    do {
                        gp.player.x = random.nextDouble(16, gp.getWidth() - 32);
                        gp.player.y = random.nextDouble(16, gp.getHeight() - 32);

                        gp.player.updateShape();

                        playerArea = new Area(gp.player.shape);

                        testArea = new Area(playerArea);
                        testArea.intersect(asteroidArea);

                        attemps++;
                    } while (!testArea.isEmpty() && attemps < maxAttempts);

                    gp.player.life--;
                }
            }
        }
    }

    public void checkBullet() {
        for (int i = 0; i < gp.player.ammo.length; ++i) {
            Bullet bulletObj = gp.player.ammo[i];
            if (bulletObj == null) continue;

            Area bulletArea = new Area(bulletObj.shape);

            for (int j = 0; j < gp.asteroids.length; ++j) {
                for (int k = 0; k < gp.asteroids[j].length; ++k) {
                    Asteroid asteroidObj = gp.asteroids[j][k];
                    if (asteroidObj == null) continue;

                    Area asteroidArea = new Area(asteroidObj.shape);

                    Area testArea = new Area(bulletArea);
                    testArea.intersect(asteroidArea);

                    if (!testArea.isEmpty()) {
                        gp.particle.emit(bulletObj.x, bulletObj.y, bulletObj.angle, 100);
                        if (!gp.asteroids[j][k].isMini) {
                            gp.objectSetter.createMiniAsteroid(j);
                            gp.player.score += 50;
                            gp.playSoundEffect(SoundManager.SMALL_BANG);
                        } else {
                            gp.playSoundEffect(SoundManager.LARGE_BANG);
                            gp.objectSetter.resetAsteroid(j, k);
                            gp.player.score += 100;
                        }

                        gp.player.ammo[i] = null;
                        gp.player.bulletCounts++;
                        break;
                    }
                }
            }
        }
    }

}
