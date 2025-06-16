public class CloudLevel extends LevelBase {
    public CloudLevel(Player player, GameWindow window) {
        super(player, window);
        objective = "Descend (press S) to condense and form rain";
    }

    @Override
    public void interact() {
        if (player.getY() > 500) {
            player.decreaseEntropy();
            showChemistryPopup("Phase change: Gas → Liquid! Entropy decreases");
        }
    }

    @Override
    public void checkObjective() {
        if (player.getEntropy() < 30) {
            showChemistryPopup("Congratulations! You completed the molecular journey");
        }
    }
}