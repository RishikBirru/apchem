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
            "ENTROPY AND MOLECULAR MOTION (Unit 9.1)\n" +
            "---------------------------------------\n" +
            "Entropy (S) measures molecular disorder:\n" +
            "- More movement = higher entropy\n" +
            "- Phase changes affect entropy dramatically\n" +
            "- ΔS > 0 for evaporation (liquid → gas)\n\n" +
            "Key AP Chemistry Concepts:\n" +
            "- Spontaneous processes favor increased entropy\n" +
            "- Gases > Liquids > Solids for entropy\n" +
            "- ΔS° = ΣS°(products) - ΣS°(reactants)\n\n" +
            "Thermodynamics:\n" +
            "ΔG = ΔH - TΔS\n" +
            "ΔS_universe = ΔS_system + ΔS_surroundings > 0");
            
        chemistryContent.put("Aurora", 
            "AURORA CHEMISTRY (Units 6.3, 1.4)\n" +
            "--------------------------------\n" +
            "Solar particles excite electrons:\n" +
            "1. e⁻ absorb energy and jump to higher orbitals\n" +
            "2. When they fall back, emit specific wavelengths\n" +
            "3. Colors correspond to energy differences:\n" +
            "   - Green: 557.7 nm (Oxygen)\n" +
            "   - Violet: 427.8 nm (N₂⁺)\n" +
            "   - Red: 630.0 nm (Oxygen)\n\n" +
            "Equations:\n" +
            "E = hν = hc/λ\n" +
            "ΔE = E_final - E_initial");
            
        chemistryContent.put("Waterspout", 
            "WATERSHED CHEMISTRY (Units 3.12, 8.1)\n" +
            "------------------------------------\n" +
            "Evaporation involves:\n" +
            "1. Overcoming hydrogen bonds (IMFs)\n" +
            "2. Energy input (ΔH > 0)\n" +
            "3. Entropy increase (ΔS > 0)\n\n" +
            "Key Concepts:\n" +
            "- Hydrogen bonding: 20 kJ/mol\n" +
            "- Vapor pressure equilibrium\n" +
            "- Clausius-Clapeyron equation:\n" +
            "  ln(P) = -ΔH_vap/R(1/T) + C\n\n" +
            "Thermodynamics:\n" +
            "ΔG = ΔH - TΔS\n" +
            "Spontaneous when ΔG < 0");
            
        chemistryContent.put("IceCrystal", 
            "ICE CRYSTAL CHEMISTRY (AP Chem Units 3.12, 2.1)\n" +
            "-----------------------------------------------\n" +
            "Hydrogen Bonding in Water:\n" +
            "- Tetrahedral geometry (109.5° bond angles)\n" +
            "- Hexagonal crystal lattice structure\n" +
            "- Lower density than liquid water (due to open lattice)\n\n" +
            "Key AP Chemistry Concepts:\n" +
            "- Each H₂O molecule forms 4 hydrogen bonds\n" +
            "- Hydrogen bond strength: ~20 kJ/mol (vs covalent O-H bond: 463 kJ/mol)\n" +
            "- ΔH_fusion = +6.01 kJ/mol (endothermic)\n" +
            "- ΔS_fusion = +22.0 J/(mol·K)\n\n" +
            "Phase Diagram Implications:\n" +
            "- Negative slope in P-T diagram (unique to water)\n" +
            "- Hydrogen bonding explains water's:\n" +
            "  * High specific heat (4.184 J/g°C)\n" +
            "  * High heat of vaporization (40.7 kJ/mol)\n" +
            "  * Surface tension (72.8 mN/m at 20°C)");
            
        chemistryContent.put("Cloud", 
            "CLOUD FORMATION (AP Chem Units 8.1, 6.3)\n" +
            "----------------------------------------\n" +
            "Condensation Fundamentals:\n" +
            "- Requires temperature below dew point\n" +
            "- Nucleation sites (dust, ions) help formation\n" +
            "- Exothermic process (ΔH < 0)\n\n" +
            "Key AP Chemistry Concepts:\n" +
            "- Vapor pressure equilibrium\n" +
            "- Relative humidity = (P_water/P_sat) × 100%\n" +
            "- Raoult's Law: P_solution = X_solvent × P°_solvent\n\n" +
            "Thermodynamics:\n" +
            "ΔG = ΔH - TΔS\n" +
            "- Spontaneous when ΔG < 0\n" +
            "- For condensation:\n" +
            "  * ΔH is negative (exothermic)\n" +
            "  * ΔS is negative (more ordered)\n" +
            "  * Favored at low temperatures");
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