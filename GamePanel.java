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

    private static boolean movingLeft = false;
    private static boolean movingRight = false;
    private static boolean isIdle = true;
    private static boolean facingRight = true;
    private static boolean wasFacingRight = true;
    private static int leftX = 110;
    private static int y = 600;
    private static int crawlFrame = 0;

    Image background;
    

    public GamePanel(CardLayout cardLayout) {
        super(cardLayout);
        setFocusable(true);
        requestFocusInWindow();

        try {
            background = ImageIO.read(new File("assets/lake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Timer timer = new Timer(16, e -> {
            if (movingLeft) {
                if (wasFacingRight) {
                    leftX -= 20; // shift left once when switching direction
                }
                leftX -= 5;
                facingRight = false;
                isIdle = false;
            } else if (movingRight) {
                if (!wasFacingRight) {
                    leftX += 20; // shift right once when switching direction
                }
                leftX += 5;
                facingRight = true;
                isIdle = false;
                if (leftX > 905) leftX = 905;
            } else {
                isIdle = true;
            }
            wasFacingRight = facingRight; // update last facing direction
            repaint();            
        });
        timer.start();

        createEventHandlers();
    }



    private void createEventHandlers() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) movingLeft = true; 
                if (e.getKeyCode() == KeyEvent.VK_D) movingRight = true; 

            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) movingLeft = false;
                if (e.getKeyCode() == KeyEvent.VK_D) movingRight = false;
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

        if (background != null) {
            g.drawImage(background, 0, 0, 1080, 720, this);
        }
    }
}
