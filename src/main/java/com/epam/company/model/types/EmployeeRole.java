package com.epam.company.model.types;

public enum EmployeeRole {
    UNKNOWN(-1),
    EMPTY(0),
    NOT_EMPLOYEE(-2),
    BUISNESS_MANAGER(1),
    PROJECT_MANAGER(2),
    TEAM_LEAD(3),
    DEVELOPER(4);

    private final int employeeProfileType;

    private EmployeeRole(int employeeProfileType){
        this.employeeProfileType = employeeProfileType;
    }

    public int getEmployeeProfileType(){
        return employeeProfileType;
    }
}
