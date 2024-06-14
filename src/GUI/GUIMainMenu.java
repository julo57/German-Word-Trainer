package src.GUI;

import javax.swing.*;
import java.awt.*;

public class GUIMainMenu extends JFrame {
    private Controlers controlers;

    // Constructor that takes Controlers as an argument
    public GUIMainMenu(Controlers controlers) {
        this.controlers = controlers;
        controlers.setMainMenu(this);

        // Setup frame
        setTitle("Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add components
        JLabel welcomeLabel = new JLabel("Main Menu");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton addButton = new JButton("Add Word");
        panel.add(addButton);

        JButton viewButton = new JButton("View Words");
        panel.add(viewButton);

        // Add action listeners
        addButton.addActionListener(e -> controlers.openAddWordMenu());
        viewButton.addActionListener(e -> controlers.openViewWordsMenu());

        // Add panel to frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}
