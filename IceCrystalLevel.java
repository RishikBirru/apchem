import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IceCrystalLevel extends LevelBase {
    private List<Rectangle> bondingSites;
    private int bondsFormed = 0;
    private final int BONDS_NEEDED = 4;

    public IceCrystalLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "CONDENSATION:\n" +
                        "1. Find cold zones (blue areas)\n" +
                        "2. Lower your entropy\n" +
                        "3. Reach 100% condensation to form rain";
    }

    @Override
    protected void initializeLevel() {
        bondingSites = new ArrayList<>();
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int radius = 150;
        
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI/3 * i;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            bondingSites.add(new Rectangle(x, y, 30, 30));
        }
        startGameLoop(60);
    }

    @Override
    public void resetLevel() {
        bondingSites.clear();
        bondsFormed = 0;
        levelComplete = false;
        initializeLevel();
    }

    @Override
    protected void updateLevel() {
        if (levelComplete) return;
        
        for (int i = 0; i < bondingSites.size(); i++) {
            if (player.getBounds().intersects(bondingSites.get(i))) {
                bondingSites.remove(i);
                bondsFormed++;
                player.decreaseEntropy();
                break;
            }
        }
        
        if (bondsFormed >= BONDS_NEEDED) completeLevel();
    }

    @Override
    protected void drawBackground(Graphics2D g2d) {
        // Winter background
        g2d.setColor(new Color(200, 230, 255));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw snowflakes
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 30; i++) {
            int x = (int)(Math.random() * getWidth());
            int y = (int)(Math.random() * getHeight());
            drawSnowflake(g2d, x, y, 10);
        }
    }

    private void drawSnowflake(Graphics2D g2d, int x, int y, int size) {
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI/3 * i;
            int x2 = x + (int)(size * Math.cos(angle));
            int y2 = y + (int)(size * Math.sin(angle));
            g2d.drawLine(x, y, x2, y2);
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

        // Draw bonding sites
        g2d.setColor(new Color(0, 150, 255, 150));
        for (Rectangle site : bondingSites) {
            g2d.fillOval(site.x, site.y, site.width, site.height);
        }
        
        // Draw bonds
        g2d.setColor(new Color(0, 100, 255, 100));
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, 
                                      BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        for (Rectangle site : bondingSites) {
            g2d.drawLine(centerX, centerY, site.x + 15, site.y + 15);
        }
        
        // Draw UI
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Bonds: " + bondsFormed + "/" + BONDS_NEEDED, 20, 60);
        g2d.drawString("Entropy: " + player.getEntropy(), 20, 90);
    }

    @Override
    protected String getChemistryExplanation() {
        return """
        ICE CRYSTAL CHEMISTRY (Units 3.12, 2.1)
        ---------------------------------------
        Hydrogen bonding in ice:
        1. Tetrahedral geometry (109.5° angles)
        2. Hexagonal crystal lattice
        3. Lower density than liquid water
        
        Key Concepts:
        - Each H₂O forms 4 hydrogen bonds
        - Bond energy: ~20 kJ/mol
        - Open structure causes expansion
        
        Thermodynamics:
        ΔH_fusion = 6.01 kJ/mol
        ΔS_fusion = 22.0 J/(mol·K)
        """;
    }

    @Override
    protected String[] getMCQOptions() {
        return new String[] {
            "Ice floats because it's denser than water (False)",
            "Hydrogen bonds are stronger than covalent bonds (False)",
            "Water's high heat capacity comes from hydrogen bonding (True)",
            "All solids expand when freezing (False - water is unusual)"
        };
    }

    @Override
    protected String getNextLevel() { return "Cloud"; }
}