import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Player player;

    public GameWindow() {
        setTitle("Molecular Odyssey");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        player = new Player();

        // Properly add levels
        mainPanel.add(new TutorialLevel(player, this), "Tutorial");
        mainPanel.add(new AuroraLevel(player, this), "Aurora");
        mainPanel.add(new WaterspoutLevel(player, this), "Waterspout");
        mainPanel.add(new IceCrystalLevel(player, this), "IceCrystal");
        mainPanel.add(new CloudLevel(player, this), "Cloud");

        add(mainPanel);
        switchToLevel("Tutorial");
    }

    public void switchToLevel(String levelName) {
        cardLayout.show(mainPanel, levelName);
        player.resetPosition();
    }
}