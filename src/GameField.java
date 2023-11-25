import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    // Константи для розмірів поля, розміру точки та кількості точок
    private static final int SIZE = 320, DOT_SIZE = 16, ALL_DOTS = 400;

    // Початкова кількість точок гравця, затребувані для ініціалізації гри
    private static final int INITIAL_DOTS = 3;

    // Затребуване значення затримки таймера (в мілісекундах)
    private static final int TIMER_DELAY = 250;

    // Зображення для точок та яблук
    private Image dot, apple;

    // Координати яблука
    private int appleX, appleY;

    // Масиви координат точок гравця
    private int[] x = new int[ALL_DOTS], y = new int[ALL_DOTS];

    // Кількість точок гравця та таймер для оновлення гри
    private int dots;
    private Timer timer;

    // Напрямки руху гравця та статус гри
    private boolean left, right = true, up, down, inGame = true;

    // Конструктор класу
    public GameField() {
        // Встановлення чорного кольору фону
        setBackground(Color.black);

        // Завантаження зображень
        loadImages();

        // Ініціалізація гри
        initGame();

        // Додавання слухача клавіш
        setKeyAdapter();

        // Налаштування фокусу на поле гри
        setFocusable(true);

        // Встановлення розміру поля гри
        setPreferredSize(new Dimension(SIZE, SIZE));

        // Ініціалізація таймера для автоматичного оновлення гри
        initializeTimer();
    }

    // Ініціалізація гри
    private void initGame() {
        dots = INITIAL_DOTS;
        initializeSnakeCoordinates();
        createApple();
    }

    // Ініціалізація координат точок гравця
    private void initializeSnakeCoordinates() {
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
    }

    // Створення нового яблука
    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    // Завантаження зображень
    private void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();
        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();
    }

    // Ініціалізація таймера
    private void initializeTimer() {
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    // Малювання компоненту
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    // Малювання елементів гри
    private void drawGame(Graphics g) {
        g.drawImage(apple, appleX, appleY, this);
        for (int i = 0; i < dots; i++) {
            g.drawImage(dot, x[i], y[i], this);
        }
    }

    // Малювання напису "Game Over"
    private void drawGameOver(Graphics g) {
        String str = "Game Over";
        g.setColor(Color.white);
        // Центрування напису по горизонталі
        g.drawString(str, SIZE / 2 - 50, SIZE / 2);
    }

    // Рух гравця
    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        updateSnakeCoordinates();
    }

    // Оновлення координат точок гравця в залежності від напрямку руху
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

    // Перевірка з'їденого яблука
    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    // Перевірка зіткнень
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

    // Обробка події таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    // Встановлення слухача клавіш
    private void setKeyAdapter() {
        addKeyListener(new FieldKeyListener());
    }

    // Клас для обробки клавіш
    private class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            updateDirection(key);
        }

        // Оновлення напрямку руху в залежності від натисканої клавіші
        private void updateDirection(int key) {
            left = key == KeyEvent.VK_LEFT && !right;
            right = key == KeyEvent.VK_RIGHT && !left;
            up = key == KeyEvent.VK_UP && !down;
            down = key == KeyEvent.VK_DOWN && !up;
        }
    }
}

