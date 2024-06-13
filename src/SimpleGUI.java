package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class SimpleGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField wordField;
    private JTextField translationField;
    private JComboBox<String> articleComboBox;
    private JButton addButton;
    private JLabel wordLabel;
    private JLabel translationLabel;
    private JLabel articleLabel;
    private JButton choiseVerb;
    private JButton choiseNoun;
    private JButton choiseAdjective;
    private DefaultTableModel currentTableModel;
    private JTabbedPane tabbedPane;
    private JTextField sectionNameField;
    private JButton createSectionButton;
    private Map<String, DefaultTableModel> sections;

    public SimpleGUI() {
        sections = new HashMap<>();

        // Tworzenie ramki
        frame = new JFrame("Prosty Interfejs Graficzny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1000);
        
        // Tworzenie głównego panelu
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Panel dla tworzenia nowych działów
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new FlowLayout());
        sectionPanel.add(new JLabel("Nazwa działu:"));
        sectionNameField = new JTextField(15);
        sectionPanel.add(sectionNameField);
        createSectionButton = new JButton("Utwórz dział");
        createSectionButton.addActionListener(e -> createSection());
        sectionPanel.add(createSectionButton);
        panel.add(sectionPanel);

        // Tworzenie tabbedPane do zarządzania zakładkami działów
        tabbedPane = new JTabbedPane();
        panel.add(tabbedPane);

        // Dodanie przycisków wyboru
        JPanel choisePanel = new JPanel();
        choisePanel.setLayout(new FlowLayout());
        choisePanel.add(new JLabel("Wybierz, jakie słowo chcesz dodać:"));
        choiseVerb = new JButton("Czasownik");
        choiseNoun = new JButton("Rzeczownik");
        choiseAdjective = new JButton("Przymiotnik");
        choiseVerb.addActionListener(e -> showWordFields("verb"));
        choiseNoun.addActionListener(e -> showWordFields("noun"));
        choiseAdjective.addActionListener(e -> showWordFields("adjective"));
        choisePanel.add(choiseVerb);
        choisePanel.add(choiseNoun);
        choisePanel.add(choiseAdjective);
        panel.add(choisePanel);

        // Dodanie pól tekstowych i etykiet (początkowo ukryte)
        articleLabel = new JLabel("Rodzajnik:");
        String[] articles = {"der", "die", "das"};
        articleComboBox = new JComboBox<>(articles);
        articleLabel.setVisible(false);
        articleComboBox.setVisible(false);

        wordLabel = new JLabel("Słówko:");
        wordField = new JTextField(15);
        wordLabel.setVisible(false);
        wordField.setVisible(false);

        translationLabel = new JLabel("Tłumaczenie:");
        translationField = new JTextField(15);
        translationLabel.setVisible(false);
        translationField.setVisible(false);

        // Dodanie przycisku (początkowo ukryte)
        addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> addWord());
        addButton.setVisible(false);

        // Dodanie komponentów do panelu
        panel.add(articleLabel);
        panel.add(articleComboBox);
        panel.add(wordLabel);
        panel.add(wordField);
        panel.add(translationLabel);
        panel.add(translationField);
        panel.add(addButton);
        
        // Dodanie panelu do ramki
        frame.add(panel);
        
        // Ustawienie ramki jako widocznej
        frame.setVisible(true);
    }

    // Metoda do tworzenia nowego działu
    private void createSection() {
        String sectionName = sectionNameField.getText().trim();
        if (sectionName.isEmpty() || sections.containsKey(sectionName)) {
            JOptionPane.showMessageDialog(frame, "Nazwa działu jest pusta lub już istnieje");
            return;
        }
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Rodzajnik", "Słówko", "Tłumaczenie"}, 0);
        JTable table = new JTable(tableModel);
        sections.put(sectionName, tableModel);
        tabbedPane.addTab(sectionName, new JScrollPane(table));
        sectionNameField.setText("");

        // Tworzenie tabeli w bazie danych
        DatabaseManager.createSectionTable(sectionName);
    }

    // Metoda do pokazywania pól tekstowych i przycisku
    private void showWordFields(String type) {
        if (tabbedPane.getSelectedComponent() == null) {
            JOptionPane.showMessageDialog(frame, "Proszę najpierw utworzyć dział");
            return;
        }
        currentTableModel = sections.get(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));

        wordLabel.setVisible(true);
        wordField.setVisible(true);
        translationLabel.setVisible(true);
        translationField.setVisible(true);
        addButton.setVisible(true);

        articleLabel.setVisible(false);
        articleComboBox.setVisible(false);

        if (type.equals("noun")) {
            articleLabel.setVisible(true);
            articleComboBox.setVisible(true);
        }
    }

    // Metoda do dodawania słówek
    private void addWord() {
        if (currentTableModel == null) {
            JOptionPane.showMessageDialog(frame, "Proszę najpierw wybrać typ słowa");
            return;
        }

        String word = wordField.getText();
        String translation = translationField.getText();
        String article = "";

        if (articleLabel.isVisible()) {
            article = (String) articleComboBox.getSelectedItem();
            word = capitalizeFirstLetter(word);
        }

        if (word.isEmpty() || translation.isEmpty() || (articleLabel.isVisible() && article.isEmpty())) {
            JOptionPane.showMessageDialog(frame, "Wszystkie pola muszą być wypełnione");
            return;
        }

        // Sprawdzanie, czy słowo już istnieje w bazie danych
        String sectionName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        if (!DatabaseManager.insertWord(sectionName, article, word, translation)) {
            JOptionPane.showMessageDialog(frame, "Słówko już istnieje w bazie danych");
            return;
        }

        // Dodanie słówka do tabeli w GUI
        currentTableModel.addRow(new Object[]{article, word, translation});

        wordField.setText("");
        translationField.setText("");
    }

    // Metoda do zmiany pierwszej litery na wielką
    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static void main(String[] args) {
        new SimpleGUI();
    }
}
