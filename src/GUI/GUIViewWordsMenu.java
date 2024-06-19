package src.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import src.DatabaseOperations;

public class GUIViewWordsMenu extends JFrame {
    private DatabaseOperations databaseOperations = new DatabaseOperations();
    private Controlers controlers;
    private JList<String> tableList;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private JButton deleteTableButton;
    private JButton deleteWordButton;

    public GUIViewWordsMenu(Controlers controlers) {
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
        setTitle("View Words Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create list of tables
        JLabel tablesLabel = new JLabel("Tables:");
        tablesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(tablesLabel, BorderLayout.NORTH);

        tableList = new JList<>();
        tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableList.addListSelectionListener(e -> controlers.showTableContents(this, tableList.getSelectedValue()));
        JScrollPane listScrollPane = new JScrollPane(tableList);
        listScrollPane.setPreferredSize(new Dimension(200, 400));
        panel.add(listScrollPane, BorderLayout.WEST);

        // Create table for displaying table contents
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back");
        deleteTableButton = new JButton("Delete Table");
        deleteWordButton = new JButton("Delete Word");

        buttonsPanel.add(backButton);
        buttonsPanel.add(deleteTableButton);
        buttonsPanel.add(deleteWordButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        backButton.addActionListener(e -> controlers.backToMainMenu());
        deleteTableButton.addActionListener(e -> deleteSelectedTable());
        deleteWordButton.addActionListener(e -> deleteSelectedWord());

        // Add panel to frame
        add(panel);

        // Populate table list
        controlers.populateTableList(this);
    }

    public void populateTableList(List<String> tableNames) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String tableName : tableNames) {
            listModel.addElement(tableName);
        }
        tableList.setModel(listModel);
    }

    public void updateTableContents(String[][] data, String[] columnNames) {
        tableModel.setDataVector(data, columnNames);
    }

    private void deleteSelectedTable() {
        String selectedTable = tableList.getSelectedValue();
        if (selectedTable != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the table: " + selectedTable + "?", "Delete Table", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controlers.deleteTable(selectedTable);
                controlers.populateTableList(this);
                tableModel.setRowCount(0); // Clear the table contents
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedWord() {
        String selectedTable = tableList.getSelectedValue();
        int selectedRow = table.getSelectedRow();
        if (selectedTable != null && selectedRow != -1) {
            String selectedId = table.getValueAt(selectedRow, 0).toString(); // Pobieranie id z pierwszej kolumny jako String
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the entry with ID: " + selectedId + "?", "Delete Entry", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                databaseOperations.deleteRowFromTable(selectedTable, selectedId);
                refreshTableContents(selectedTable); // Odświeżanie zawartości tabeli
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table and an entry to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTableContents(String tableName) {
        controlers.showTableContents(this, tableName);
    }
}
