import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompetitorList {

    
    private List<Competitor> competitors;

    public CompetitorList() {
        competitors = new ArrayList<>();
    }

    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public void addCompetitor(int competitorNumber, String name, String category, String level, int[] scores) {
        Competitor competitor = new Competitor(competitorNumber, name, category, level, scores);
        competitors.add(competitor);
    }

    public Competitor getCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }

    public void removeCompetitor(int competitorNumber) {
        Competitor competitorToRemove = null;
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                competitorToRemove = competitor;
                break;
            }
        }
        if (competitorToRemove != null) {
            competitors.remove(competitorToRemove);
        }
    }

    public List<Competitor> getAllCompetitors() {
        return competitors;
    }

    public Competitor getWinner() {
        return competitors.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);
    }

    // Method to write competitors to CSV file
    public void writeCompetitorsToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\build\\classes\\competitors.csv"))) {
            for (Competitor competitor : competitors) {
                String line = String.format("%d,%s,%s,%s,%s",
                        competitor.getCompetitorNumber(),
                        competitor.getName(),
                        competitor.getCategory(),
                        competitor.getLevel(),
                        String.join(",", Arrays.stream(competitor.getScores()).mapToObj(String::valueOf).toArray(String[]::new)));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read competitors from CSV file
    public void readCompetitorsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\MOCIN\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication2\\build\\classes\\competitors.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int competitorNumber = Integer.parseInt(parts[0]);
                String name = parts[1];
                String category = parts[2];
                String level = parts[3];
                int[] scores = Arrays.stream(parts[4].split(",")).mapToInt(Integer::parseInt).toArray();
                addCompetitor(competitorNumber, name, category, level, scores);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add other methods as needed

    public static void main(String[] args) {
        // You can test the CompetitorList class here if needed
    }
}
