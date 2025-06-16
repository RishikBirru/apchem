import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int width = 30, height = 30;
    private int entropy = 50;
    private int energy = 50;
    private Color color = Color.BLUE;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private int speed = 2;

    public void updatePosition() {
        int dx = 0, dy = 0;
        
        if (pressedKeys.contains(KeyEvent.VK_W)) dy -= speed;
        if (pressedKeys.contains(KeyEvent.VK_S)) dy += speed;
        if (pressedKeys.contains(KeyEvent.VK_A)) dx -= speed;
        if (pressedKeys.contains(KeyEvent.VK_D)) dx += speed;
        
        // Normalize diagonal movement speed
        if (dx != 0 && dy != 0) {
            dx = (int)(dx * 0.7071); // 1/sqrt(2)
            dy = (int)(dy * 0.7071);
        }
        
        x += dx;
        y += dy;
    }

    public void keyPressed(int keyCode) {
        pressedKeys.add(keyCode);
    }

    public void keyReleased(int keyCode) {
        pressedKeys.remove(keyCode);
    }



    public Player() {
        x = 100;
        y = 300;
    }

    // Add all missing methods
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getEnergy() { return energy; }
    public int getEntropy() { return entropy; }

    public void increaseEnergy() {
        energy = Math.min(100, energy + 10);
    }

    public void increaseEntropy() {
        entropy = Math.min(100, entropy + 10);
    }

    public void decreaseEntropy() {
        entropy = Math.max(0, entropy - 10);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void resetPosition() {
        x = 100;
        y = 300;
    }

    public void draw(Graphics g) {

        // Draw player (more visible version)
        g.setColor(new Color(0, 100, 255, 200));  // More opaque blue
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);  // Outline

        g.setColor(new Color(0, 0, 255, 150 + energy/2));
        g.fillOval(x, y, width, height);
        
        g.setColor(Color.RED);
        g.fillRect(10, 10, energy * 2, 20);
        g.setColor(Color.GREEN);
        g.fillRect(10, 40, entropy * 2, 20);
        
        g.setColor(Color.BLACK);
        g.drawString("Energy: " + energy, 220, 25);
        g.drawString("Entropy: " + entropy, 220, 55);
    }
}