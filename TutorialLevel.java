import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TutorialLevel extends LevelBase {
    private List<Rectangle> energyOrbs;
    private int orbsCollected = 0;

    public TutorialLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "BASIC MOVEMENT:\n" +
                "1. Use WASD keys to move\n" +
                "2. Collect all 3 energy orbs\n" +
                "3. Watch your entropy increase!";
        energyOrbs = new ArrayList<>(); // Initialize here
        initializeLevel(); // Then populate
    }

    @Override
    protected void initializeLevel() {
        energyOrbs.clear(); // Now safe to call
        energyOrbs.add(new Rectangle(150, 200, 25, 25));
        energyOrbs.add(new Rectangle(400, 300, 25, 25));
        energyOrbs.add(new Rectangle(600, 150, 25, 25));
        startGameLoop(60);
    }

    @Override
    public void resetLevel() {
        orbsCollected = 0;
        levelComplete = false;
        initializeLevel(); // Just reinitialize
    }

    @Override
    protected void updateLevel() {
        if (levelComplete) return;
        
        for (int i = 0; i < energyOrbs.size(); i++) {
            if (player.getBounds().intersects(energyOrbs.get(i))) {
                energyOrbs.remove(i);
                player.increaseEntropy();
                orbsCollected++;
                break;
            }
        }
        
        if (orbsCollected >= 3) completeLevel();
    }

    @Override
    protected void drawBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, new Color(173, 216, 230), 
                                                0, getHeight(), new Color(100, 149, 237));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void drawUI(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(getWidth()-250, 20, 230, 100, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(getWidth()-250, 20, 230, 100, 20, 20);

        String[] instructions = levelObjective.split("\n");
        for (int i = 0; i < instructions.length; i++) {
            g2d.drawString(instructions[i], getWidth()-240, 40 + i*20);
        }

        g2d.setColor(Color.YELLOW);
        for (Rectangle orb : energyOrbs) {
            g2d.fillOval(orb.x, orb.y, orb.width, orb.height);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Orbs: " + orbsCollected + "/3", 20, 60);
        g2d.drawString("Entropy: " + player.getEntropy(), 20, 90);
    }

    @Override
    protected String getChemistryExplanation() {
        return """
        ENTROPY AND MOLECULAR MOTION (Unit 9.1)
        ---------------------------------------
        Entropy (S) measures molecular disorder:
        - More movement = higher entropy
        - Phase changes affect entropy dramatically
        - ΔS > 0 for evaporation (liquid → gas)
        
        Key Equations:
        ΔS = q_rev/T (reversible process)
        ΔS_universe = ΔS_system + ΔS_surroundings > 0
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Entropy decreases when water evaporates (False)",
            "Gases have higher entropy than liquids (True)",
            "All spontaneous processes increase entropy (True)",
            "Entropy is measured in J/mol (False - it's J/K·mol)"
        };
    }

    @Override
    protected String getNextLevel() { return "Aurora"; }
}