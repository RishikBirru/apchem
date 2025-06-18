import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class LevelBase extends JPanel {
    protected final Player player;
    protected final GameWindow window;
    protected Timer gameLoop;
    protected String levelObjective;
    protected boolean levelComplete = false;

    public LevelBase(Player player, GameWindow window) {
        this.player = player;
        this.window = window;
        setBackground(new Color(240, 248, 255));
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e.getKeyCode());
            }
        });
    }

    protected void showChemistryExplanation() {
        player.setInputsEnabled(false);
        
        // Create popup content
        JTextArea explanation = new JTextArea(getChemistryExplanation());
        explanation.setEditable(false);
        explanation.setLineWrap(true);
        explanation.setWrapStyleWord(true);
        
        JOptionPane.showMessageDialog(this, explanation, "Chemistry Explanation", 
                                    JOptionPane.INFORMATION_MESSAGE);
        
        player.setInputsEnabled(true);
    }

    protected void startGameLoop(int fps) {
        gameLoop = new Timer(1000/fps, e -> {
            player.update();
            updateLevel();
            repaint();
        });
        gameLoop.start();
    }

    protected void completeLevel() {
        levelComplete = true;
        gameLoop.stop();
        showChemistryExplanation();
        
        Timer transitionTimer = new Timer(2000, e -> {
            window.switchToLevel(getNextLevel());
        });
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }

    // Abstract methods
    protected abstract void initializeLevel();
    public abstract void resetLevel();
    protected abstract void updateLevel();
    protected abstract void drawBackground(Graphics2D g2d);
    protected abstract void drawUI(Graphics2D g2d);
    protected abstract String getChemistryExplanation();
    protected abstract String[] getMCQOptions();
    protected abstract String getNextLevel();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        drawBackground(g2d);
        player.draw(g2d);
        drawUI(g2d);
        
        // Safely draw AP Chem reference
        try {
            String levelNum = getNextLevel().replaceAll("\\D+", "");
            if (!levelNum.isEmpty()) {
                int index = Integer.parseInt(levelNum) - 1;
                if (index >= 0 && index < APChemObjectives.LEVEL_OBJECTIVES.length) {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.ITALIC, 12));
                    g2d.drawString(APChemObjectives.LEVEL_OBJECTIVES[index], 
                                20, getHeight()-20);
                }
            }
        } catch (NumberFormatException e) {
            // Skip drawing if level number can't be determined
        }
    }
}