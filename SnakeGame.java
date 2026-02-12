import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame implements ActionListener {
    private GamePanel gamePanel;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        gamePanel = new GamePanel();
        add(gamePanel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gamePanel.update();
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}