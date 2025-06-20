import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EndScreen extends JPanel {
    private final GameWindow window;
    private final JButton reviewButton;
    
    // Store chemistry content without needing level instances
    private final Map<String, String> chemistryContent = new HashMap<>();

    public EndScreen(GameWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        // Pre-load all chemistry content
        loadChemistryContent();
        
        // Title
        JLabel title = new JLabel("Molecular Odyssey Complete!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        // Message
        JTextArea message = new JTextArea(
            "Congratulations! You've explored all phase changes and chemistry concepts.\n\n" +
            "You can now review the chemistry concepts!"
        );
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(new Font("Arial", Font.PLAIN, 18));
        message.setAlignmentX(CENTER_ALIGNMENT);
        message.setBackground(new Color(240, 248, 255));
        message.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        add(message, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        reviewButton = new JButton("Review Chemistry Concepts");
        reviewButton.addActionListener(e -> showReviewOptions());
        buttonPanel.add(reviewButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

        private void loadChemistryContent() {
        chemistryContent.put("Tutorial", 
            "ENTROPY (Unit 9.1)\n" +
            "---------------------------------------\n" +
            "Observations:\n" +
            "• Gaining energy increases entropy\n" +
            "• Higher entropy/energy leads to phase changes (liquid → gas)\n" +
            "• Gas molecules move more freely than liquids\n\n" +
            "AP Chemistry:\n" +
            "• Entropy measures molecular disorder and adding energy increases entropy\n" +
            "• Spontaneous processes favor increased entropy\n" +
            "• Phase changes occur at specific entropy thresholds\n\n" +
            "Connections:\n" +
            "• Heating ice → water → steam increases entropy\n" +
            "• Evaporation occurs spontaneously at room temperature");

        chemistryContent.put("Aurora",
            "Photoelectric Effect and Heat Transfer (Units 6.3, 1.4)\n" +
            "--------------------------------\n" +
            "Observations:\n" +
            "• Yellow particles = solar energy packets\n" +
            "• Energy absorption → immediate light emission\n" +
            "• Different colors = specific energy amounts\n\n" +
            "AP Chemistry:\n" +
            "• Light behaves as particle-like photons\n" +
            "• Each photon carries quantized energy (E=hv)\n" +
            "• Electrons absorb/release exact energy amounts\n\n" +
            "• Energy moves from solar particles → molecule\n" +
            "• Excess energy releases as colored light\n" +
            "• Demonstrates energy conservation\n\n" +
            "Connection:\n" +
            "• Actual auroras work identically\n" +
            "• Neon signs use same electron jumps");

        chemistryContent.put("Waterspout",
            "IMFs, Heat Transfer, and Entropy (Units 3.12, 6.03, 9.01)\n" +
            "-------------------------------------\n" +
            "Key Observations:\n" +
            "• Red zones = heat sources adding energy\n" +
            "• Rising mist = entropy increasing\n\n" +
            "AP Chemistry Principles:\n" +
            "• Intermolecular Forces (Unit 3.12)\n" +
            "• Heat breaks hydrogen bonds between water molecules\n" +
            "• Explains waterspout's upward motion (IMFs → vapor pressure)\n\n" +
            "Heat Transfer (Unit 6.03):\n" +
            "• Energy flows into the system (endothermic)\n" +
            "• Phase change requires specific energy input (ΔH_vap)\n\n" +
            "Entropy (Unit 9.01):\n" +
            "• Liquid → gas = ΔS > 0 (disorder increases)\n" +
            "• Spontaneous because TΔS > ΔH at high temps\n\n" +
            "Connection:\n" +
            "• Why oceans evaporate under sunlight\n" +
            "• How steam engines convert heat → motion\n" +
            "• Why sweat cools you (heat absorbed → evaporation)");

        chemistryContent.put("IceCrystal",
            "IMFs and Entropy (Units 3.11 and 9.01)\n" +
            "----------------------------------------------\n" +
            "Key Observations:\n" +
            "• Blue zones = hydrogen bond sites\n" +
            "• Hexagonal shapes = real ice lattice structure\n" +
            "• Entropy decreased as crystals formed\n\n" +
            "AP Chemistry:\n" +
            "• Each H₂O forms 4 bonds (tetrahedral shape)\n" +
            "• Stronger than dipole but weaker than covalent bonds\n\n" +
            "• Liquid → solid = ΔS < 0 (more ordered)\n" +
            "• Still occurs because ΔH is negative (exothermic)\n\n" +
            "Connection:\n" +
            "• Why ice floats\n" +
            "• Snowflake symmetry from H-bond angles");

        chemistryContent.put("Cloud",
            "IMFs and Exo/Endothermic Reactions (3.12 and 6.01)\n" +
            "----------------------------------------\n" +
            "Observations:\n" +
            "• Blue zones are cold air regions\n" +
            "• Rain droplets formed happens when entropy is decreasing\n\n" +
            "AP Chemistry:\n" +
            "• Water molecules reform hydrogen bonds\n" +
            "• Nucleation requires dust/ions (like in-game cold zones)\n\n" +
            "• Gas → liquid = ΔS < 0 (more ordered)\n" +
            "• Exothermic process (releases heat)\n\n" +
            "Connection:\n" +
            "• How real clouds form at dew point\n" +
            "• Why mornings are foggy (Overnight cooling)");
    }

    private void showReviewOptions() {
        JDialog reviewDialog = new JDialog(window, "Chemistry Review", true);
        reviewDialog.setSize(700, 500);
        reviewDialog.setLocationRelativeTo(window);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add tabs using our pre-loaded content
        chemistryContent.forEach((level, content) -> {
            tabbedPane.addTab(level, createReviewPanel(content));
        });

        reviewDialog.add(tabbedPane);
        reviewDialog.setVisible(true);
    }

    private JScrollPane createReviewPanel(String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        return new JScrollPane(textArea);
    }
}