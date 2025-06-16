import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TutorialLevel extends LevelBase {
    private List<Rectangle> orbs;
    private int orbsCollected = 0;
    private boolean showSuccessMessage = false;

    public TutorialLevel(Player player, GameWindow window) {
        super(player, window);
        objective = "Collect 3 energy orbs (walk into them) to increase entropy";
        
        // Initialize orbs
        orbs = new ArrayList<>();
        orbs.add(new Rectangle(500, 300, 30, 30));
        orbs.add(new Rectangle(200, 400, 30, 30));
        orbs.add(new Rectangle(300, 150, 30, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw orbs
        g.setColor(Color.YELLOW);
        for (Rectangle orb : orbs) {
            g.fillOval(orb.x, orb.y, orb.width, orb.height);
        }
        
        // Draw success message
        if (showSuccessMessage) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Level Complete!", 300, 100);
        }
    }

    @Override
    public void checkObjective() {
        // Check for orb collisions automatically (no need to press E)
        for (int i = 0; i < orbs.size(); i++) {
            if (player.getBounds().intersects(orbs.get(i))) {
                orbs.remove(i);
                player.increaseEntropy();
                orbsCollected++;
                repaint();
                break;
            }
        }
        
        // Remove upward movement entropy increase
        // (This is now handled only by orb collection)
        
        if (orbsCollected >= 3) {
            showSuccessMessage = true;
            timer.stop(); // Stop the game loop
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Show success for 2 seconds
                    window.switchToLevel("Aurora");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void interact() {
        // Not used for orb collection anymore
    }
}