import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(CardLayout cardLayout) {
        super(cardLayout);
        this.setLayout(null);
        addButtons();
        setFocusable(true);
        requestFocusInWindow();
        createEventHandlers();
    }


    private void addButtons() {
        JButton startGame = new JButton("Start Game");
        startGame.setFont(new Font("Arial", Font.PLAIN, 40));
        startGame.setForeground(new Color(177, 0, 0));
        startGame.setBounds(550, 400, 340, 45);
        startGame.setOpaque(false);
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);
        startGame.addActionListener(e -> MainFrame.swapPanel("Game"));
        this.add(startGame);
    }

    private void createEventHandlers() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                int x = me.getX();
                int y = me.getY();
                System.out.println("WelcomePanel: Mouse Pressed at " + x + ", " + y);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
    }
}
