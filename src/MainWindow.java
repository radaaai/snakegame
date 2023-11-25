import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Snake Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400, 400);

        add(new GameField());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}

