import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaterspoutLevel extends LevelBase {
    private List<Rectangle> heatSources;
    private int evaporationProgress = 0;
    public WaterspoutLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "EVAPORATION CHALLENGE:\n1. Collect heat from red zones (WASD to move)\n2. Rise upward to evaporate\n3. Reach 100% evaporation";
        heatSources = new ArrayList<>(); // Initialize here
        initializeLevel(); // Then populate
    }

    @Override
    protected void initializeLevel() {
        heatSources.clear(); // Now safe to call
        heatSources.add(new Rectangle(100, 100, 60, 60));
        heatSources.add(new Rectangle(400, 200, 60, 60));
        heatSources.add(new Rectangle(600, 300, 60, 60));
        evaporationProgress = 0;
        startGameLoop(60);
    }

    @Override
    public void resetLevel() {
        initializeLevel(); // Simplified reset
    }

    @Override
    protected void updateLevel() {
    if (levelComplete || !window.isCurrentLevel("WaterspoutLevel")) return;

        boolean inHeatSource = false;
        for (Rectangle heat : heatSources) {
            if (player.getBounds().intersects(heat)) {
                player.increaseEntropy();
                evaporationProgress += 2; // Reduced rate
                inHeatSource = true;
                break;
            }
        }
        
        // Remove the automatic progress when above middle
        // if (player.getY() < getHeight()/2) evaporationProgress++;
        
        if (evaporationProgress >= 100) completeLevel();
    }

    @Override
    protected void drawBackground(Graphics2D g2d) {
        // Ocean background
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(0, 105, 148),
            0, getHeight(), new Color(0, 191, 255));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw waterspout
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillOval(getWidth()/2 - 50, getHeight()/2 - 200, 100, 400);
    }

    @Override
    protected String getPhenomenonDescription() {
        return "Demonstrating evaporation thermodynamics and hydrogen bond breaking.";
    }

    @Override
    protected String getRelevantEquations() {
        return """
        Key Equations:
        - Clausius-Clapeyron: ln(P₂/P₁) = (ΔH_vap/R)(1/T₁ - 1/T₂)
        - Heat: q = m·ΔH_vap
        - Entropy: ΔS = ΔH_vap/T_boil
        - Vapor pressure: P = exp(-ΔH_vap/RT + C)
        """;
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

        // Draw heat sources
        g2d.setColor(new Color(255, 100, 0, 150));
        for (Rectangle heat : heatSources) {
            g2d.fillOval(heat.x, heat.y, heat.width, heat.height);
        }
        
        // Draw UI
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Evaporation: " + evaporationProgress + "%", 20, 60);
        g2d.drawString("Entropy: " + player.getEntropy(), 20, 90);
        
        // Draw phase indicator
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(getWidth() - 120, 20, 100, 20);
        g2d.setColor(Color.WHITE);
        g2d.drawString(player.getState().toString(), getWidth() - 110, 35);
    }

    @Override
    protected String getChemistryExplanation() {
        return """
        WATERSHED CHEMISTRY (Units 3.12, 8.1)
        -------------------------------------
        Evaporation involves:
        1. Overcoming hydrogen bonds (IMFs)
        2. Energy input (ΔH > 0)
        3. Entropy increase (ΔS > 0)
        
        Key Concepts:
        - Hydrogen bonding: 20 kJ/mol
        - Vapor pressure equilibrium
        - Clausius-Clapeyron equation:
          ln(P) = -ΔH_vap/R(1/T) + C
        
        Thermodynamics:
        ΔG = ΔH - TΔS
        Spontaneous when ΔG < 0
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Ice floats because it's denser than water",
            "Hydrogen bonds are stronger than covalent bonds",
            "Water's high heat capacity comes from hydrogen bonding",
            "All solids expand when freezing"
        };
    }

    @Override
    protected boolean[] getMCQAnswers() {
        return new boolean[] {
            false, // False - less dense
            false, // False - weaker than covalent
            true,  // True - due to hydrogen bonding
            false  // False - water is unusual
        };
    }

    @Override
    protected String getNextLevel() { return "IceCrystal"; }
}