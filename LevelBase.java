import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class LevelBase extends JPanel implements KeyListener {
    protected Player player;
    protected GameWindow window;
    protected Timer timer;
    protected String objective;

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_E) {
            interact();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e.getKeyCode());
    }

    // Modify the timer to continuously update position
    public LevelBase(Player player, GameWindow window) {
        this.player = player;
        this.window = window;
        setFocusable(true);
        addKeyListener(this);
        setBackground(new Color(173, 216, 230));
        
        timer = new Timer(16, e -> {
            player.updatePosition(); // Continuous movement update
            repaint();
            checkObjective();
        });
        timer.start();
    }

    protected void showChemistryPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Chemistry Connection", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(objective, 300, 30);
    }

    @Override public void keyTyped(KeyEvent e) {}

    public abstract void interact();
    public abstract void checkObjective();
}