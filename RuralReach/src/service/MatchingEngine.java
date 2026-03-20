package service;

import model.Entrepreneur;
import model.Mentor;
import model.MatchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Core matching algorithm. Scores each Mentor against an Entrepreneur
 * profile using weighted criteria — industry, location, expertise keywords,
 * and experience. Returns a ranked list of MatchResult objects.
 *
 * Scoring breakdown:
 *   Industry match    : 35 points
 *   Location match    : 20 points
 *   Expertise keywords: 30 points (scaled)
 *   Experience bonus  : 15 points (scaled)
 */
public class MatchingEngine {

    private static final double INDUSTRY_WEIGHT   = 35.0;
    private static final double LOCATION_WEIGHT   = 20.0;
    private static final double EXPERTISE_WEIGHT  = 30.0;
    private static final double EXPERIENCE_WEIGHT = 15.0;

    /**
     * Scores all mentors against the given entrepreneur and returns
     * a sorted list of MatchResult (best match first).
     */
    public List<MatchResult> findMatches(Entrepreneur entrepreneur, List<Mentor> mentors) {
        List<MatchResult> results = new ArrayList<>();

        for (Mentor mentor : mentors) {
            MatchResult result = score(entrepreneur, mentor);
            results.add(result);
        }

        Collections.sort(results);
        return results;
    }

    private MatchResult score(Entrepreneur entrepreneur, Mentor mentor) {
        MatchResult result = new MatchResult(mentor);
        double total = 0.0;

        // 1. Industry match (35 pts)
        boolean industryMatch = mentor.getIndustries().contains(entrepreneur.getIndustry());
        if (industryMatch) {
            total += INDUSTRY_WEIGHT;
            result.addMatchReason("Specialises in " + entrepreneur.getIndustry().display());
        }

        // 2. Location match (20 pts) — exact city or same state keyword
        double locationScore = scoreLocation(entrepreneur.getLocation(), mentor.getLocation());
        total += locationScore;
        if (locationScore >= LOCATION_WEIGHT) {
            result.addMatchReason("Based in the same region (" + mentor.getLocation() + ")");
        } else if (locationScore > 0) {
            result.addMatchReason("Nearby region (" + mentor.getLocation() + ")");
        }

        // 3. Expertise keyword match (30 pts)
        double expertiseScore = scoreExpertise(entrepreneur, mentor);
        total += expertiseScore;
        if (expertiseScore >= EXPERTISE_WEIGHT * 0.6) {
            result.addMatchReason("Strong expertise alignment with your goals");
        } else if (expertiseScore > 0) {
            result.addMatchReason("Partial expertise overlap with your goals");
        }

        // 4. Experience bonus (15 pts)
        double expScore = scoreExperience(mentor.getYearsOfExperience());
        total += expScore;
        if (mentor.getYearsOfExperience() >= 15) {
            result.addMatchReason("Highly experienced mentor (" + mentor.getYearsOfExperience() + " years)");
        } else if (mentor.getYearsOfExperience() >= 8) {
            result.addMatchReason("Well-experienced mentor (" + mentor.getYearsOfExperience() + " years)");
        }

        // Rating boost (up to 5 extra points)
        if (mentor.getRating() >= 4.5) {
            total = Math.min(100.0, total + 5.0);
            result.addMatchReason("Top-rated mentor (" + String.format("%.1f", mentor.getRating()) + "/5.0)");
        }

        result.setCompatibilityScore(total);
        return result;
    }

    private double scoreLocation(String entrepreneurLoc, String mentorLoc) {
        if (entrepreneurLoc == null || mentorLoc == null) return 0;
        String eLoc = entrepreneurLoc.toLowerCase().trim();
        String mLoc = mentorLoc.toLowerCase().trim();

        if (eLoc.equals(mLoc)) return LOCATION_WEIGHT;

        // Check if any word overlaps (e.g. same state)
        String[] eWords = eLoc.split("[,\\s]+");
        String[] mWords = mLoc.split("[,\\s]+");
        for (String ew : eWords) {
            for (String mw : mWords) {
                if (ew.length() > 3 && ew.equals(mw)) return LOCATION_WEIGHT * 0.6;
            }
        }
        return 0;
    }

    private double scoreExpertise(Entrepreneur entrepreneur, Mentor mentor) {
        if (mentor.getExpertiseAreas().isEmpty()) return 0;

        // Build a set of keywords from entrepreneur's goals + business description
        List<String> entrepreneurKeywords = new ArrayList<>(entrepreneur.getGoals());
        if (entrepreneur.getBusinessDescription() != null) {
            for (String word : entrepreneur.getBusinessDescription().toLowerCase().split("\\s+")) {
                if (word.length() > 4) entrepreneurKeywords.add(word);
            }
        }

        if (entrepreneurKeywords.isEmpty()) return EXPERTISE_WEIGHT * 0.3;

        int matches = 0;
        for (String expertise : mentor.getExpertiseAreas()) {
            for (String keyword : entrepreneurKeywords) {
                if (expertise.contains(keyword) || keyword.contains(expertise)) {
                    matches++;
                    break;
                }
            }
        }

        double ratio = (double) matches / mentor.getExpertiseAreas().size();
        return EXPERTISE_WEIGHT * Math.min(1.0, ratio);
    }

    private double scoreExperience(int years) {
        if (years >= 20) return EXPERIENCE_WEIGHT;
        if (years >= 15) return EXPERIENCE_WEIGHT * 0.85;
        if (years >= 10) return EXPERIENCE_WEIGHT * 0.70;
        if (years >= 5)  return EXPERIENCE_WEIGHT * 0.50;
        return EXPERIENCE_WEIGHT * 0.25;
    }
}
