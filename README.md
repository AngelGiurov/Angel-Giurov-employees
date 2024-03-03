# Longest Employees That Worked Together Identifier

This application is designed to identify the pair of employees who have worked together on common projects for the longest period of time. Running the program in the `Main` method will process the provided `input.csv` file located in the project directory. Alternatively, executing `MainBonusTask` will launch a basic UI interface allowing you to import any CSV file and retrieve the desired output.

## CSV File Format
- The desired CSV file must be formatted as follows:
    - `EmployeeID, ProjectID, Project DateFrom, Project DateTo`
- **Note for Dates:** Currently, only dates in the format `yyyy-MM-dd` are supported due to limitations with the `LocalDate` object.

## Output Example
- The output example is as follows:
    - `EmployeeOneID`, `EmployeeTwoID`, `duration of time worked together`
- The duration of time worked together is formatted as `% years, % months, % days`
