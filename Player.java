import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private final GameWindow window;
    private int x, y;
    private final int diameter = 30;
    private int entropy = 50;
    private int energy = 50;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final int baseSpeed = 3;
    
    public enum MolecularState { SOLID, LIQUID, GAS }
    private MolecularState state = MolecularState.LIQUID;
    private boolean movementEnabled = true;
    private boolean inputsEnabled = true;



    public Player(GameWindow window) {
        this.window = window;
        resetPosition();
    }

    public void resetMovement() {
        pressedKeys.clear();
        movementEnabled = true;
        inputsEnabled = true;
    }

    public void update() {
        if (!movementEnabled || !inputsEnabled) return;
        
        int dx = 0, dy = 0;
        int speed = getActualSpeed();
        
        if (pressedKeys.contains(KeyEvent.VK_W)) dy -= speed;
        if (pressedKeys.contains(KeyEvent.VK_S)) dy += speed;
        if (pressedKeys.contains(KeyEvent.VK_A)) dx -= speed;
        if (pressedKeys.contains(KeyEvent.VK_D)) dx += speed;
        
        // Normalize diagonal movement
        if (dx != 0 && dy != 0) {
            dx = (int)(dx * 0.7071);
            dy = (int)(dy * 0.7071);
        }
        
        x += dx;
        y += dy;
        constrainToBounds();
    }

    public void setInputsEnabled(boolean enabled) {
        this.inputsEnabled = enabled;
        if (!enabled) {
            pressedKeys.clear();
        }
    }

    public void setMovementEnabled(boolean enabled) {
        this.movementEnabled = enabled;
        if (!enabled) {
            pressedKeys.clear(); // Clear any pressed keys when disabling movement
        }
    }

    private void updateState() {
        if (entropy > 75) state = MolecularState.GAS;
        else if (entropy < 25) state = MolecularState.SOLID;
        else state = MolecularState.LIQUID;
    }

    private int getActualSpeed() {
        return (int)(baseSpeed * (state == MolecularState.GAS ? 1.5 : 1));
    }

    private void constrainToBounds() {
        x = Math.max(0, Math.min(x, window.getWidth() - diameter));
        y = Math.max(0, Math.min(y, window.getHeight() - diameter));
    }

    public void draw(Graphics2D g2d) {
        // State-specific colors and effects
        switch(state) {
            case SOLID:
                g2d.setColor(new Color(100, 100, 255, 220));
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setColor(Color.WHITE);
                g2d.drawString("❄", x + diameter/2 - 5, y + diameter/2 + 5); // Snowflake icon
                break;
            case LIQUID:
                g2d.setColor(new Color(100, 150, 255, 180));
                g2d.fillOval(x, y, diameter, diameter);
                // Ripple effect
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.drawOval(x-5, y-5, diameter+10, diameter+10);
                break;
            case GAS:
                g2d.setColor(new Color(200, 200, 255, 120));
                g2d.fillOval(x, y, diameter, diameter);
                // Vapor effect
                for (int i = 0; i < 3; i++) {
                    g2d.fillOval(x + 5*i, y - 5*i, diameter - 5*i, diameter - 5*i);
                }
                break;
        }
        
        // State indicator
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(state.toString(), x, y - 5);
    }

    public void setEntropy(int value) {
        this.entropy = Math.max(0, Math.min(100, value));
        updateState(); // Update molecular state based on new entropy
    }

    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getEnergy() { return energy; }
    public int getEntropy() { return entropy; }
    public Rectangle getBounds() { return new Rectangle(x, y, diameter, diameter); }
    public MolecularState getState() { return state; } // ADDED THIS METHOD
    public void increaseEnergy() { energy = Math.min(100, energy + 10); }
    public void decreaseEnergy() { energy = Math.max(0, energy - 10); }
    public void increaseEntropy() { entropy = Math.min(100, entropy + 10); }
    public void decreaseEntropy() { entropy = Math.max(0, entropy - 10); }
    public void keyPressed(int keyCode) { pressedKeys.add(keyCode); }
    public void keyReleased(int keyCode) { pressedKeys.remove(keyCode); }
    public void resetPosition() { x = window.getWidth()/2; y = window.getHeight()/2; }
}