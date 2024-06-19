package src.GUI;

import src.DatabaseManager;

import java.util.List;

public class Controlers {
    private GUIMainMenu mainMenu;
    private GUIAddWordMenu addWordMenu;
    private GUIViewWordsMenu viewWordsMenu;
    private GUILearnMenu learnMenu;

    public void setMainMenu(GUIMainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void openAddWordMenu() {
        if (addWordMenu == null) {
            addWordMenu = new GUIAddWordMenu(this);
        }
        addWordMenu.setVisible(true);
        mainMenu.setVisible(false);
    }

    public void openViewWordsMenu() {
        if (viewWordsMenu == null) {
            viewWordsMenu = new GUIViewWordsMenu(this);
        }
        viewWordsMenu.setVisible(true);
        mainMenu.setVisible(false);
    }

    public void openLearnMenu() {
        if (learnMenu == null) {
            learnMenu = new GUILearnMenu(this);
        }
        learnMenu.setVisible(true);
        mainMenu.setVisible(false);
    }

    public void backToMainMenu() {
        if (mainMenu != null) {
            mainMenu.setVisible(true);
        }
        if (addWordMenu != null) {
            addWordMenu.setVisible(false);
        }
        if (viewWordsMenu != null) {
            viewWordsMenu.setVisible(false);
        }
        if (learnMenu != null) {
            learnMenu.setVisible(false);
        }
    }

    public void addSection(String sectionName) {
        DatabaseManager.createSectionTable(sectionName);
    }

    public void insertWord(String sectionName, String article, String word, String translation) {
        if (DatabaseManager.insertWord(sectionName, article, word, translation)) {
            System.out.println("Word added successfully.");
        } else {
            System.out.println("Word already exists.");
        }
    }

    public void deleteTable(String tableName) {
        DatabaseManager.deleteTable(tableName);
    }

    public void deleteWord(String tableName, String word) {
        DatabaseManager.deleteWord(tableName, word);
    }

    public void populateTableList(GUIViewWordsMenu viewWordsMenu) {
        viewWordsMenu.populateTableList(DatabaseManager.fetchTableNames());
    }

    public void showTableContents(GUIViewWordsMenu viewWordsMenu, String tableName) {
        String[][] data = DatabaseManager.fetchTableContents(tableName);
        String[] columnNames = DatabaseManager.fetchTableColumnNames(tableName);
        viewWordsMenu.updateTableContents(data, columnNames);
    }

    public List<String[]> getWordsForFlashcards(List<String> sectionNames) {
        return DatabaseManager.fetchWordsFromSections(sectionNames);
    }

    public List<String> getSections() {
        return DatabaseManager.fetchTableNames();
    }
    
}
