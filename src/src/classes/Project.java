package classes;
import java.time.LocalDate;
import java.util.Objects;

public class Project {
    private final int employeeId;
    private final int projectId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Project(int employeeId, int projectId, LocalDate dateFrom, LocalDate endDate) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.startDate = dateFrom;
        this.endDate = endDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public LocalDate getDateFrom() {
        return startDate;
    }

    public LocalDate getDateTo() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return getEmployeeId() == project.getEmployeeId() && getProjectId() == project.getProjectId() && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getProjectId(), startDate, endDate);
    }
}
