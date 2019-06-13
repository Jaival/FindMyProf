package com.jaivalsaija.findmyprof.data;

public class ProfessorData {

    private String Name;
    private String Available;
    private String EmployeeId;

    public ProfessorData(String name, String available, String employeeId) {
        Name = name;
        Available = available;
        EmployeeId = employeeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }
}
