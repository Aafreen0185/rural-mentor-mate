package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mentor offering guidance to rural entrepreneurs.
 * Persisted to mentors.csv via FileManager.
 */
public class Mentor {

    private String id;
    private String name;
    private String email;
    private String location;
    private int yearsOfExperience;
    private String bio;
    private List<String> expertiseAreas;
    private List<Entrepreneur.Industry> industries;
    private double rating;
    private int totalSessions;

    public Mentor() {
        this.expertiseAreas = new ArrayList<>();
        this.industries     = new ArrayList<>();
        this.rating         = 0.0;
    }

    public Mentor(String id, String name, String email, String location, int yearsOfExperience, String bio) {
        this();
        this.id                 = id;
        this.name               = name;
        this.email              = email;
        this.location           = location;
        this.yearsOfExperience  = yearsOfExperience;
        this.bio                = bio;
    }

    // ── Getters ──────────────────────────────────────────────

    public String getId()                              { return id; }
    public String getName()                            { return name; }
    public String getEmail()                           { return email; }
    public String getLocation()                        { return location; }
    public int getYearsOfExperience()                  { return yearsOfExperience; }
    public String getBio()                             { return bio; }
    public List<String> getExpertiseAreas()            { return expertiseAreas; }
    public List<Entrepreneur.Industry> getIndustries() { return industries; }
    public double getRating()                          { return rating; }
    public int getTotalSessions()                      { return totalSessions; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(String id)                               { this.id = id; }
    public void setName(String name)                           { this.name = name; }
    public void setEmail(String email)                         { this.email = email; }
    public void setLocation(String location)                   { this.location = location; }
    public void setYearsOfExperience(int years)                { this.yearsOfExperience = years; }
    public void setBio(String bio)                             { this.bio = bio; }
    public void setRating(double rating)                       { this.rating = rating; }
    public void setTotalSessions(int totalSessions)            { this.totalSessions = totalSessions; }

    public void addExpertise(String area) {
        if (area != null && !area.isBlank()) expertiseAreas.add(area.trim().toLowerCase());
    }

    public void addIndustry(Entrepreneur.Industry industry) {
        if (industry != null && !industries.contains(industry)) industries.add(industry);
    }

    // ── CSV ──────────────────────────────────────────────────

    public String toCsvRow() {
        List<String> industryNames = new ArrayList<>();
        for (Entrepreneur.Industry i : industries) industryNames.add(i.name());

        return String.join("|",
                id, name, email, location,
                String.valueOf(yearsOfExperience),
                bio.replace("|", ";"),
                String.join(",", expertiseAreas),
                String.join(",", industryNames),
                String.format("%.1f", rating),
                String.valueOf(totalSessions)
        );
    }

    public static Mentor fromCsvRow(String row) {
        String[] p = row.split("\\|", -1);
        if (p.length < 10) return null;
        Mentor m = new Mentor();
        m.id                = p[0].trim();
        m.name              = p[1].trim();
        m.email             = p[2].trim();
        m.location          = p[3].trim();
        try { m.yearsOfExperience = Integer.parseInt(p[4].trim()); } catch (NumberFormatException ignored) {}
        m.bio               = p[5].trim();
        for (String e : p[6].split(",")) m.addExpertise(e);
        for (String i : p[7].split(",")) m.addIndustry(Entrepreneur.Industry.parse(i));
        try { m.rating = Double.parseDouble(p[8].trim()); } catch (NumberFormatException ignored) {}
        try { m.totalSessions = Integer.parseInt(p[9].trim()); } catch (NumberFormatException ignored) {}
        return m;
    }

    public static String csvHeader() {
        return "id|name|email|location|yearsOfExperience|bio|expertiseAreas|industries|rating|totalSessions";
    }

    /**
     * Formatted profile card for CLI display.
     */
    public String toProfileCard() {
        return String.format(
            "  ID      : %s\n" +
            "  Name    : %s\n" +
            "  Location: %s\n" +
            "  Exp     : %d years\n" +
            "  Rating  : %.1f / 5.0  (%d sessions)\n" +
            "  Bio     : %s\n" +
            "  Skills  : %s",
            id, name, location, yearsOfExperience,
            rating, totalSessions,
            bio,
            String.join(", ", expertiseAreas)
        );
    }

    @Override
    public String toString() {
        return String.format("Mentor[id=%s, name=%s, exp=%dy, rating=%.1f]",
                id, name, yearsOfExperience, rating);
    }
}
