import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private static final int SIZE = 320, DOT_SIZE = 16, ALL_DOTS = 400;
    private static final int INITIAL_DOTS = 3;
    private static final int TIMER_DELAY = 250;

    private Image dot, apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS], y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left, right = true, up, down, inGame = true;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        setKeyAdapter();
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE, SIZE));
        initializeTimer();
    }

    private void initGame() {
        dots = INITIAL_DOTS;
        initializeSnakeCoordinates();
        createApple();
    }

    private void initializeSnakeCoordinates() {
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    private void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();
        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();
    }

    private void initializeTimer() {
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        g.drawImage(apple, appleX, appleY, this);
        for (int i = 0; i < dots; i++) {
            g.drawImage(dot, x[i], y[i], this);
        }
    }

    private void drawGameOver(Graphics g) {
        String str = "Game Over";
        g.setColor(Color.white);
        g.drawString(str, SIZE / 2 - 50, SIZE / 2);
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        updateSnakeCoordinates();
    }

    private void updateSnakeCoordinates() {
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    private void setKeyAdapter() {
        addKeyListener(new FieldKeyListener());
    }

    private class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            updateDirection(key);
        }

        private void updateDirection(int key) {
            left = key == KeyEvent.VK_LEFT && !right;
            right = key == KeyEvent.VK_RIGHT && !left;
            up = key == KeyEvent.VK_UP && !down;
            down = key == KeyEvent.VK_DOWN && !up;
        }
    }
}

