
import model.Competitor;

import model.CompetitorList;

public class Manager {
    private CompetitorList model;

    public Manager(CompetitorList model) {
        this.model = model;
    }

    public void startCompetition() {
        // Initialize and set up the competition
        // ...
    }

    public void closeCompetition() {
        // Write the final report to a text file
        // ...
    }

    public void handleUserInput(int competitorNumber) {
        // Handle user input to interact with competitors
        Competitor competitor = model.getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            // Perform actions based on user input or application logic
            // Example: Display competitor details, update competitor information, etc.
        } else {
            // Handle case when competitor is not found
        }
    }
}
