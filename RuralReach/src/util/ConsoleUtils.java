package util;

import java.util.Scanner;

/**
 * Reusable CLI input/output helpers.
 */
public class ConsoleUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String message) {
        System.out.print("  " + message + ": ");
        return scanner.nextLine().trim();
    }

    public static int promptInt(String message, int min, int max) {
        while (true) {
            System.out.print("  " + message + " (" + min + "-" + max + "): ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
                System.out.println("  Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    public static void printDivider() {
        System.out.println("  ─────────────────────────────────────────");
    }

    public static void printHeader(String title) {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.printf ("  ║  %-40s║%n", title);
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    public static void printSuccess(String message) {
        System.out.println("  [OK] " + message);
    }

    public static void printError(String message) {
        System.out.println("  [ERROR] " + message);
    }

    public static void pressEnterToContinue() {
        System.out.print("\n  Press Enter to continue...");
        scanner.nextLine();
    }
}
