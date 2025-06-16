import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow game = new GameWindow();
            game.setVisible(true);
        });
    }
}