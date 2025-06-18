import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IceCrystalLevel extends LevelBase {
    private List<Rectangle> bondingSites;
    private int bondsFormed = 0;
    private final int BONDS_NEEDED = 4;

    public IceCrystalLevel(Player player, GameWindow window) {
        super(player, window);
        levelObjective = "ICE CRYSTAL FORMATION:\n" +
                        "1. Form 4 hydrogen bonds (blue zones)\n" +
                        "2. Each bond decreases entropy\n" +
                        "3. Observe hexagonal lattice formation";
        bondingSites = new ArrayList<>(); // Initialize in constructor
        initializeLevel();
    }

    @Override
    protected void initializeLevel() {
        bondingSites.clear();
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
        ICE CRYSTAL CHEMISTRY (AP Chem Units 3.12, 2.1)
        -----------------------------------------------
        Hydrogen Bonding in Water:
        - Tetrahedral geometry (109.5° bond angles)
        - Hexagonal crystal lattice structure
        - Lower density than liquid water (due to open lattice)
        
        Key AP Chemistry Concepts:
        - Each H₂O molecule forms 4 hydrogen bonds
        - Hydrogen bond strength: ~20 kJ/mol (vs covalent O-H bond: 463 kJ/mol)
        - ΔH_fusion = +6.01 kJ/mol (endothermic)
        - ΔS_fusion = +22.0 J/(mol·K)
        
        Phase Diagram Implications:
        - Negative slope in P-T diagram (unique to water)
        - Hydrogen bonding explains water's:
          * High specific heat (4.184 J/g°C)
          * High heat of vaporization (40.7 kJ/mol)
          * Surface tension (72.8 mN/m at 20°C)
        """;
    }

    @Override
    protected String getPhenomenonDescription() {
        return "Forming crystalline structures through hydrogen bonding in water.";
    }

    @Override
    protected String getRelevantEquations() {
        return """
        Key Equations:
        - ΔH_fusion = 6.01 kJ/mol
        - ΔS_fusion = 22.0 J/(mol·K)
        - Coulomb's Law: F = k(q₁q₂)/r²
        - Lattice energy calculations
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
    protected String getNextLevel() { return "Cloud"; }
}