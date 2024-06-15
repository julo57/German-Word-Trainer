package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUILearnMenu extends JFrame {
    private Controlers controlers;
    private JLabel wordLabel;
    private JLabel wordCountLabel;
    private JPanel cardPanel;
    private List<String[]> words;
    private int currentIndex;
    private boolean showingTranslation;

    public GUILearnMenu(Controlers controlers) {
        this.controlers = controlers;

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

        // Setup frame
        setTitle("Learn Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add word count label
        wordCountLabel = new JLabel("", SwingConstants.RIGHT);
        wordCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(wordCountLabel, BorderLayout.NORTH);

        // Create card panel
        cardPanel = new JPanel(new CardLayout());
        panel.add(cardPanel, BorderLayout.CENTER);

        // Add word label
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        wordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                flipCard();
            }
        });
        cardPanel.add(wordLabel, "wordCard");

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        buttonsPanel.add(backButton);
        buttonsPanel.add(nextButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        backButton.addActionListener(e -> controlers.backToMainMenu());
        nextButton.addActionListener(e -> showNextWord());

        // Add panel to frame
        add(panel);

        // Fetch words
        fetchWordsForFlashcards();

        // Show first word
        showNextWord();
    }

    private void fetchWordsForFlashcards() {
        List<String> sectionNames = selectSections();
        words = controlers.getWordsForFlashcards(sectionNames);
        System.out.println("Fetched words: " + words.size());
        for (String[] word : words) {
            System.out.println("Word: " + word[0] + ", Translation: " + word[1] + ", Array Length: " + word.length);
        }
        currentIndex = -1;
        showingTranslation = false;
        updateWordCountLabel();
    }

    private List<String> selectSections() {
        List<String> sectionNames = controlers.getSections();
        JList<String> list = new JList<>(sectionNames.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 150));

        int option = JOptionPane.showConfirmDialog(this, scrollPane, "Select sections to learn from:", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return list.getSelectedValuesList();
        } else {
            return new ArrayList<>();
        }
    }

    private void showNextWord() {
        if (words.isEmpty()) {
            wordLabel.setText("No words available.");
            return;
        }

        currentIndex = (currentIndex + 1) % words.size();
        showingTranslation = false;
        updateWordLabel();
        updateWordCountLabel();
    }

    private void updateWordLabel() {
        String germanWord = words.get(currentIndex)[0];
        String article = words.get(currentIndex).length > 2 ? words.get(currentIndex)[2] : "";
        if (!article.isEmpty()) {
            wordLabel.setText(article + " " + germanWord);
        } else {
            wordLabel.setText(germanWord);
        }
    }

    private void updateWordCountLabel() {
        wordCountLabel.setText("Word " + (currentIndex + 1) + " of " + words.size());
    }

    private void flipCard() {
        if (words.isEmpty()) {
            return;
        }

        showingTranslation = !showingTranslation;
        if (showingTranslation) {
            wordLabel.setText(words.get(currentIndex)[1]); // Show Polish translation
        } else {
            updateWordLabel(); // Show German word with article if it exists
        }
        animateCardFlip();
    }

    private void animateCardFlip() {
        // Implement a simple flip animation
        Timer timer = new Timer(10, new ActionListener() {
            private int angle = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 10;
                if (angle >= 180) {
                    ((Timer) e.getSource()).stop();
                    angle = 0;
                }
                cardPanel.repaint();
            }
        });
        timer.start();
    }
}
