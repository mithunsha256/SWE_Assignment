package storage;

import model.Feedback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete storage implementation for Feedback records persisted in a CSV file.
 * 
 * OOP Concepts Demonstrated:
 * 1. Interface Implementation: Second concrete class implementing Storage<T> (Polymorphism).
 * 2. File I/O: Plain text / CSV file operations using BufferedReader and BufferedWriter.
 * 3. Exception Handling: Employs try-with-resources for deterministic stream closure.
 */
public class FeedbackFileStorage implements Storage<Feedback> {
    private final String filePath;

    public FeedbackFileStorage(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    public FeedbackFileStorage() {
        this("data/feedback.csv");
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
            System.err.println("Warning: Could not create feedback storage file: " + e.getMessage());
        }
    }

    @Override
    public void save(List<Feedback> items) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Write CSV header
            writer.write("feedbackId,studentUsername,targetType,targetId,targetName,rating,comment,timestamp");
            writer.newLine();

            for (Feedback fb : items) {
                String line = String.format("%s,%s,%s,%s,%s,%d,%s,%s",
                        escapeCsv(fb.getFeedbackId()),
                        escapeCsv(fb.getStudentUsername()),
                        escapeCsv(fb.getTargetType()),
                        escapeCsv(fb.getTargetId()),
                        escapeCsv(fb.getTargetName()),
                        fb.getRating(),
                        escapeCsv(fb.getComment()),
                        escapeCsv(fb.getTimestamp().format(Feedback.FORMATTER)));
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public List<Feedback> load() throws IOException {
        List<Feedback> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = parseCsvLine(line);
                if (parts.length >= 8) {
                    String feedbackId = parts[0];
                    String studentUsername = parts[1];
                    String targetType = parts[2];
                    String targetId = parts[3];
                    String targetName = parts[4];
                    int rating = Integer.parseInt(parts[5]);
                    String comment = parts[6];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[7], Feedback.FORMATTER);

                    list.add(new Feedback(feedbackId, studentUsername, targetType, 
                            targetId, targetName, rating, comment, timestamp));
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

