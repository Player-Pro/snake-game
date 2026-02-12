import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 20;
    private ArrayList<Point> snake;
    private Point food;
    private int direction;
    private int nextDirection;
    private boolean gameOver;
    private int score;
    private Random random;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        random = new Random();
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(10, 10));
        snake.add(new Point(9, 10));
        snake.add(new Point(8, 10));
        direction = 0;
        nextDirection = 0;
        gameOver = false;
        score = 0;
        spawnFood();
    }

    private void spawnFood() {
        int maxX = getWidth() / TILE_SIZE;
        int maxY = getHeight() / TILE_SIZE;
        while (true) {
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            Point newFood = new Point(x, y);
            if (!snake.contains(newFood)) {
                food = newFood;
                break;
            }
        }
    }

    public void update() {
        if (gameOver) {
            return;
        }
        direction = nextDirection;
        Point head = snake.get(0);
        Point newHead = new Point(head);
        switch (direction) {
            case 0:
                newHead.x++;
                break;
            case 1:
                newHead.y++;
                break;
            case 2:
                newHead.x--;
                break;
            case 3:
                newHead.y--;
                break;
        }
        int maxX = getWidth() / TILE_SIZE;
        int maxY = getHeight() / TILE_SIZE;
        if (newHead.x < 0 || newHead.x >= maxX || newHead.y < 0 || newHead.y >= maxY) {
            gameOver = true;
            return;
        }
        if (snake.contains(newHead)) {
            gameOver = true;
            return;
        }
        snake.add(0, newHead);
        if (newHead.equals(food)) {
            score += 10;
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
        repaint();
    }

    private void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_RIGHT:
                if (direction != 2) nextDirection = 0;
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 3) nextDirection = 1;
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 0) nextDirection = 2;
                break;
            case KeyEvent.VK_UP:
                if (direction != 1) nextDirection = 3;
                break;
            case KeyEvent.VK_SPACE:
                if (gameOver) {
                    initGame();
                }
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < snake.size(); i++) {
            Point segment = snake.get(i);
            if (i == 0) {
                g2d.setColor(new Color(144, 238, 144));
            } else {
                g2d.setColor(Color.GREEN);
            }
            g2d.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            g2d.setColor(new Color(0, 100, 0));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        g2d.setColor(Color.RED);
        g2d.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 25);
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            String gameOverText = "GAME OVER";
            int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(gameOverText, x, y);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartText = "Press SPACE to Restart";
            x = (getWidth() - fm.stringWidth(restartText)) / 2;
            y += 50;
            g2d.drawString(restartText, x, y);
        }
    }
}