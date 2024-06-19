package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {
    public static void insertUser(String name) {
        String sql = "INSERT INTO users(name) VALUES(?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Dodano użytkownika: " + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAllUsers() {
        String sql = "SELECT id, name FROM users";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                   rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
    
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela '" + tableName + "' została usunięta.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public static void deleteRowFromTable(String tableName, String id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id)); // Konwersja id z String na int
            pstmt.executeUpdate();
            System.out.println("Wiersz o id " + id + " został usunięty z tabeli " + tableName + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Błąd formatu ID: " + e.getMessage());
        }
    }
}
