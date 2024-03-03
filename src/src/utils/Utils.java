package utils;
import classes.Project;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static constants.Constants.*;

public class Utils {

    public static Map<String, Map<String, Long>> calculatePairDurations(final List<Project> projects) {
        final Map<String, Map<String, Long>> pairDurations = new HashMap<>();

        for (int i = 0; i < projects.size(); i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                final Project p1 = projects.get(i);
                final Project p2 = projects.get(j);

                if (p1.getProjectId() == p2.getProjectId()) {
                    final int smallerEmployeeId = Math.min(p1.getEmployeeId(), p2.getEmployeeId());
                    final int largerEmployeeId = Math.max(p1.getEmployeeId(), p2.getEmployeeId());
                    if (smallerEmployeeId == largerEmployeeId) {
                        continue;
                    }
                    final String key = smallerEmployeeId + COMMA + largerEmployeeId;

                    final long duration = calculateDuration(p1.getDateFrom(), p1.getDateTo(), p2.getDateFrom(), p2.getDateTo());

                    pairDurations.putIfAbsent(key, new HashMap<>());
                    pairDurations.get(key).put(String.valueOf(p1.getProjectId()), duration);
                }
            }
        }

        return pairDurations;
    }

    public static Map.Entry<String, Map<String, Long>> findLongestPair(final Map<String, Map<String, Long>> pairDurations) {
        Map.Entry<String, Map<String, Long>> longestPair = null;
        long maxDuration = 0;

        for (Map.Entry<String, Map<String, Long>> entry : pairDurations.entrySet()) {
            final long entryMaxDuration = entry.getValue().values().stream().mapToLong(Long::longValue).max().orElse(0);
            if (longestPair == null || entryMaxDuration > maxDuration) {
                longestPair = entry;
                maxDuration = entryMaxDuration;
            }
        }

        return longestPair;
    }


    public static List<Project> readProjectsFromCSV() {
        final List<Project> projects = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] parts = line.split(SPACE_COMMAS);
                final LocalDate dateTo = parts[3].equals(NULL) ? LocalDate.now() : LocalDate.parse(parts[3]);
                projects.add(new Project(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), LocalDate.parse(parts[2]), dateTo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static List<Project> readProjectsFromCSV(final String filename) {
        final List<Project> projects = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(SPACE_COMMAS);
                LocalDate dateTo = parts[3].equals(NULL) ? LocalDate.now() : parseDate(parts[3]);
                projects.add(new Project(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), LocalDate.parse(parts[2]), dateTo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static JButton getSelectFileButton(final JFrame frame, final JTextArea textArea) {
        final JButton selectFileButton = new JButton(SELECT_CSV_FILE);
        selectFileButton.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            final int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                final String filename = fileChooser.getSelectedFile().getAbsolutePath();
                final List<Project> projects = readProjectsFromCSV(filename);
                if (!projects.isEmpty()) {
                    final Map<String, Map<String, Long>> pairDurations = Utils.calculatePairDurations(projects);
                    displayLongestPair(pairDurations, textArea);
                } else {
                    textArea.setText(NO_PROJECTS_FOUND);
                }
            }
        });
        return selectFileButton;
    }

    public static void displayLongestPair(final Map<String, Map<String, Long>> pairDurations, final JTextArea textArea) {
        final Map.Entry<String, Map<String, Long>> longestPair = Utils.findLongestPair(pairDurations);
        if (longestPair != null) {
            final String[] employees = longestPair.getKey().split(COMMA);
            final long maxDuration = longestPair.getValue().values().stream().mapToLong(Long::longValue).max().orElse(0);
            textArea.setText(employees[0] + SPACE_COMMAS + employees[1] + SPACE_COMMAS + formatDuration(maxDuration));
        } else {
            textArea.setText(NO_PAIR_FOUND);
        }
    }

    private static long calculateDuration(final LocalDate dateFrom1, final LocalDate dateTo1,
                                          final LocalDate dateFrom2, final LocalDate dateTo2) {
        final long duration1 = dateTo1.toEpochDay() - dateFrom1.toEpochDay();
        final long duration2 = dateTo2.toEpochDay() - dateFrom2.toEpochDay();
        return  duration1 + duration2;
    }

    //TODO In future fix multiformat dates
    public static LocalDate parseDate(final String dateString) {
        LocalDate date = null;
        final String[] formats = {"dd-MM-yyyy", "yyyy-MM-dd", "MM-dd-yyyy"};
        for (final String format : formats) {
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(format));
                break;
            } catch (DateTimeParseException e) {

            }
        }
        return date;
    }

    public static String formatDuration(final long totalDays) {
        final long years = totalDays / 365;
        final long months = (totalDays % 365) / 30;
        final long days = (totalDays % 365) % 30;

        return String.format(DISPLAY_FORMAT, years, months, days);
    }
}
