import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CloudLevel extends LevelBase {
    private List<Rectangle> coldZones;
    private int condensationProgress = 0;
    private final int CONDENSATION_TARGET = 100;

    public CloudLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "CLOUD FORMATION:\n" +
                        "1. Find cold zones (blue areas)\n" +
                        "2. Lower your entropy\n" +
                        "3. Reach 100% condensation to form rain";
        coldZones = new ArrayList<>(); // Initialize in constructor
        initializeLevel();
    }

    @Override
    protected void initializeLevel() {
        coldZones.clear();
        coldZones.add(new Rectangle(150, 200, 80, 80));
        coldZones.add(new Rectangle(400, 300, 80, 80));
        coldZones.add(new Rectangle(600, 150, 80, 80));
        
        // Set player position away from cold zones
        player.resetPosition();
        player.setEntropy(90);
        player.setX(getWidth()/4);  // Spawn at left quarter of screen
        player.setY(getHeight()/2);
        
        condensationProgress = 0;
        levelComplete = false;
        startGameLoop(60);
    }

    @Override
    public void resetLevel() {
        coldZones.clear();
        condensationProgress = 0;
        levelComplete = false;
        initializeLevel();
    }

    @Override
    protected void updateLevel() {
        if (levelComplete || !window.isCurrentLevel("Cloud")) return;
        
        for (Rectangle coldZone : coldZones) {
            if (player.getBounds().intersects(coldZone)) {
                player.decreaseEntropy(1);
                condensationProgress += 1;
                break;
            }
        }
        
        if (condensationProgress >= CONDENSATION_TARGET) completeLevel();
    }

    @Override
    protected void drawBackground(Graphics2D g2d) {
        // Sky background
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(135, 206, 235),
            0, getHeight(), new Color(225, 225, 225));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw cloud formations
        g2d.setColor(new Color(255, 255, 255, 200));
        for (int i = 0; i < 5; i++) {
            int x = 100 + i*150;
            int y = 30 + (int)(Math.random() * 2);
            g2d.fillOval(x, y, 120, 80);
            g2d.fillOval(x+30, y+20, 150, 90);
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

        // Draw cold zones
        g2d.setColor(new Color(100, 100, 255, 100));
        for (Rectangle zone : coldZones) {
            g2d.fillOval(zone.x, zone.y, zone.width, zone.height);
        }
        
        // Draw rain when condensing
        if (condensationProgress > 50) {
            g2d.setColor(new Color(0, 100, 255, 100));
            for (int i = 0; i < 50; i++) {
                int x = (int)(Math.random() * getWidth());
                int y = (int)(Math.random() * getHeight());
                g2d.drawLine(x, y, x, y+10);
            }
        }
        
        // Draw UI
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Condensation: " + condensationProgress + "%", 20, 60);
        g2d.drawString("Entropy: " + player.getEntropy(), 20, 90);
    }

    @Override
    protected String getPhenomenonDescription() {
        return "Simulating nucleation and droplet formation in atmospheric condensation.";
    }

    @Override
    protected String getChemistryExplanation() {
        return """
        IMFS and Exo/Endothermic Reactions (3.01 and 6.01)
        ----------------------------------------
        Observations:
        • Blue zones are cold air regions
        • Rain droplets formed happens when entropy is decreasing

        AP Chemistry:
        • Water molecules reform hydrogen bonds
        • Nucleation requires dust/ions (like in-game cold zones)

        • Gas → liquid = ΔS < 0 (more ordered)
        • Exothermic process (releases heat)
        
        Connection:
        • How real clouds form at dew point
        • Why mornings are foggy (Overnight cooling)
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Condensation releases heat",
            "Dew point depends only on temperature",
            "Clouds form when air is supersaturated",
            "All phase changes decrease entropy"
        };
    }

    @Override
    protected boolean[] getMCQAnswers() {
        return new boolean[] {
            true,  // True - exothermic process
            false, // False - depends on humidity too
            true,  // True - requires supersaturation
            false  // False - some increase entropy
        };
    }

    @Override
    protected String getNextLevel() { return "EndScreen"; } // Loop back or add "Credits"
}