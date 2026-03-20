import model.Entrepreneur;
import model.Mentor;
import model.MatchResult;
import service.MatchingEngine;
import util.ConsoleUtils;
import util.FileManager;

import java.util.List;
import java.util.Scanner;

/**
 * RuralReach — Mentor Matching Platform for Rural Entrepreneurs
 * Entry point and main menu handler.
 */
public class Main {

    private static final FileManager   fileManager   = new FileManager();
    private static final MatchingEngine matchingEngine = new MatchingEngine();
    private static final Scanner        scanner        = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1" -> registerEntrepreneur();
                case "2" -> registerMentor();
                case "3" -> findMatches();
                case "4" -> browseMentors();
                case "5" -> viewEntrepreneurs();
                case "0" -> { running = false; printGoodbye(); }
                default  -> ConsoleUtils.printError("Invalid option. Please choose 0-5.");
            }
        }
    }

    // ── Banner & Menu ────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║           RURALREACH v1.0                    ║");
        System.out.println("  ║   Mentor Matching for Rural Entrepreneurs     ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("  ┌────────────────────────────────────────┐");
        System.out.println("  │               MAIN MENU                │");
        System.out.println("  ├────────────────────────────────────────┤");
        System.out.println("  │  1.  Register as Entrepreneur          │");
        System.out.println("  │  2.  Register as Mentor                │");
        System.out.println("  │  3.  Find Mentor Matches               │");
        System.out.println("  │  4.  Browse All Mentors                │");
        System.out.println("  │  5.  View All Entrepreneurs            │");
        System.out.println("  │  0.  Exit                              │");
        System.out.println("  └────────────────────────────────────────┘");
        System.out.print("  Choose an option: ");
    }

    // ── Option 1: Register Entrepreneur ─────────────────────

    private static void registerEntrepreneur() {
        ConsoleUtils.printHeader("Register as Entrepreneur");

        String name    = ConsoleUtils.prompt("Full name");
        String email   = ConsoleUtils.prompt("Email address");
        String location = ConsoleUtils.prompt("Location (city, state)");

        System.out.println("\n  Industries:");
        Entrepreneur.Industry[] industries = Entrepreneur.Industry.values();
        for (int i = 0; i < industries.length; i++) {
            System.out.printf("    %d. %s%n", i + 1, industries[i].display());
        }
        int industryChoice = ConsoleUtils.promptInt("Select industry", 1, industries.length);
        Entrepreneur.Industry industry = industries[industryChoice - 1];

        String businessDesc = ConsoleUtils.prompt("Describe your business in one line");
        String goalsInput   = ConsoleUtils.prompt("Your top goals (comma-separated, e.g. funding, marketing, export)");

        Entrepreneur entrepreneur = new Entrepreneur(
                fileManager.generateEntrepreneurId(),
                name, email, location, industry, businessDesc
        );
        for (String goal : goalsInput.split(",")) entrepreneur.addGoal(goal);

        fileManager.saveEntrepreneur(entrepreneur);

        System.out.println();
        ConsoleUtils.printSuccess("Profile saved! Your ID is " + entrepreneur.getId());
        ConsoleUtils.printSuccess("Use option 3 to find your mentor matches.");
        ConsoleUtils.pressEnterToContinue();
    }

    // ── Option 2: Register Mentor ────────────────────────────

    private static void registerMentor() {
        ConsoleUtils.printHeader("Register as Mentor");

        String name     = ConsoleUtils.prompt("Full name");
        String email    = ConsoleUtils.prompt("Email address");
        String location = ConsoleUtils.prompt("Location (city, state)");
        int    years    = ConsoleUtils.promptInt("Years of experience", 1, 50);
        String bio      = ConsoleUtils.prompt("Short professional bio (1-2 sentences)");
        String expertise = ConsoleUtils.prompt("Areas of expertise (comma-separated, e.g. marketing, export, finance)");

        System.out.println("\n  Select industries you mentor in (comma-separated numbers):");
        Entrepreneur.Industry[] industries = Entrepreneur.Industry.values();
        for (int i = 0; i < industries.length; i++) {
            System.out.printf("    %d. %s%n", i + 1, industries[i].display());
        }
        String industryChoices = ConsoleUtils.prompt("Industry numbers");

        Mentor mentor = new Mentor(
                fileManager.generateMentorId(),
                name, email, location, years, bio
        );
        for (String e : expertise.split(",")) mentor.addExpertise(e.trim());
        for (String idx : industryChoices.split(",")) {
            try {
                int i = Integer.parseInt(idx.trim()) - 1;
                if (i >= 0 && i < industries.length) mentor.addIndustry(industries[i]);
            } catch (NumberFormatException ignored) {}
        }

        fileManager.saveMentor(mentor);

        System.out.println();
        ConsoleUtils.printSuccess("Mentor profile saved! Your ID is " + mentor.getId());
        ConsoleUtils.pressEnterToContinue();
    }

    // ── Option 3: Find Matches ───────────────────────────────

    private static void findMatches() {
        ConsoleUtils.printHeader("Find Mentor Matches");

        List<Entrepreneur> entrepreneurs = fileManager.loadEntrepreneurs();
        if (entrepreneurs.isEmpty()) {
            ConsoleUtils.printError("No entrepreneur profiles found. Register first (option 1).");
            ConsoleUtils.pressEnterToContinue();
            return;
        }

        System.out.println("  Registered entrepreneurs:");
        for (int i = 0; i < entrepreneurs.size(); i++) {
            Entrepreneur e = entrepreneurs.get(i);
            System.out.printf("    %d. %s (%s) — %s%n", i + 1, e.getName(), e.getId(), e.getIndustry().display());
        }

        int choice = ConsoleUtils.promptInt("Select entrepreneur", 1, entrepreneurs.size());
        Entrepreneur selected = entrepreneurs.get(choice - 1);

        List<Mentor> mentors = fileManager.loadMentors();
        if (mentors.isEmpty()) {
            ConsoleUtils.printError("No mentors registered yet. Check back soon.");
            ConsoleUtils.pressEnterToContinue();
            return;
        }

        List<MatchResult> matches = matchingEngine.findMatches(selected, mentors);

        System.out.println();
        System.out.println("  Top mentor matches for " + selected.getName() + ":");
        ConsoleUtils.printDivider();
        System.out.printf("  %-4s %-20s %-15s %-8s %-10s %s%n",
                "Rank", "Name", "Location", "Score", "Grade", "Experience");
        ConsoleUtils.printDivider();

        for (int i = 0; i < matches.size(); i++) {
            System.out.println("  " + matches.get(i).toRankRow(i + 1));
        }

        ConsoleUtils.printDivider();
        System.out.print("\n  Enter rank number to view full profile (or 0 to go back): ");
        String input = scanner.nextLine().trim();
        try {
            int rank = Integer.parseInt(input);
            if (rank >= 1 && rank <= matches.size()) {
                System.out.println();
                System.out.println(matches.get(rank - 1).toDetailedReport());
            }
        } catch (NumberFormatException ignored) {}

        ConsoleUtils.pressEnterToContinue();
    }

    // ── Option 4: Browse Mentors ─────────────────────────────

    private static void browseMentors() {
        ConsoleUtils.printHeader("Browse All Mentors");

        List<Mentor> mentors = fileManager.loadMentors();
        if (mentors.isEmpty()) {
            ConsoleUtils.printError("No mentors registered yet.");
            ConsoleUtils.pressEnterToContinue();
            return;
        }

        for (int i = 0; i < mentors.size(); i++) {
            System.out.printf("%n  [%d]%n", i + 1);
            System.out.println(mentors.get(i).toProfileCard());
            ConsoleUtils.printDivider();
        }

        ConsoleUtils.pressEnterToContinue();
    }

    // ── Option 5: View Entrepreneurs ────────────────────────

    private static void viewEntrepreneurs() {
        ConsoleUtils.printHeader("Registered Entrepreneurs");

        List<Entrepreneur> list = fileManager.loadEntrepreneurs();
        if (list.isEmpty()) {
            ConsoleUtils.printError("No entrepreneurs registered yet.");
            ConsoleUtils.pressEnterToContinue();
            return;
        }

        ConsoleUtils.printDivider();
        System.out.printf("  %-6s %-20s %-20s %-15s%n", "ID", "Name", "Location", "Industry");
        ConsoleUtils.printDivider();
        for (Entrepreneur e : list) {
            System.out.printf("  %-6s %-20s %-20s %-15s%n",
                    e.getId(), e.getName(), e.getLocation(), e.getIndustry().display());
        }
        ConsoleUtils.printDivider();

        ConsoleUtils.pressEnterToContinue();
    }

    private static void printGoodbye() {
        System.out.println();
        System.out.println("  Thank you for using RuralReach.");
        System.out.println("  Empowering rural entrepreneurs, one connection at a time.");
        System.out.println();
    }
}
