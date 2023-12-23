package model;

import Controller.Controller;
import java.util.Arrays;

public class Competitor {
    private int competitorNumber;
    private String name;
    private Category category;
    private Level level;
    private int age;
    private String gender;
    private String country;
    private int[] scores;

    public Competitor(int competitorNumber, String name, Level level, Category category, int[] scores,
                      int age, String gender, String country) {
        this.competitorNumber = competitorNumber;
        this.name = name;
               this.scores = scores;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.level = level;
        this.category = category;
    }

     public enum Level {
        NOVICE, PROFESSIONAL, MEDIUM
    }

    public enum Category {
        ATHLETE, RUNNING, JAVELIN
    }
    
    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Level getLevel() {
        return level;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public int[] getScores() {
        return scores;
    }

    public double getOverallScore() {
        // Implement the logic to calculate the overall score
        return Arrays.stream(scores).average().orElse(0);
    }

    public String getFullDetails() {
        return String.format("Competitor number %d, name %s, category %s, level %s, age %d, gender %s, country %s. %s%n",
                competitorNumber, name, category, level, age, gender, country, getShortDetails());
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

    public void setName(String newName) {
        this.name = newName;
    }

    public void setCategory(Category newCategory) {
        this.category = newCategory;
    }

    public void setLevel(Level newLevel) {
        this.level = newLevel;
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }

    public void setGender(String newGender) {
        this.gender = newGender;
    }

    public void setCountry(String newCountry) {
        this.country = newCountry;
    }

    public void setScores(int[] newScores) {
        this.scores = newScores;
    }
}
