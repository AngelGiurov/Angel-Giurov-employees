public class EmployeePair{
    private final String employee1;
    private final String employee2;
    private final int duration;

    public EmployeePair(String employee1, String employee2, int duration) {
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.duration = duration;
    }

    public String getFirstEmployee() {
        return employee1;
    }

    public String getSecondEmployee() {
        return employee2;
    }

    public int getDuration() {
        return duration;
    }
}
