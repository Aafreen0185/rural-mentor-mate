package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rural entrepreneur seeking mentorship.
 * Persisted to entrepreneurs.csv via FileManager.
 */
public class Entrepreneur {

    private String id;
    private String name;
    private String email;
    private String location;
    private Industry industry;
    private String businessDescription;
    private List<String> goals;

    public enum Industry {
        AGRICULTURE,
        HANDICRAFTS,
        TOURISM,
        RETAIL,
        TECHNOLOGY,
        FOOD_AND_BEVERAGE,
        OTHER;

        public static Industry parse(String input) {
            if (input == null) return OTHER;
            try {
                return valueOf(input.toUpperCase().replace(" ", "_").replace("&", "AND"));
            } catch (IllegalArgumentException e) {
                return OTHER;
            }
        }

        public String display() {
            return name().replace("_", " ");
        }
    }

    public Entrepreneur() {
        this.goals = new ArrayList<>();
    }

    public Entrepreneur(String id, String name, String email, String location, Industry industry, String businessDescription) {
        this();
        this.id                  = id;
        this.name                = name;
        this.email               = email;
        this.location            = location;
        this.industry            = industry;
        this.businessDescription = businessDescription;
    }

    // ── Getters ──────────────────────────────────────────────

    public String getId()                   { return id; }
    public String getName()                 { return name; }
    public String getEmail()                { return email; }
    public String getLocation()             { return location; }
    public Industry getIndustry()           { return industry; }
    public String getBusinessDescription()  { return businessDescription; }
    public List<String> getGoals()          { return goals; }

    // ── Setters ──────────────────────────────────────────────

    public void setId(String id)                                { this.id = id; }
    public void setName(String name)                            { this.name = name; }
    public void setEmail(String email)                          { this.email = email; }
    public void setLocation(String location)                    { this.location = location; }
    public void setIndustry(Industry industry)                  { this.industry = industry; }
    public void setBusinessDescription(String desc)             { this.businessDescription = desc; }

    public void addGoal(String goal) {
        if (goal != null && !goal.isBlank()) goals.add(goal.trim().toLowerCase());
    }

    // ── CSV ──────────────────────────────────────────────────

    public String toCsvRow() {
        return String.join("|",
                id, name, email, location,
                industry.name(),
                businessDescription.replace("|", ";"),
                String.join(",", goals)
        );
    }

    public static Entrepreneur fromCsvRow(String row) {
        String[] p = row.split("\\|", -1);
        if (p.length < 7) return null;
        Entrepreneur e = new Entrepreneur();
        e.id                  = p[0].trim();
        e.name                = p[1].trim();
        e.email               = p[2].trim();
        e.location            = p[3].trim();
        e.industry            = Industry.parse(p[4].trim());
        e.businessDescription = p[5].trim();
        for (String g : p[6].split(",")) e.addGoal(g);
        return e;
    }

    public static String csvHeader() {
        return "id|name|email|location|industry|businessDescription|goals";
    }

    @Override
    public String toString() {
        return String.format("Entrepreneur[id=%s, name=%s, industry=%s, location=%s]",
                id, name, industry.display(), location);
    }
}
