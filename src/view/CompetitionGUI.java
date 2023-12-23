package view;
import model.Competitor;
import model.CompetitorList;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class CompetitionGUI extends JFrame {

    private User currentUser;
    private CompetitorList model;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea searchResultTextArea;
    private JTable competitorTable;
    private JTextArea detailsTextArea;
    private JButton updateButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton reportButton;
    private JButton addCompetitorButton;
    private JButton shortButton;
    private JButton generateReportButton;
    

    public CompetitionGUI(CompetitorList model, User user) {
        this.model = model;
        this.currentUser = user;

        // Initialize GUI components
        competitorTable = new JTable();
        detailsTextArea = new JTextArea();
        updateButton = new JButton("Update");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        reportButton = new JButton("Generate Report");
        shortButton = new JButton("Short Detail");
        searchField = new JTextField(10);
        searchButton = new JButton("Search");
        searchResultTextArea = new JTextArea();
        addCompetitorButton = new JButton("Add Competitor");
        generateReportButton = new JButton("Generate Final Report");
        

        // Set up layout manager
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(generateReportButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(shortButton);
        buttonPanel.add(addCompetitorButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(competitorTable), BorderLayout.CENTER);
        tablePanel.add(detailsTextArea, BorderLayout.SOUTH);
        tablePanel.add(searchResultTextArea, BorderLayout.NORTH);

        // Add the panels to the frame
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        setupButtonVisibility();

        // Add action listeners
        updateButton.addActionListener(e -> updateCompetitorTable());
        editButton.addActionListener(e -> editCompetitorDetails());
        removeButton.addActionListener(e -> removeCompetitor());
        reportButton.addActionListener(e -> generateReport());
        shortButton.addActionListener(e -> displayShortDetailsForCompetitor());
        addCompetitorButton.addActionListener(e -> addCompetitorFromForm());
        searchButton.addActionListener(e -> searchCompetitor());
        generateReportButton.addActionListener(e -> generateFinalReport());

        // Set up JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialize the competitor table
        updateCompetitorTable();
    }

    public void searchCompetitor() {
        try {
            int competitorNumberToSearch = Integer.parseInt(searchField.getText());
            Competitor foundCompetitor = model.getCompetitorByNumber(competitorNumberToSearch);
            if (foundCompetitor != null) {
                searchResultTextArea.setText("Competitor Found:\n" + foundCompetitor.getFullDetails());
            } else {
                searchResultTextArea.setText("Competitor not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid competitor number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setupButtonVisibility() {
        if ("staff".equals(currentUser.getRole())) {
            // Staff can access all buttons
            updateButton.setEnabled(true);
            editButton.setEnabled(true);
            removeButton.setEnabled(true);
            reportButton.setEnabled(true);
            shortButton.setEnabled(true);
            addCompetitorButton.setEnabled(true);
            searchButton.setEnabled(true);
        } else if ("competitor".equals(currentUser.getRole())) {
            // Competitors can only search for their scores
            updateButton.setEnabled(false);
            editButton.setEnabled(false);
            removeButton.setEnabled(false);
            reportButton.setEnabled(false);
            shortButton.setEnabled(false);
            addCompetitorButton.setEnabled(false);
            searchButton.setEnabled(true);
        }
    }
    public void updateCompetitorTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Competitor Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Level");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Country");
        tableModel.addColumn("Scores");

        for (Competitor competitor : model.getAllCompetitors()) {
            Object[] rowData = {
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getCategory(),
                    competitor.getLevel(),
                    competitor.getAge(),
                    competitor.getGender(),
                    competitor.getCountry(),
                    Arrays.toString(competitor.getScores())
            };
            tableModel.addRow(rowData);
        }

        competitorTable.setModel(tableModel);
        String filePath = "C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\src\\competitors.csv";
        if ("staff".equals(currentUser.getRole())) {
        model.writeCompetitorsToCSV(filePath);
    }
    }

    public void editCompetitorDetails() {
        
        if ("competitor".equals(currentUser.getRole())) {
        JOptionPane.showMessageDialog(this, "Competitors are not allowed to edit details.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int selectedRow = competitorTable.getSelectedRow();
    if (selectedRow != -1) {
        int competitorNumber = (int) competitorTable.getValueAt(selectedRow, 0);
        Competitor competitor = model.getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            JTextField nameField = new JTextField(competitor.getName());
            JComboBox<Competitor.Level> levelComboBox = new JComboBox<>(Competitor.Level.values());
            levelComboBox.setSelectedItem(competitor.getLevel());
            JComboBox<Competitor.Category> categoryComboBox = new JComboBox<>(Competitor.Category.values());
            categoryComboBox.setSelectedItem(competitor.getCategory());
            JTextField ageField = new JTextField(String.valueOf(competitor.getAge()));
            JTextField genderField = new JTextField(competitor.getGender());
            JTextField countryField = new JTextField(competitor.getCountry());

            Object[] message = {
                    "Name:", nameField,
                    "Level:", levelComboBox,
                    "Category:", categoryComboBox,
                    "Age:", ageField,
                    "Gender:", genderField,
                    "Country:", countryField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Edit Competitor Details", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    String newName = nameField.getText();
                    Competitor.Level newLevel = (Competitor.Level) levelComboBox.getSelectedItem();
                    Competitor.Category newCategory = (Competitor.Category) categoryComboBox.getSelectedItem();
                    int newAge = Integer.parseInt(ageField.getText());
                    String newGender = genderField.getText();
                    String newCountry = countryField.getText();

                    int[] newScores = new int[competitor.getScores().length];
                    for (int i = 0; i < newScores.length; i++) {
                        String scoreStr = JOptionPane.showInputDialog("Enter new score for round " + (i + 1) + ":", competitor.getScores()[i]);
                        newScores[i] = Integer.parseInt(scoreStr);
                    }

                    competitor.setName(newName);
                    competitor.setLevel(newLevel);
                    competitor.setCategory(newCategory);
                    competitor.setAge(newAge);
                    competitor.setGender(newGender);
                    competitor.setCountry(newCountry);
                    competitor.setScores(newScores);

                    updateCompetitorTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    }

    public void removeCompetitor() {
        
        if ("competitor".equals(currentUser.getRole())) {
        JOptionPane.showMessageDialog(this, "Competitors are not allowed to remove competitors.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        return;
    }       
        
        int selectedRow = competitorTable.getSelectedRow();
        if (selectedRow != -1) {
            int competitorNumber = (int) competitorTable.getValueAt(selectedRow, 0);
            model.removeCompetitor(competitorNumber);
            updateCompetitorTable();
        }
    }

    public void generateReport() {
        if ("competitor".equals(currentUser.getRole())) {
            detailsTextArea.setText("Access Denied: Competitors are not allowed to generate reports.");
            return;
        }

        int selectedRow = competitorTable.getSelectedRow();
        if (selectedRow != -1) {
            int competitorNumber = (int) competitorTable.getValueAt(selectedRow, 0);
            Competitor competitor = model.getCompetitorByNumber(competitorNumber);
            if (competitor != null) {
                detailsTextArea.setText(competitor.getFullDetails());
            }
        }
    }

    public void shortReport() {
        StringBuilder sreport = new StringBuilder();
        Competitor winner = model.getWinner();
        if (winner != null) {
            sreport.append("Competitor with the highest overall score:\n");
            sreport.append(String.format("Competitor Number: %d, Name: %s, Overall Score: %.2f%n",
                    winner.getCompetitorNumber(), winner.getName(), winner.getOverallScore()));
            sreport.append("\n");
        }
        detailsTextArea.setText(sreport.toString());
    }

    public void addCompetitorFromForm() {
        
   JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JTextField countryField = new JTextField();
    JTextField scoresField = new JTextField();

    // Create dropdown lists for Level and Category
    JComboBox<Competitor.Level> levelComboBox = new JComboBox<>(Competitor.Level.values());
    JComboBox<Competitor.Category> categoryComboBox = new JComboBox<>(Competitor.Category.values());

    // Create a panel for the form with labels and input fields
    JPanel formPanel = new JPanel(new GridLayout(8, 2));
    formPanel.add(new JLabel("Name:"));
    formPanel.add(nameField);
    formPanel.add(new JLabel("Age:"));
    formPanel.add(ageField);
    formPanel.add(new JLabel("Gender:"));
    formPanel.add(genderField);
    formPanel.add(new JLabel("Country:"));
    formPanel.add(countryField);
    formPanel.add(new JLabel("Level:"));
    formPanel.add(levelComboBox);  // Use dropdown list
    formPanel.add(new JLabel("Category:"));
    formPanel.add(categoryComboBox);  // Use dropdown list
    formPanel.add(new JLabel("Scores (comma-separated, e.g., 1,2,3,4,5):"));
    formPanel.add(scoresField);

    // Show the form to the user
    int result = JOptionPane.showConfirmDialog(
            this, formPanel, "Add Competitor", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        // Get values from input fields and dropdown lists
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = genderField.getText();
        String country = countryField.getText();
        String scoresText = scoresField.getText();

        // Validate age is a non-empty integer
        if (!isNonEmptyInteger(ageText)) {
            JOptionPane.showMessageDialog(this, "Age must be a valid non-empty integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int age = Integer.parseInt(ageText);

        // Validate name is a non-empty string containing only letters
        if (!isNonEmptyStringWithLetters(name)) {
            JOptionPane.showMessageDialog(this, "Name must be a non-empty string containing only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate country is a non-empty string containing only letters
        if (!isNonEmptyStringWithLetters(country)) {
            JOptionPane.showMessageDialog(this, "Country must be a non-empty string containing only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse scores
        int[] scores = Arrays.stream(scoresText.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Get selected values from the dropdown lists
        Competitor.Level selectedLevel = (Competitor.Level) levelComboBox.getSelectedItem();
        Competitor.Category selectedCategory = (Competitor.Category) categoryComboBox.getSelectedItem();

        // Add the competitor to the model
        int competitorNumber = model.getAllCompetitors().size() + 1;
        model.addCompetitor(competitorNumber, name, selectedCategory, selectedLevel, scores, age, gender, country);
        updateCompetitorTable();
    }
}

// Helper method to check if a string is a non-empty integer
private boolean isNonEmptyInteger(String str) {
    return !str.isEmpty() && isInteger(str);
}

// Helper method to check if a string contains only letters
private boolean isAlpha(String str) {
    return str.matches("^[a-zA-Z]+$");
}

// Helper method to check if a string is a non-empty string containing only letters
private boolean isNonEmptyStringWithLetters(String str) {
    return !str.isEmpty() && isAlpha(str);
}

// Helper method to check if a string is an integer
private boolean isInteger(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
    
     
    }

    public void displayShortDetailsForCompetitor() {
        try {
            
             if ("competitor".equals(currentUser.getRole())) {
            JOptionPane.showMessageDialog(this, "Competitors are not allowed to add competitors.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
            int competitorNumberToDisplay = Integer.parseInt(JOptionPane.showInputDialog("Enter competitor number:"));
            if (model.isValidCompetitorNumber(competitorNumberToDisplay)) {
                Competitor competitor = model.getCompetitorByNumber(competitorNumberToDisplay);
                JOptionPane.showMessageDialog(this, "Competitor Details:\n" + competitor.getShortDetails(), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid competitor number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid competitor number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateFinalReport() {
         if ("competitor".equals(currentUser.getRole())) {
            JOptionPane.showMessageDialog(this, "Competitors are not allowed to add competitors.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String filePath = "C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\src\\final_report.txt";  // Change this path
        model.produceFinalReport(filePath);
        JOptionPane.showMessageDialog(this, "Final report generated successfully!", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
    }

  //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new CompetitionGUI(new CompetitorList()));
    //}
    
    

      public JButton getUpdateButton() {
     return updateButton;
    }
      public JButton getEditButton() {
     return editButton;
    }
      public JButton getRemoveButton() {
     return removeButton;
    }
      public JButton getReportButton() {
     return reportButton;
    }
      public JButton getShortButton() {
     return shortButton;
    }
    public JButton getAddCompetitorButton() {
     return addCompetitorButton;
    }
    public JButton getSearchButton() {
     return searchButton;
    }
      
      
      
      
}
