import javax.swing.*;
public class FinanceTracker extends JFrame {
    private static final long serialVersionUID = 1L;

    public FinanceTracker() {
        setTitle("Finance Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components and layout here
        // For example, you can add a menu bar, panels, buttons, etc.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinanceTracker tracker = new FinanceTracker();
            tracker.setVisible(true);
        });
    }

}
