package particle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
    double x, y;
    double vx, vy;
    double life;
    Color color;
    double size;

    public Particle(double x, double y, Color color, int spreadFactor, double angle) {
        this.x = x;
        this.y = y;
        this.size = 2 + Math.random() * 3;

        double speed = 1 + Math.random() * 2;
        double spreadAngle = Math.toRadians(180);
        double randomAngle = angle + (Math.random() * spreadAngle * spreadFactor);

        vx = Math.cos(Math.toRadians(randomAngle)) * speed;
        vy = Math.sin(Math.toRadians(randomAngle)) * speed;

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        this.color = new Color((int) (r + Math.random() * r), (int)(g + Math.random() * g), (int) (b + Math.random() * b));
        life = 30 + Math.random() * 20; // frames of life
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void update() {
        x += vx;
        y += vy;
        life -= 1;

        // Simple fading
        vx *= 0.95;
        vy *= 0.95;
    }

    public void draw(Graphics2D g2) {
        if (life > 0) {
            float alpha = (float)(life / 50.0);
            alpha = Math.max(0, Math.min(1, alpha));

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(color);
            g2.fillOval((int)x, (int)y, (int)size, (int)size);
        }
    }
}
