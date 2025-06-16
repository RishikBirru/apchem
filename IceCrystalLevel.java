import java.awt.*;

public class IceCrystalLevel extends LevelBase {
    private boolean[][] bonds = new boolean[3][3];
    
    public IceCrystalLevel(Player player, GameWindow window) {
        super(player, window);
        objective = "Form hydrogen bonds (press E near molecules)";
    }

    @Override
    public void interact() {
        // Simplified bonding logic
        if (player.getBounds().intersects(new Rectangle(400, 400, 30, 30))) {
            bonds[1][1] = true;
            showChemistryPopup("Hydrogen bond formed! Water molecules align in hexagons");
        }
    }

    @Override
    public void checkObjective() {
        if (bonds[1][1]) {
            window.switchToLevel("Cloud");
        }
    }
}