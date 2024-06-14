package src.GUI;

public class Controlers {
    private GUIMainMenu mainMenu;
    private GUIAddWordMenu addWordMenu;
    private GUIViewWordsMenu viewWordsMenu;

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
    }
}
