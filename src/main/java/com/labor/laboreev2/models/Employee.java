package com.labor.laboreev2.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Employees")
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobTitleId", nullable = false)
    private JobTitle jobTitle;

    public Employee() {
    }

    public Employee(String name, LocalDate birthDate, String socialSecurityNumber, String email, String password, String salt, String phone, LocalDate hireDate, Integer numOfChildren, Double salary, Address address, Department department, JobTitle jobTitle) {
        super(name, birthDate, socialSecurityNumber, email, password, salt, phone, hireDate, numOfChildren, salary, address);
        this.department = department;
        this.jobTitle = jobTitle;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return
                super.toString() +
                "Employee{" +
                "department=" + department +
                ", jobTitle=" + jobTitle +
                '}';
    }
}
