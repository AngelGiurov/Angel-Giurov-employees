package constants;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String COMMA = ",";
    public static final String SPACE_COMMAS = ", ";
    public static final String UI_NAME = "Pair Durations Viewer";
    public static final String SELECT_CSV_FILE = "Select CSV File";
    public static final String CSV_FILE_NAME = "input.csv";
    public static final String NULL = "NULL";
    public static final String NO_PROJECTS_FOUND = "No projects found in the file.";
    public static final String NO_PAIR_FOUND = "No longest pair found.";
    public static final String[] formats = {"yyyy-MM-dd", "MM/dd/yyyy", "dd-MM-yyyy"};
    public static final String DISPLAY_FORMAT = "%d years, %d months, %d days";

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, MM-dd-yyyy, dd-MM-yyyy");
}
