package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.DriverManager;


public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Błąd podczas nawiązywania połączenia: " + e.getMessage());
        }
        return conn;
    }

    public static void createSectionTable(String sectionName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + sectionName + " (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    article TEXT,\n"
                + "    word TEXT NOT NULL,\n"
                + "    translation TEXT NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }

    public static boolean insertWord(String sectionName, String article, String word, String translation) {
        if (wordExists(sectionName, word)) {
            return false;
        }

        String sql = "INSERT INTO " + sectionName + "(article, word, translation) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, article);
            pstmt.setString(2, word);
            pstmt.setString(3, translation);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Błąd podczas wstawiania danych: " + e.getMessage());
            return false;
        }
    }

    public static boolean wordExists(String sectionName, String word) {
        String sql = "SELECT id FROM " + sectionName + " WHERE word = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Błąd podczas sprawdzania istnienia słowa: " + e.getMessage());
            return false;
        }
    }
}
