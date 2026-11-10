package service;

/**
 * Interface defining contract for generating reports.
 * 
 * OOP Concepts Demonstrated:
 * 1. Interface & Abstraction: Standard contract for analytical summary output.
 * 2. Polymorphism: Different report generators implement generateReport() differently.
 */
public interface Reportable {
    /**
     * Generates a formatted report string.
     * @return structured report text
     */
    String generateReport();
}

