import Controller.Controller;
import view.CompetitionGUI;
import model.CompetitorList;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Competition {
    private JFrame selectionFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Competition().showSelectionPanel());
    }

    private void showSelectionPanel() {
        selectionFrame = new JFrame("Role Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(2, 1));

        JButton staffButton = new JButton("Staff");
        JButton competitorButton = new JButton("Competitor");

        staffButton.addActionListener(e -> openCompetitionGUI("staff"));
        competitorButton.addActionListener(e -> openCompetitionGUI("competitor"));

        selectionPanel.add(staffButton);
        selectionPanel.add(competitorButton);

        selectionFrame.add(selectionPanel);
        selectionFrame.setSize(400, 400);
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

    private void openCompetitionGUI(String role) {
        selectionFrame.dispose(); // Close the selection frame

        // Create the Model
        CompetitorList model = new CompetitorList();

        // Read competitors from CSV (optional)
        model.readCompetitorsFromCSV("src\\competitors.csv");

        // Create the User based on the selected role
        User user = new User(role);

        // Create the View for staff and competitor
        CompetitionGUI competitionView = new CompetitionGUI(model, user);

        // Create the Controller for staff and competitor
        Controller competitionController = new Controller(competitionView, model);

        // Set up the GUI for staff and competitor
        competitionView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        competitionView.pack();
        competitionView.setLocationRelativeTo(null);
        competitionView.setVisible(true);
    }
}

