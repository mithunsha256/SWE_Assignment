package util;

import java.util.Scanner;

/**
 * Helper class for safe, interactive console input handling.
 * Prevents scanner buffer issues and manages input parsing.
 * 
 * OOP Concepts Demonstrated:
 * 1. Encapsulation: Wraps System.in Scanner inside a helper.
 * 2. Exception Handling: Catches NumberFormatException and handles invalid menu choices.
 */
public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads a required non-empty line of text.
     */
    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("  [!] Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads an integer within an inclusive range [min, max].
     */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                int value = Integer.parseInt(line.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("  [!] Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid number format. Please enter a valid integer.");
            }
        }
    }

    /**
     * Pauses the console and waits for the user to press Enter.
     */
    public void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}

