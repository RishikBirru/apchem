import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class MainFrame extends JFrame {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    private static CardLayout cardLayout = new CardLayout();
    private static JPanel cards = new JPanel(cardLayout);
    private JPanel welcomePanel;
    private JPanel gamePanel;

    public static void startGUI() throws InterruptedException {
        MainFrame theGUI = new MainFrame();

        // Starts the UI Thread and creates the the UI in that thread.
        // Uses a Lambda Expression to call the createFrame method.
        // Use theGUI as the semaphore object
        SwingUtilities.invokeLater(() -> theGUI.createFrame(theGUI));

        synchronized (theGUI) {
            theGUI.wait();
        }
    }

    /**
     * Create the main JFrame and all animation JPanels.
     * 
     * @param semaphore The object to notify when complete
     */
    public void createFrame(Object semaphore) {
        
        // Sets the title found in the Title Bar of the JFrame
        this.setTitle("RukMAN");
        // Sets the size of the main Window
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        SwingUtilities.invokeLater(() -> addPanels());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {swapPanel("Welcome"); this.setVisible(true);});


        // tell the main thread that we are done creating our stuff
        synchronized (semaphore) {
            semaphore.notify();
        }

        /*try {  
            Thread.sleep(4000); 
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
        
        swapPanel("game");*/
    }

    private void addPanels() {
        welcomePanel = new WelcomePanel(cardLayout); 
        gamePanel = new GamePanel(cardLayout);


        cards.add(welcomePanel, "Welcome");
        cards.add(gamePanel, "Game");
        this.add(cards);
    }

    public static void swapPanel(String panelName) {
        System.out.println("Swapped to " + panelName + " Panel");
        cardLayout.show(cards, panelName);

        //Credit: ChatGPT 4o
        // Essentially what this does is takes all the components of all the panels
        // and requestFocusInWindow for all Components visible allowing us to be able to 
        // move without additional accent

        Component current = Arrays.stream(cards.getComponents())
            .filter(Component::isVisible)
            .findFirst()
            .orElse(null);
        if (current != null) {
            current.requestFocusInWindow();
        }
    }
}
