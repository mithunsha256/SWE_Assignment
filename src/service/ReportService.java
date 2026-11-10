package service;

import model.Feedback;
import model.FeedbackTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for computing analytics and generating analytical feedback reports.
 * 
 * OOP Concepts Demonstrated:
 * 1. Interface Implementation: Implements Reportable interface (Polymorphism).
 * 2. Method Overloading: generateReport() and generateReport(String targetType).
 * 3. Collections (HashMap, ArrayList): Aggregates ratings per target in Maps.
 * 4. Comparator & Sorting: Sorts target statistics to find highest and lowest rated targets.
 */
public class ReportService implements Reportable {
    private final FeedbackService feedbackService;

    public ReportService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Inner helper class representing aggregated statistics for a specific target.
     * Demonstrates Encapsulation.
     */
    public static class TargetStat {
        private final String targetId;
        private final String targetName;
        private final String targetType;
        private int count = 0;
        private int ratingSum = 0;

        public TargetStat(String targetId, String targetName, String targetType) {
            this.targetId = targetId;
            this.targetName = targetName;
            this.targetType = targetType;
        }

        public void addRating(int rating) {
            this.count++;
            this.ratingSum += rating;
        }

        public double getAverageRating() {
            return count == 0 ? 0.0 : (double) ratingSum / count;
        }

        public int getCount() {
            return count;
        }

        public String getTargetName() {
            return targetName;
        }

        public String getTargetType() {
            return targetType;
        }

        public String getTargetId() {
            return targetId;
        }
    }

    /**
     * Implementation of Reportable interface.
     * Generates a comprehensive summary report across all targets.
     */
    @Override
    public String generateReport() {
        return generateReport(null);
    }

    /**
     * Method Overloading:
     * Overloaded generateReport(targetType) filters results by a specific target type.
     */
    public String generateReport(String filterTargetType) {
        List<Feedback> feedbacks = filterTargetType == null 
                ? feedbackService.getAllFeedback() 
                : feedbackService.getFeedbackByTargetType(filterTargetType);

        List<FeedbackTarget> targets = filterTargetType == null
                ? feedbackService.getPredefinedTargets()
                : feedbackService.getTargetsByType(filterTargetType);

        StringBuilder sb = new StringBuilder();
        String title = filterTargetType == null ? "CAMPUS FEEDBACK EXECUTIVE SUMMARY" : filterTargetType.toUpperCase() + " FEEDBACK REPORT";
        sb.append("========================================================================================\n");
        sb.append(String.format("                             %s\n", title));
        sb.append("========================================================================================\n");

        int totalCount = feedbacks.size();
        if (totalCount == 0) {
            sb.append("\n  No feedback entries have been recorded yet.\n");
            sb.append("========================================================================================\n");
            return sb.toString();
        }

        int totalRatingSum = 0;
        for (Feedback fb : feedbacks) {
            totalRatingSum += fb.getRating();
        }
        double overallAverage = (double) totalRatingSum / totalCount;

        sb.append(String.format("  * Total Feedback Submissions : %d\n", totalCount));
        sb.append(String.format("  * Overall Average Rating     : %.2f / 5.00 (%s)\n", 
                overallAverage, getStarBar(overallAverage)));
        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append("  BREAKDOWN PER TARGET:\n");
        sb.append(String.format("  %-10s | %-32s | %-10s | %-8s | %-12s\n", 
                "Type", "Target Name", "ID", "Count", "Avg Rating"));
        sb.append("  --------------------------------------------------------------------------------------\n");

        // Map to aggregate stats per targetId
        Map<String, TargetStat> statMap = new HashMap<>();
        for (FeedbackTarget t : targets) {
            statMap.put(t.getId(), new TargetStat(t.getId(), t.getName(), t.getTargetType()));
        }

        for (Feedback fb : feedbacks) {
            TargetStat stat = statMap.get(fb.getTargetId());
            if (stat != null) {
                stat.addRating(fb.getRating());
            }
        }

        List<TargetStat> statsList = new ArrayList<>(statMap.values());

        // Sort alphabetically by target name for consistent display
        Collections.sort(statsList, new Comparator<TargetStat>() {
            @Override
            public int compare(TargetStat s1, TargetStat s2) {
                return s1.getTargetName().compareToIgnoreCase(s2.getTargetName());
            }
        });

        for (TargetStat stat : statsList) {
            String avgStr = stat.getCount() > 0 ? String.format("%.2f / 5.0", stat.getAverageRating()) : "N/A (0)";
            sb.append(String.format("  %-10s | %-32s | %-10s | %-8d | %-12s\n",
                    stat.getTargetType(),
                    truncate(stat.getTargetName(), 32),
                    stat.getTargetId(),
                    stat.getCount(),
                    avgStr));
        }

        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append("  TARGET PERFORMANCE HIGHLIGHTS:\n");

        // Find targets with at least 1 rating to determine highest and lowest
        List<TargetStat> ratedStats = new ArrayList<>();
        for (TargetStat stat : statsList) {
            if (stat.getCount() > 0) {
                ratedStats.add(stat);
            }
        }

        if (ratedStats.isEmpty()) {
            sb.append("  No rated targets available yet.\n");
        } else {
            // Sort by average rating descending using Comparator
            Collections.sort(ratedStats, new Comparator<TargetStat>() {
                @Override
                public int compare(TargetStat o1, TargetStat o2) {
                    int cmp = Double.compare(o2.getAverageRating(), o1.getAverageRating());
                    if (cmp != 0) return cmp;
                    return Integer.compare(o2.getCount(), o1.getCount()); // tie-break with count
                }
            });

            TargetStat highest = ratedStats.get(0);
            TargetStat lowest = ratedStats.get(ratedStats.size() - 1);

            sb.append(String.format("  [+] HIGHEST RATED : %s (%s) - %.2f / 5.00 (%d review%s)\n",
                    highest.getTargetName(), highest.getTargetType(), 
                    highest.getAverageRating(), highest.getCount(), highest.getCount() > 1 ? "s" : ""));
            sb.append(String.format("  [-] LOWEST RATED  : %s (%s) - %.2f / 5.00 (%d review%s)\n",
                    lowest.getTargetName(), lowest.getTargetType(), 
                    lowest.getAverageRating(), lowest.getCount(), lowest.getCount() > 1 ? "s" : ""));
        }
        sb.append("========================================================================================\n");

        return sb.toString();
    }

    private String getStarBar(double avg) {
        int stars = (int) Math.round(avg);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 1; i <= 5; i++) {
            sb.append(i <= stars ? "*" : "-");
        }
        sb.append("]");
        return sb.toString();
    }

    private String truncate(String text, int max) {
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}
