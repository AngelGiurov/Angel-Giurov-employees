import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        final List<Project> projects = readProjectsFromCSV();
        final Map<String, Map<String, Long>> pairDurations = Utils.calculatePairDurations(projects);

        Map.Entry<String, Map<String, Long>> longestPair = Utils.findLongestPair(pairDurations);
        if (longestPair != null) {
            final String[] employees = longestPair.getKey().split(",");
            final long maxDuration = longestPair.getValue().values().stream().mapToLong(Long::longValue).max().orElse(0);
            System.out.println(employees[0] + ", " + employees[1] + ", " + maxDuration);
        }
    }

    private static List<Project> readProjectsFromCSV() {
        final List<Project> projects = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new FileReader("input.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] parts = line.split(", ");
//                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final LocalDate dateTo = parts[3].equals("NULL") ? LocalDate.now() : LocalDate.parse(parts[3]);
                projects.add(new Project(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), LocalDate.parse(parts[2]), dateTo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }
}