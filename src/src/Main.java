import classes.Project;
import java.util.*;
import static constants.Constants.*;
import static utils.Utils.*;

public class Main {
    public static void main(String[] args) {
        final List<Project> projects = readProjectsFromCSV();
        final Map<String, Map<String, Long>> pairDurations = calculatePairDurations(projects);
        final Map.Entry<String, Map<String, Long>> longestPair = findLongestPair(pairDurations);
        if (longestPair != null) {
            final String[] employees = longestPair.getKey().split(COMMA);
            final long maxDuration = longestPair.getValue().values().stream().mapToLong(Long::longValue).max().orElse(0);
            System.out.println(employees[0] + SPACE_COMMAS + employees[1] + SPACE_COMMAS + formatDuration(maxDuration));
        }
    }
}