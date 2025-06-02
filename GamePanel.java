import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GamePanel extends JPanel {

    private static boolean left = false;
    private static boolean right = false;
    private static boolean up = false;
    private static boolean down = false;

    private static int x = 110;
    private static int y = 300;

    Image background;
    Image molecule;

    

    public GamePanel(CardLayout cardLayout) {
        super(cardLayout);
        setFocusable(true);
        requestFocusInWindow();

        try {
            background = ImageIO.read(new File("assets/lake.png"));
            molecule = ImageIO.read(new File("assets/molecule.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Timer timer = new Timer(16, e -> {
            if (left) {
                x -= 5;              
            } else if (right) {
                x += 5;
            } else if (up) {
                y -= 5;
            } else if (down) {
                y += 5;
            }
            repaint();            
        });
        timer.start();

        createEventHandlers();
    }



    private void createEventHandlers() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) up = true; 
                if (e.getKeyCode() == KeyEvent.VK_A) left = true; 
                if (e.getKeyCode() == KeyEvent.VK_S) down = true; 
                if (e.getKeyCode() == KeyEvent.VK_D) right = true; 
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) up = false; 
                if (e.getKeyCode() == KeyEvent.VK_A) left = false; 
                if (e.getKeyCode() == KeyEvent.VK_S) down = false; 
                if (e.getKeyCode() == KeyEvent.VK_D) right = false; 
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                requestFocusInWindow();
                System.out.println(me.getX() + " " + me.getY());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        Graphics2D g2 = (Graphics2D) g;

        if (background != null) {
            g.drawImage(background, 0, 0, 1080, 720, this);
        }

        if (up) {
            g.drawImage(molecule, x, y, 100, 100, null);
        } else if (down) {
            g.drawImage(molecule, x, y + 5, 100, 100, null);
        } else if (left) {
            g.drawImage(molecule, x - 5, y, 100, 100, null);
        } else if (right) {
            g.drawImage(molecule, x + 5, y, 100, 100, null);
        } else {
            g.drawImage(molecule, x, y, 100, 100, null);
        }

    }
}
