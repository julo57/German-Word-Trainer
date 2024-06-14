package src.GUI;

import javax.swing.*;
import java.awt.*;

public class GUIViewWordsMenu extends JFrame {
    private Controlers controlers;

    public GUIViewWordsMenu(Controlers controlers) {
        this.controlers = controlers;

        // Setup frame
        setTitle("View Words Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add components
        JLabel viewWordsLabel = new JLabel("View Words");
        viewWordsLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        viewWordsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(viewWordsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add a list or table to display words (this is just a placeholder)
        JTextArea wordsArea = new JTextArea(20, 50);
        wordsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(wordsArea);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back");
        panel.add(backButton);

        // Add action listeners
        backButton.addActionListener(e -> controlers.backToMainMenu());

        // Add panel to frame
        add(panel);
    }
}
