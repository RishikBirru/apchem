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
    
    // Create main panel with tabs
    JTabbedPane tabbedPane = new JTabbedPane();
    
    // Concepts tab
    JTextArea conceptsArea = new JTextArea(getChemistryExplanation());
    styleTextArea(conceptsArea);
    tabbedPane.addTab("Chemistry Concepts", new JScrollPane(conceptsArea));
    
    // Equations tab
    JTextArea equationsArea = new JTextArea(getRelevantEquations());
    styleTextArea(equationsArea);
    tabbedPane.addTab("Key Equations", new JScrollPane(equationsArea));
    
    // MCQ tab
    JPanel mcqPanel = createMCQPanel();
    tabbedPane.addTab("Quick Quiz", mcqPanel);
    
    JOptionPane.showMessageDialog(this, tabbedPane, 
                               "AP Chemistry Review", 
                               JOptionPane.PLAIN_MESSAGE);
    
    player.setInputsEnabled(true);
}

private JPanel createMCQPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(new Dimension(500, 300));
    
    JLabel questionLabel = new JLabel("Select the correct statement:");
    panel.add(questionLabel, BorderLayout.NORTH);
    
    JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
    ButtonGroup group = new ButtonGroup();
    String[] options = getMCQOptions();
    boolean[] answers = getMCQAnswers();
    
    for (int i = 0; i < options.length; i++) {
        JRadioButton option = new JRadioButton(options[i]);
        option.setActionCommand(String.valueOf(i));
        group.add(option);
        optionsPanel.add(option);
    }
    
    JButton submitButton = new JButton("Submit");
    JLabel resultLabel = new JLabel(" ");
    resultLabel.setForeground(Color.RED);
    
    submitButton.addActionListener(e -> {
        String selected = group.getSelection() != null ? 
                         group.getSelection().getActionCommand() : "-1";
        int selectedIndex = Integer.parseInt(selected);
        
        if (selectedIndex == -1) {
            resultLabel.setText("Please select an answer!");
        } else {
            boolean isCorrect = answers[selectedIndex];
            resultLabel.setText(isCorrect ? 
                "✓ Correct! Well done!" : 
                "✗ Incorrect. Review the concepts.");
            submitButton.setEnabled(false);
        }
    });
    
    panel.add(optionsPanel, BorderLayout.CENTER);
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(submitButton, BorderLayout.WEST);
    bottomPanel.add(resultLabel, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);
    
    return panel;
}

// Add this new method to LevelBase
    protected abstract boolean[] getMCQAnswers();

    private void styleTextArea(JTextArea area) {
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setMargin(new Insets(10, 10, 10, 10));
    }

    private void styleExplanationText(JTextArea textArea) {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
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
        if (levelComplete) return;
        
        levelComplete = true;
        gameLoop.stop();
        
        // Disable player movement during transition
        player.setMovementEnabled(false);
        player.setInputsEnabled(false);
        
        // Show completion sequence
        SwingUtilities.invokeLater(() -> {
            // 1. Show completion message
            JOptionPane.showMessageDialog(this,
                "Level Complete!\n\n" + getLevelCompletionMessage(),
                "Phase Change Achieved",
                JOptionPane.INFORMATION_MESSAGE);
            
            // 2. Show chemistry explanation
            showChemistryExplanation();
            
            // 3. Transition with clean state
            new Timer(500, e -> {
                window.switchToLevel(getNextLevel());
                ((Timer)e.getSource()).stop();
            }).start();
        });
    }

    protected String getLevelCompletionMessage() {
        return switch(this.getClass().getSimpleName()) {
            case "TutorialLevel" -> "You've mastered molecular movement!";
            case "AuroraLevel" -> "Aurora phenomenon achieved!";
            case "WaterspoutLevel" -> "Evaporation process completed!";
            case "IceCrystalLevel" -> "Crystal formation successful!";
            case "CloudLevel" -> "Condensation achieved!";
            default -> "Phase transition complete!";
        };
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
    protected abstract String getPhenomenonDescription();
    protected abstract String getRelevantEquations();


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