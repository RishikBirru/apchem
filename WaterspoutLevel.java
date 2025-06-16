public class WaterspoutLevel extends LevelBase {
    public WaterspoutLevel(Player player, GameWindow window) {
        super(player, window);
        objective = "Rise upward (hold W) to evaporate"; // Update instruction
    }

    @Override
    public void checkObjective() {
        if (player.getY() < 100) { // Check Y position continuously
            player.increaseEntropy();
            if (player.getEntropy() > 80) {
                showChemistryPopup("Phase change: Liquid → Gas! Entropy increases");
                window.switchToLevel("IceCrystal");
            }
        }
    }

    @Override
    public void interact() {
        if (player.getY() < 100) {
            player.increaseEntropy();
            showChemistryPopup("Phase change: Liquid → Gas! Entropy increases");
        }
    }
}