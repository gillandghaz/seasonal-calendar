package org.example;

import org.example.ui.CalendarFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Tampilan default Swing tetap bisa digunakan.
            }

            new CalendarFrame().setVisible(true);
        });
    }
}
