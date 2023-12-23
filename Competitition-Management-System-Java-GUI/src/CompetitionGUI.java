import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;

public class CompetitionGUI extends JFrame {

    private CompetitorList competitorList;

    private JTable competitorTable;
    private JTextArea detailsTextArea;
    private JButton updateButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton reportButton;
    private JButton addCompetitorButton;

    public CompetitionGUI() {
        competitorList = new CompetitorList();

        // Initialize GUI components
        competitorTable = new JTable();
        detailsTextArea = new JTextArea();
        updateButton = new JButton("Update");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        reportButton = new JButton("Generate Report");
        addCompetitorButton = new JButton("Add Competitor");

        // Set up layout manager
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(competitorTable), BorderLayout.CENTER);
        leftPanel.add(detailsTextArea, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(addCompetitorButton);

        add(leftPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        updateButton.addActionListener(e -> updateCompetitorTable());
        editButton.addActionListener(e -> editCompetitorDetails());
        removeButton.addActionListener(e -> removeCompetitor());
        reportButton.addActionListener(e -> generateReport());
        addCompetitorButton.addActionListener(e -> addCompetitorFromForm());

        // Set up JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialize the competitor table
        updateCompetitorTable();
    }

  
    
    private void updateCompetitorTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Competitor Number");
        model.addColumn("Name");
        model.addColumn("Category");
        model.addColumn("Level");
        model.addColumn("Scores");
        for (Competitor competitor : competitorList.getAllCompetitors()) {
            Object[] rowData = {
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getCategory(),
                    competitor.getLevel(),
                    Arrays.toString(competitor.getScores())
            };
            model.addRow(rowData);
        }
        competitorTable.setModel(model);
        String filePath = "C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\build\\classes\\competitors.csv";
        competitorList.writeCompetitorsToCSV(filePath);
    }

    private void editCompetitorDetails() {
        int selectedRow = competitorTable.getSelectedRow();
        if (selectedRow != -1) {
            int competitorNumber = (int) competitorTable.getValueAt(selectedRow, 0);
            Competitor competitor = competitorList.getCompetitorByNumber(competitorNumber);
            if (competitor != null) {
                // Display a dialog to edit competitor details
                String newName = JOptionPane.showInputDialog("Enter new name:", competitor.getName());
                String newCategory = JOptionPane.showInputDialog("Enter new category:", competitor.getCategory());
                String newLevel = JOptionPane.showInputDialog("Enter new level:", competitor.getLevel());

                // For simplicity, use JOptionPane for updating scores
                int[] newScores = new int[competitor.getScores().length];
                for (int i = 0; i < newScores.length; i++) {
                    String scoreStr = JOptionPane.showInputDialog("Enter new score for round " + (i + 1) + ":", competitor.getScores()[i]);
                    newScores[i] = Integer.parseInt(scoreStr);
                }

                // Update competitor details
                competitor.setName(newName);
                competitor.setCategory(newCategory);
                competitor.setLevel(newLevel);
                competitor.setScores(newScores);

                // Update the table
                updateCompetitorTable();

                // Update the CSV file
                competitorList.writeCompetitorsToCSV("C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\build\\classes\\competitors.csv");}}
    }

    private void removeCompetitor() {
        int selectedRow = competitorTable.getSelectedRow();
        if (selectedRow != -1) {
            int competitorNumber = (int) competitorTable.getValueAt(selectedRow, 0);
            competitorList.removeCompetitor(competitorNumber);
            updateCompetitorTable();
        }
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder();

        // Table of competitors with full details
        report.append("Competitors:\n");
        report.append("Competitor Number | Name | Category | Level | Scores\n");
        for (Competitor competitor : competitorList.getAllCompetitors()) {
            report.append(String.format("%d | %s | %s | %s | %s%n",
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getCategory(),
                    competitor.getLevel(),
                    Arrays.toString(competitor.getScores())));
        }
        report.append("\n");

        // Details of the competitor with the highest overall score
        Competitor winner = competitorList.getWinner();
        if (winner != null) {
            report.append("Competitor with the highest overall score:\n");
            report.append(String.format("Competitor Number: %d, Name: %s, Overall Score: %.2f%n",
                    winner.getCompetitorNumber(), winner.getName(), winner.getOverallScore()));
            report.append("\n");
        }

        // Other summary statistics (you can customize this part)
        // ...

        // Display the report in the detailsTextArea
        detailsTextArea.setText(report.toString());
    }

    private void addCompetitorFromForm() {
        // For simplicity, let's use JOptionPane for user input
        String name = JOptionPane.showInputDialog("Enter competitor name:");
        String category = JOptionPane.showInputDialog("Enter competitor category:");
        String level = JOptionPane.showInputDialog("Enter competitor level:");
        int[] scores = new int[5];
        for (int i = 0; i < 5; i++) {
            String scoreStr = JOptionPane.showInputDialog("Enter score for round " + (i + 1) + ":");
            scores[i] = Integer.parseInt(scoreStr);
        }

        // Generate a unique competitor number (replace this logic with your own)
        int competitorNumber = competitorList.getAllCompetitors().size() + 1;

        // Add the competitor to the CompetitorList
        competitorList.addCompetitor(competitorNumber, name, category, level, scores);

        // Update the competitor table
        updateCompetitorTable();
    }
    
    
/*private void loadCompetitorsFromCSV(String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = csvReader.readAll();

            // Assuming the first row contains column headers
            String[] columnHeaders = rows.get(0);

            DefaultTableModel model = new DefaultTableModel(columnHeaders, 0);

            for (int i = 1; i < rows.size(); i++) {
                model.addRow(rows.get(i));
            }

            // Update the table model
            competitorTable.setModel(model);

        } catch (IOException | CsvException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from CSV file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
 private void initComponents() {
        // ... Existing initialization code

        // Assuming you have a button for loading from CSV
        JButton loadCSVButton = new JButton("Load From CSV");
        loadCSVButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                loadCompetitorsFromCSV(filePath);
            }
        }); */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        


    public static void main(String[] args) {
         CompetitorList competitorList = new CompetitorList();
        competitorList.readCompetitorsFromCSV("C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\build\\classes\\competitors.csv");
        SwingUtilities.invokeLater(() -> new CompetitionGUI());
        
         
        
    }
}
