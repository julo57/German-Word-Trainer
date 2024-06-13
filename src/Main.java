package src;

public class Main {
    public static void main(String[] args) {
        // Tworzenie tabeli w bazie danych
        DatabaseConnection.createNewTable();

        

     

        // Tworzenie instancji SimpleGUI
        new SimpleGUI();
    }
}
