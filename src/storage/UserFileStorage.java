package storage;

import model.Admin;
import model.Student;
import model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete storage implementation for User records persisted in a CSV file.
 * 
 * OOP Concepts Demonstrated:
 * 1. Interface Implementation: Implements Storage<User>.
 * 2. Polymorphism: Reads base User records and instantiates Student or Admin polymorphically.
 * 3. Exception Handling: Employs try-with-resources for automatic resource management.
 * 4. File I/O: Plain text / CSV reading and writing via java.io.
 */
public class UserFileStorage implements Storage<User> {
    private final String filePath;

    public UserFileStorage(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    public UserFileStorage() {
        this("data/users.csv");
    }

    private void ensureFileExists() {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not create user storage file: " + e.getMessage());
        }
    }

    @Override
    public void save(List<User> items) throws IOException {
        // Try-with-resources ensures BufferedWriter is automatically closed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Write CSV header
            writer.write("id,username,password,fullName,role,extraField");
            writer.newLine();

            for (User user : items) {
                String extra = "";
                if (user instanceof Student) {
                    extra = ((Student) user).getRollNumber();
                } else if (user instanceof Admin) {
                    extra = ((Admin) user).getDepartment();
                }

                String line = String.format("%s,%s,%s,%s,%s,%s",
                        escapeCsv(user.getId()),
                        escapeCsv(user.getUsername()),
                        escapeCsv(user.getPassword()),
                        escapeCsv(user.getFullName()),
                        escapeCsv(user.getRole()),
                        escapeCsv(extra));
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public List<User> load() throws IOException {
        List<User> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return list;
        }

        // Try-with-resources with BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = parseCsvLine(line);
                if (parts.length >= 5) {
                    String id = parts[0];
                    String username = parts[1];
                    String password = parts[2];
                    String fullName = parts[3];
                    String role = parts[4];
                    String extra = parts.length > 5 ? parts[5] : "";

                    // Polymorphic instantiation based on role
                    if ("Student".equalsIgnoreCase(role)) {
                        list.add(new Student(id, username, password, fullName, extra));
                    } else if ("Admin".equalsIgnoreCase(role)) {
                        list.add(new Admin(id, username, password, fullName, extra));
                    }
                }
            }
        }
        return list;
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String[] parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    sb.append('\"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString().trim());
        return tokens.toArray(new String[0]);
    }
}

