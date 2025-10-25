package particle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleManager {
    private final List<Particle> particles = new ArrayList<>();
    Color color = new Color(200, 200, 200);
    int spreadFactor = 15;

    public void emit(double x, double y, double angle, int count) {
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(x, y, color, spreadFactor, angle));
        }
    }

    public void setColor(int r, int g, int b) {
        color = new Color(r, g, b);
    }

    public void setSpreadFactor(int value) {
        spreadFactor = value;
    }

    public void update() {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update();
            if (!p.isAlive()) {
                it.remove();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Particle p : particles) {
            p.draw(g2);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
