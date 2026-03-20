package util;

import model.Entrepreneur;
import model.Mentor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all CSV read/write operations.
 * Files are stored in the data/ directory relative to the project root.
 */
public class FileManager {

    private static final String DATA_DIR         = "data/";
    private static final String MENTORS_FILE      = DATA_DIR + "mentors.csv";
    private static final String ENTREPRENEURS_FILE = DATA_DIR + "entrepreneurs.csv";

    // ── Mentors ──────────────────────────────────────────────

    public List<Mentor> loadMentors() {
        List<Mentor> mentors = new ArrayList<>();
        File file = new File(MENTORS_FILE);
        if (!file.exists()) return mentors;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                if (line.isBlank()) continue;
                Mentor m = Mentor.fromCsvRow(line);
                if (m != null) mentors.add(m);
            }
        } catch (IOException e) {
            System.err.println("Error loading mentors: " + e.getMessage());
        }
        return mentors;
    }

    public void saveMentor(Mentor mentor) {
        ensureDataDir();
        File file = new File(MENTORS_FILE);
        boolean needsHeader = !file.exists() || file.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (needsHeader) writer.write(Mentor.csvHeader() + "\n");
            writer.write(mentor.toCsvRow() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving mentor: " + e.getMessage());
        }
    }

    // ── Entrepreneurs ────────────────────────────────────────

    public List<Entrepreneur> loadEntrepreneurs() {
        List<Entrepreneur> list = new ArrayList<>();
        File file = new File(ENTREPRENEURS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                if (line.isBlank()) continue;
                Entrepreneur e = Entrepreneur.fromCsvRow(line);
                if (e != null) list.add(e);
            }
        } catch (IOException e) {
            System.err.println("Error loading entrepreneurs: " + e.getMessage());
        }
        return list;
    }

    public void saveEntrepreneur(Entrepreneur entrepreneur) {
        ensureDataDir();
        File file = new File(ENTREPRENEURS_FILE);
        boolean needsHeader = !file.exists() || file.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (needsHeader) writer.write(Entrepreneur.csvHeader() + "\n");
            writer.write(entrepreneur.toCsvRow() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving entrepreneur: " + e.getMessage());
        }
    }

    // ── ID generation ────────────────────────────────────────

    public String generateMentorId() {
        return "M" + String.format("%03d", loadMentors().size() + 1);
    }

    public String generateEntrepreneurId() {
        return "E" + String.format("%03d", loadEntrepreneurs().size() + 1);
    }

    // ── Utilities ────────────────────────────────────────────

    private void ensureDataDir() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }
}
