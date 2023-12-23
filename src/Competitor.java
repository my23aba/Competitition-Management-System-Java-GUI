import java.util.Arrays;

public class Competitor {
    private int competitorNumber;
    private String name;
    private String category;
    private String level;
    private int[] scores;

    public Competitor(int competitorNumber, String name, String category, String level, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.category = category;
        this.level = level;
        this.scores = scores;
    }

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
    }

    public int[] getScores() {
        return scores;
    }

    public double getOverallScore() {
        // Implement the logic to calculate the overall score
        return Arrays.stream(scores).average().orElse(0);
    }

    public String getFullDetails() {
        return String.format("Competitor number %d, name %s, category %s, level %s. %s%n",
                competitorNumber, name, category, level, getShortDetails());
    }

    public String getShortDetails() {
        return String.format("CN %d (%s) has overall score %.2f.%n",
                competitorNumber, getInitials(), getOverallScore());
    }

    private String getInitials() {
        // Implement logic to extract initials from the name
        // For simplicity, let's assume initials are the first letters of the first and last names
        String[] nameParts = name.split(" ");
        return nameParts.length >= 2 ?
                Character.toString(nameParts[0].charAt(0)) + nameParts[1].charAt(0) :
                "";
    }

    void setName(String newName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setCategory(String newCategory) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setLevel(String newLevel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setScores(int[] newScores) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
