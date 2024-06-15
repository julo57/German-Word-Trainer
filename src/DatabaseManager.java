package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Połączenie z SQLite zostało nawiązane.");
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
            System.out.println("Tabela " + sectionName + " została utworzona.");
        } catch (SQLException e) {
            System.out.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }

    public static boolean insertWord(String sectionName, String article, String word, String translation) {
        if (wordExists(sectionName, word)) {
            return false;
        }

        String sql = "INSERT INTO " + sectionName + " (article, word, translation) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, article);
            pstmt.setString(2, word);
            pstmt.setString(3, translation);
            pstmt.executeUpdate();
            System.out.println("Word inserted: " + word + " -> " + translation);
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

    public static List<String> fetchTableNames() {
        List<String> tableNames = new ArrayList<>();
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania nazw tabel: " + e.getMessage());
        }
        return tableNames;
    }

    public static String[][] fetchTableContents(String tableName) {
        List<String[]> contents = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                contents.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania danych z tabeli: " + e.getMessage());
        }

        String[][] data = new String[contents.size()][];
        for (int i = 0; i < contents.size(); i++) {
            data[i] = contents.get(i);
        }
        return data;
    }

    public static String[] fetchTableColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " LIMIT 1";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                columnNames.add(rs.getMetaData().getColumnName(i + 1));
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania nazw kolumn: " + e.getMessage());
        }

        return columnNames.toArray(new String[0]);
    }

    public static void deleteTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela " + tableName + " została usunięta.");
        } catch (SQLException e) {
            System.out.println("Błąd podczas usuwania tabeli: " + e.getMessage());
        }
    }

    public static List<String[]> fetchWordsFromSections(List<String> sectionNames) {
        List<String[]> words = new ArrayList<>();
        for (String section : sectionNames) {
            String sql = "SELECT word, translation, article FROM " + section;

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    words.add(new String[]{rs.getString("word"), rs.getString("translation"), rs.getString("article")});
                }
            } catch (SQLException e) {
                System.out.println("Błąd podczas pobierania słów z sekcji: " + e.getMessage());
            }
        }
        return words;
    }

    public static void deleteWord(String tableName, String word) {
        String sql = "DELETE FROM " + tableName + " WHERE word = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.executeUpdate();
            System.out.println("Word deleted: " + word);
        } catch (SQLException e) {
            System.out.println("Błąd podczas usuwania słowa: " + e.getMessage());
        }
    }
}
