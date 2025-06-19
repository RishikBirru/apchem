import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TutorialLevel extends LevelBase {
    private List<Rectangle> energyOrbs;
    private int orbsCollected = 0;
    private boolean initialized = false;


    public TutorialLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "BASIC MOVEMENT:\n" +
                "1. Use WASD keys to move\n" +
                "2. Collect all 3 energy orbs\n" +
                "3. Watch your entropy increase!";
        energyOrbs = new ArrayList<>();
    }

    
    @Override
    protected void initializeLevel() {
        if (initialized) return;
        
        energyOrbs.clear();
        energyOrbs.add(new Rectangle(150, 200, 25, 25));
        energyOrbs.add(new Rectangle(400, 300, 25, 25));
        energyOrbs.add(new Rectangle(600, 150, 25, 25));
        
        orbsCollected = 0;
        levelComplete = false;
        player.setEntropy(50);
        player.resetMovement();
        player.resetPosition();
        
        startGameLoop(60);
        requestFocusInWindow();
        initialized = true;
    }

    @Override
    protected void updateLevel() {
        if (levelComplete || !window.isCurrentLevel("TutorialLevel")) return;

        // Check orb collection
        for (int i = 0; i < energyOrbs.size(); i++) {
            if (player.getBounds().intersects(energyOrbs.get(i))) {
                energyOrbs.remove(i);
                player.increaseEntropy();
                orbsCollected++;
                break;
            }
        }
        
        // Only complete when collecting all orbs
        if (orbsCollected >= 3) {
            completeLevel();
        }
    }


    @Override
    protected String getPhenomenonDescription() {
        return "Observing how molecular movement affects entropy changes in different states of matter.";
    }

    @Override
    protected String getRelevantEquations() {
        return """
        Key Equations:
        - ΔS = q_rev/T (reversible process)
        - ΔG = ΔH - TΔS (Gibbs free energy)
        - ΔS_universe = ΔS_system + ΔS_surroundings
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Entropy decreases when water evaporates",
            "Gases have higher entropy than liquids",
            "All spontaneous processes increase entropy",
            "Entropy is measured in J/mol"
        };
    }

    @Override
    protected boolean[] getMCQAnswers() {
        return new boolean[] {
            false,  // First option is incorrect
            true,   // Second option is correct
            true,   // Third option is correct
            false   // Fourth option is incorrect
        };
    }



    @Override
    public void resetLevel() {
        initializeLevel();
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
        // Draw instruction panel
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(getWidth()-250, 20, 230, 100, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(getWidth()-250, 20, 230, 100, 20, 20);

        String[] instructions = levelObjective.split("\n");
        for (int i = 0; i < instructions.length; i++) {
            g2d.drawString(instructions[i], getWidth()-240, 40 + i*20);
        }

        // Draw orbs
        g2d.setColor(Color.YELLOW);
        for (Rectangle orb : energyOrbs) {
            g2d.fillOval(orb.x, orb.y, orb.width, orb.height);
        }
        
        // Draw UI elements
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
        
        Key AP Chemistry Concepts:
        - Spontaneous processes favor increased entropy
        - Gases > Liquids > Solids for entropy
        - ΔS° = ΣS°(products) - ΣS°(reactants)
        
        Thermodynamics:
        ΔG = ΔH - TΔS
        ΔS_universe = ΔS_system + ΔS_surroundings > 0
        """;
    }

    @Override
    protected String getNextLevel() { return "Aurora"; }
}