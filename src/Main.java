package src;

import src.GUI.GUIMainMenu;

import src.GUI.Controlers;

public class Main {
    public static void main(String[] args) {
        // Initialize the database connection and create tables
        DatabaseConnection.createNewTable();

        // Initialize Controlers
        Controlers controlers = new Controlers();

        // Initialize the GUI main menu
        new GUIMainMenu(controlers);
    }
}
