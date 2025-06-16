import java.awt.Rectangle;

public class AuroraLevel extends LevelBase {
    public AuroraLevel(Player player, GameWindow window) {
        super(player, window);
        objective = "Absorb high-energy particles (blue circles)";
    }

    @Override
    public void interact() {
        if (player.getBounds().intersects(new Rectangle(600, 200, 30, 30))) {
            player.increaseEnergy();
            showChemistryPopup("Energy absorbed! Electrons jump to higher levels");
        }
    }

    @Override
    public void checkObjective() {
        if (player.getEnergy() > 80) {
            showChemistryPopup("Energy emitted as light! This causes aurora colors");
            window.switchToLevel("Waterspout");
        }
    }
}