import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {

    protected static Map<String, Map<String, Long>> calculatePairDurations(final List<Project> projects) {
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
                    final String key = smallerEmployeeId + "," + largerEmployeeId;

                    final long duration = calculateDuration(p1.getDateFrom(), p1.getDateTo(), p2.getDateFrom(), p2.getDateTo());

                    pairDurations.putIfAbsent(key, new HashMap<>());
                    pairDurations.get(key).put(String.valueOf(p1.getProjectId()), duration);
                }
            }
        }

        return pairDurations;
    }

    protected static Map.Entry<String, Map<String, Long>> findLongestPair(final Map<String, Map<String, Long>> pairDurations) {
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

    private static long calculateDuration(LocalDate dateFrom1, LocalDate dateTo1, LocalDate dateFrom2, LocalDate dateTo2) {
        long duration1 = dateTo1.toEpochDay() - dateFrom1.toEpochDay();
        long duration2 = dateTo2.toEpochDay() - dateFrom2.toEpochDay();
        double years1 = TimeUnit.DAYS.toDays(duration1) / 365.25;
        double years2 = TimeUnit.DAYS.toDays(duration2) / 365.25;
        double absoluteYears = Math.abs(years1 + years2);
        return Math.round(absoluteYears);
    }
}
