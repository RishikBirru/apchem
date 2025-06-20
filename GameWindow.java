import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final Player player;
    private final Map<String, LevelBase> levels = new HashMap<>();
    private String currentLevelName; 
    private final EndScreen endScreen;




    public GameWindow() {
        setTitle("Molecular Odyssey");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        player = new Player(this);
        initializeLevels();
        add(mainPanel);
        setVisible(true);

        endScreen = new EndScreen(this);
        mainPanel.add(endScreen, "EndScreen");
        
        // Start the first level after window is ready
        SwingUtilities.invokeLater(() -> {
            switchToLevel("Tutorial");
        });
    }


    public boolean isCurrentLevel(String levelName) {
        return currentLevelName != null && 
            currentLevelName.equals(levelName.replace("Level", ""));
    }

    public void switchToLevel(String levelName) {
        if ("EndScreen".equals(levelName)) {
            cardLayout.show(mainPanel, "EndScreen");
            return;
        }

        System.out.println("Switching to level: " + levelName); // Debug output
        currentLevelName = levelName; // Track current level
        player.resetMovement();
        player.resetPosition();
        levels.get(levelName).resetLevel();
        cardLayout.show(mainPanel, levelName);
        levels.get(levelName).requestFocusInWindow();
        
        // Only show intro message for Tutorial level
        if ("Tutorial".equals(levelName)) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Welcome to Molecular Odyssey!\n\n" +
                    levels.get(levelName).levelObjective,
                    "Phase Change Challenge", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    private void initializeLevels() {
        levels.put("Tutorial", new TutorialLevel(player, this));
        levels.put("Aurora", new AuroraLevel(player, this));
        levels.put("Waterspout", new WaterspoutLevel(player, this));
        levels.put("IceCrystal", new IceCrystalLevel(player, this));
        levels.put("Cloud", new CloudLevel(player, this));
        
        levels.forEach((name, level) -> mainPanel.add(level, name));
    }
}