package Internship;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseManager.connect();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() -> {
                GradeTrackerGUI app = new GradeTrackerGUI();
                app.setVisible(true);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Database connection failed!\n" + e.getMessage(),
                    "Startup Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}