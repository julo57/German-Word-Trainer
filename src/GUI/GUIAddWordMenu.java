package src.GUI;

import javax.swing.*;
import java.awt.*;

public class GUIAddWordMenu extends JFrame {
    private Controlers controlers;

    public GUIAddWordMenu(Controlers controlers) {
        this.controlers = controlers;

        // Setup frame
        setTitle("Add Word Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add components
        JLabel addWordLabel = new JLabel("Add Word");
        addWordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        addWordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addWordLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField wordField = new JTextField(20);
        panel.add(wordField);

        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        panel.add(backButton);

        // Add action listeners
        backButton.addActionListener(e -> controlers.backToMainMenu());

        // Add panel to frame
        add(panel);
    }
}
