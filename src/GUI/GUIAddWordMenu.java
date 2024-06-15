package src.GUI;

import src.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class GUIAddWordMenu extends JFrame {
    private Controlers controlers;
    private JComboBox<String> sectionComboBox;
    private JTextField newSectionField;
    private JTextField[] wordFields;
    private JComboBox<String>[] articleComboBoxes;
    private JTextField[] translationFields;

    private static final int NUM_WORDS = 1; // Number of words to add at once

    public GUIAddWordMenu(Controlers controlers) {
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

        // Setup frame
        setTitle("Add Word Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components for section input
        JLabel chooseSectionLabel = new JLabel("Wybierz lub dodaj sekcję:");
        chooseSectionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(chooseSectionLabel, gbc);

        sectionComboBox = new JComboBox<>();
        populateSectionComboBox();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(sectionComboBox, gbc);

        newSectionField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(newSectionField, gbc);

        JButton addSectionButton = new JButton("Dodaj sekcje");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(addSectionButton, gbc);

        // Add components for word inputs
        JLabel addWordsLabel = new JLabel("Dodaj słowa:");
        addWordsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(addWordsLabel, gbc);

        wordFields = new JTextField[NUM_WORDS];
        articleComboBoxes = new JComboBox[NUM_WORDS];
        translationFields = new JTextField[NUM_WORDS];

        for (int i = 0; i < NUM_WORDS; i++) {
            JPanel wordPanel = new JPanel(new GridBagLayout());
            wordPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            GridBagConstraints wgbc = new GridBagConstraints();
            wgbc.insets = new Insets(5, 5, 5, 5);
            wgbc.fill = GridBagConstraints.HORIZONTAL;

            wordFields[i] = new JTextField(10);
            articleComboBoxes[i] = new JComboBox<>(new String[]{"","Der", "Die", "Das"});
            translationFields[i] = new JTextField(10);

            wgbc.gridx = 0;
            wgbc.gridy = 0;
            wordPanel.add(new JLabel("Słowo " + (i + 1) + ": "), wgbc);

            wgbc.gridx = 1;
            wordPanel.add(articleComboBoxes[i], wgbc);

            wgbc.gridx = 2;
            wordPanel.add(wordFields[i], wgbc);

            wgbc.gridx = 3;
            wordPanel.add(new JLabel("Tłumaczenie: "), wgbc);

            wgbc.gridx = 4;
            wordPanel.add(translationFields[i], wgbc);

            gbc.gridx = 0;
            gbc.gridy = 4 + i;
            gbc.gridwidth = 2;
            panel.add(wordPanel, gbc);
        }

        JButton saveButton = new JButton("Zapisz");
        gbc.gridx = 0;
        gbc.gridy = 4 + NUM_WORDS;
        gbc.gridwidth = 1;
        panel.add(saveButton, gbc);

        JButton backButton = new JButton("Wróć");
        gbc.gridx = 1;
        gbc.gridy = 4 + NUM_WORDS;
        panel.add(backButton, gbc);

        // Add action listeners
        addSectionButton.addActionListener(e -> {
            String newSection = newSectionField.getText();
            if (!newSection.isEmpty()) {
                controlers.addSection(newSection);
                sectionComboBox.addItem(newSection);
                newSectionField.setText("");
            }
        });
        saveButton.addActionListener(e -> saveWords());
        backButton.addActionListener(e -> controlers.backToMainMenu());

        // Add panel to frame
        add(panel);
    }

    private void populateSectionComboBox() {
        List<String> sectionNames = DatabaseManager.fetchTableNames(); // Correct method name
        for (String sectionName : sectionNames) {
            sectionComboBox.addItem(sectionName);
        }
    }

    private void saveWords() {
        String sectionName = (String) sectionComboBox.getSelectedItem();
        for (int i = 0; i < NUM_WORDS; i++) {
            String article = (String) articleComboBoxes[i].getSelectedItem();
            String word = wordFields[i].getText();
            String translation = translationFields[i].getText();

            if (!word.isEmpty() && !translation.isEmpty()) {
                controlers.insertWord(sectionName, article, word, translation);
            }
        }
    }
}
