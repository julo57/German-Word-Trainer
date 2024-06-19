package src.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import src.DatabaseOperations;

public class GUIMainMenu extends JFrame {
    private Controlers controlers;
    private DatabaseOperations databaseOperations = new DatabaseOperations();
    public GUIMainMenu(Controlers controlers) {
        // Set Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to the default look and feel
        }

        this.controlers = controlers;
        controlers.setMainMenu(this);

        // Setup frame
        setTitle("Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add welcome label
        JLabel welcomeLabel = new JLabel("Witaj w słowniku!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeLabel, gbc);

        // Add spacing after the welcome label
        gbc.gridy++;
        panel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        // Add buttons
        JButton addButton = new JButton("Dodaj słówka do słownika");
        addButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        JButton viewButton = new JButton("Zobacz słówka w słowniku");
        viewButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy++;
        panel.add(viewButton, gbc);

        JButton learButton = new JButton("Nauka słówek");
        learButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy++;
        panel.add(learButton, gbc);

        

        // Add action listeners
        addButton.addActionListener(e -> controlers.openAddWordMenu());
        viewButton.addActionListener(e -> controlers.openViewWordsMenu());
        learButton.addActionListener(e -> controlers.openLearnMenu());
       
        // Add panel to frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}
