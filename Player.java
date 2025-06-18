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
    private double kineticEnergy = 0.5;
    private boolean movementEnabled = true;
    private boolean inputsEnabled = true;



    public Player(GameWindow window) {
        this.window = window;
        resetPosition();
    }

    public void update() {
        if (!movementEnabled) return;
        if (!inputsEnabled) return;



        updateState();
        int dx = 0, dy = 0;
        
        if (pressedKeys.contains(KeyEvent.VK_W)) dy -= getActualSpeed();
        if (pressedKeys.contains(KeyEvent.VK_S)) dy += getActualSpeed();
        if (pressedKeys.contains(KeyEvent.VK_A)) dx -= getActualSpeed();
        if (pressedKeys.contains(KeyEvent.VK_D)) dx += getActualSpeed();
        
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
        
        kineticEnergy = switch(state) {
            case SOLID -> 0.1 + (entropy/100.0);
            case LIQUID -> 0.5 + (entropy/50.0);
            case GAS -> 1.0 + (entropy/25.0);
        };
    }

    private int getActualSpeed() {
        return (int)(baseSpeed * (state == MolecularState.GAS ? 1.5 : 1));
    }

    private void constrainToBounds() {
        x = Math.max(0, Math.min(x, window.getWidth() - diameter));
        y = Math.max(0, Math.min(y, window.getHeight() - diameter));
    }

    public void draw(Graphics2D g2d) {
        Color fillColor = switch(state) {
            case SOLID -> new Color(100, 100, 255, 220);
            case LIQUID -> new Color(100, 150, 255, 180);
            case GAS -> new Color(200, 200, 255, 120);
        };
        
        g2d.setColor(fillColor);
        g2d.fillOval(x, y, diameter, diameter);
        
        if (state == MolecularState.GAS) {
            g2d.setColor(Color.RED);
            g2d.drawLine(x+diameter/2, y+diameter/2, 
                        x+diameter/2+(int)(10*kineticEnergy), 
                        y+diameter/2);
        }
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
    public void resetMovement() { pressedKeys.clear(); }
    public void resetPosition() { x = window.getWidth()/2; y = window.getHeight()/2; }
}