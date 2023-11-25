import javax.swing.*;

// Клас вікна гри
public class MainWindow extends JFrame {

    // Конструктор класу
    public MainWindow() {
        initializeUI(); // Ініціалізація інтерфейсу користувача
    }

    // Метод для налаштування інтерфейсу користувача
    private void initializeUI() {
        setTitle("Змейка"); // Встановлення заголовку вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Встановлення операції при закритті вікна
        setSize(320, 345); // Встановлення розміру вікна
        setLocation(400, 400); // Встановлення розташування вікна
        add(new GameField()); // Додавання ігрового поля до вікна
        setVisible(true); // Робить вікно видимим
    }

    // Точка входу в програму
    public static void main(String[] args) {
        // Запуск GUI в потоці обробки подій
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}


