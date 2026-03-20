package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the compatibility result between one Entrepreneur and one Mentor.
 * Produced by MatchingEngine. Implements Comparable for ranked display.
 */
public class MatchResult implements Comparable<MatchResult> {

    private Mentor mentor;
    private double compatibilityScore;   // 0.0 – 100.0
    private List<String> matchReasons;   // why this mentor was matched
    private String compatibilityGrade;   // Excellent / Good / Fair / Low

    public MatchResult(Mentor mentor) {
        this.mentor       = mentor;
        this.matchReasons = new ArrayList<>();
    }

    // ── Setters ──────────────────────────────────────────────

    public void setCompatibilityScore(double score) {
        this.compatibilityScore  = Math.min(100.0, Math.max(0.0, score));
        this.compatibilityGrade  = computeGrade(this.compatibilityScore);
    }

    public void addMatchReason(String reason) {
        if (reason != null && !reason.isBlank()) matchReasons.add(reason);
    }

    // ── Getters ──────────────────────────────────────────────

    public Mentor getMentor()                    { return mentor; }
    public double getCompatibilityScore()        { return compatibilityScore; }
    public List<String> getMatchReasons()        { return matchReasons; }
    public String getCompatibilityGrade()        { return compatibilityGrade; }

    // ── Helpers ──────────────────────────────────────────────

    private String computeGrade(double score) {
        if (score >= 80) return "Excellent";
        if (score >= 60) return "Good";
        if (score >= 40) return "Fair";
        return "Low";
    }

    /**
     * One-line summary for the ranked leaderboard view.
     */
    public String toRankRow(int rank) {
        return String.format("#%-3d %-20s %-15s %5.1f%%  %-10s %dy exp",
                rank,
                mentor.getName(),
                mentor.getLocation(),
                compatibilityScore,
                compatibilityGrade,
                mentor.getYearsOfExperience()
        );
    }

    /**
     * Full match report shown when user selects a mentor.
     */
    public String toDetailedReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("┌─────────────────────────────────────────┐\n");
        sb.append(String.format("│  Match Report: %-25s│\n", mentor.getName()));
        sb.append("├─────────────────────────────────────────┤\n");
        sb.append(String.format("│  Compatibility : %-5.1f%%  (%s)%s│\n",
                compatibilityScore, compatibilityGrade,
                " ".repeat(Math.max(0, 14 - compatibilityGrade.length()))));
        sb.append(String.format("│  Experience    : %-22s│\n", mentor.getYearsOfExperience() + " years"));
        sb.append(String.format("│  Location      : %-22s│\n", mentor.getLocation()));
        sb.append(String.format("│  Rating        : %-22s│\n",
                String.format("%.1f / 5.0 (%d sessions)", mentor.getRating(), mentor.getTotalSessions())));
        sb.append("├─────────────────────────────────────────┤\n");
        sb.append("│  Why this match:                        │\n");
        for (String reason : matchReasons) {
            sb.append(String.format("│    + %-35s│\n", reason));
        }
        sb.append("├─────────────────────────────────────────┤\n");
        sb.append("│  Bio:                                   │\n");
        String bio = mentor.getBio();
        for (int i = 0; i < bio.length(); i += 37) {
            sb.append(String.format("│  %-39s│\n", bio.substring(i, Math.min(i + 37, bio.length()))));
        }
        sb.append("└─────────────────────────────────────────┘\n");
        return sb.toString();
    }

    @Override
    public int compareTo(MatchResult other) {
        return Double.compare(other.compatibilityScore, this.compatibilityScore); // descending
    }

    @Override
    public String toString() {
        return String.format("MatchResult[mentor=%s, score=%.1f, grade=%s]",
                mentor.getName(), compatibilityScore, compatibilityGrade);
    }
}
