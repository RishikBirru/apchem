import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AuroraLevel extends LevelBase {
    private List<Rectangle> solarParticles;
    private int particlesAbsorbed = 0;
    private final Color[] AURORA_COLORS = {
        new Color(0, 255, 127, 50),  // Green
        new Color(138, 43, 226, 50), // Violet
        new Color(255, 0, 255, 50)   // Magenta
    };

    public AuroraLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "AURORA MECHANICS:\n" +
                        "1. Absorb 10 solar particles (yellow circles)\n" +
                        "2. Particles excite your electrons\n" +
                        "3. Energy emitted creates aurora colors";
        solarParticles = new ArrayList<>(); // Initialize here
        initializeLevel(); // Then populate
    }

    @Override
    protected void initializeLevel() {
        solarParticles.clear(); // Now safe to call
        for (int i = 0; i < 15; i++) {
            solarParticles.add(new Rectangle(
                (int)(Math.random() * 700 + 50),
                (int)(Math.random() * 400 + 50),
                20, 20
            ));
        }
        startGameLoop(60);
    }

    @Override
    public void resetLevel() {
        particlesAbsorbed = 0;
        levelComplete = false;
        initializeLevel(); // Just reinitialize
    }
    @Override
    protected void updateLevel() {
        if (levelComplete) return;
        
        for (int i = 0; i < solarParticles.size(); i++) {
            if (player.getBounds().intersects(solarParticles.get(i))) {
                solarParticles.remove(i);
                player.increaseEnergy();
                particlesAbsorbed++;
                break;
            }
        }
        
        if (particlesAbsorbed >= 10) completeLevel();
    }

    @Override
    protected void drawBackground(Graphics2D g2d) {
        // Space background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw stars
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 200; i++) {
            int x = (int)(Math.random() * getWidth());
            int y = (int)(Math.random() * getHeight());
            int size = 1 + (int)(Math.random() * 3);
            g2d.fillOval(x, y, size, size);
        }
        
        // Draw aurora effect
        for (int i = 0; i < 3; i++) {
            g2d.setColor(AURORA_COLORS[i]);
            g2d.fillRect(0, 100 + i*50, getWidth(), 40);
        }
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
        // Draw particles
        g2d.setColor(Color.YELLOW);
        for (Rectangle particle : solarParticles) {
            g2d.fillOval(particle.x, particle.y, particle.width, particle.height);
        }
        
        // Draw UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Particles: " + particlesAbsorbed + "/10", 20, 60);
        g2d.drawString("Energy: " + player.getEnergy(), 20, 90);
    }

    @Override
    protected String getChemistryExplanation() {
        return """
        AURORA CHEMISTRY (Units 6.3, 1.4)
        --------------------------------
        Solar particles excite electrons:
        1. e⁻ absorb energy and jump to higher orbitals
        2. When they fall back, emit specific wavelengths
        3. Colors correspond to energy differences:
           - Green: 557.7 nm (Oxygen)
           - Violet: 427.8 nm (N₂⁺)
           - Red: 630.0 nm (Oxygen)
        
        Equations:
        E = hν = hc/λ
        ΔE = E_final - E_initial
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Auroras are caused by proton collisions (False - electrons)",
            "Green light has higher energy than red (True)",
            "The photoelectric effect demonstrates light's wave nature (False - particle)",
            "Energy levels are quantized (True)"
        };
    }

    @Override
    protected String getNextLevel() { return "Waterspout"; }
}