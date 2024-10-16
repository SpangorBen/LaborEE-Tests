package com.labor.laboreev2.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Recruiters")
@DiscriminatorValue("RECRUITER")
public class Recruiter extends User{

    public Recruiter() {
    }

    public Recruiter(String name, LocalDate birthDate, String socialSecurityNumber, String email, String password, String salt, String phone, LocalDate hireDate, Integer numOfChildren, Double salary , Address address) {
        super(name, birthDate, socialSecurityNumber, email, password, salt, phone, hireDate, numOfChildren, salary, address);
    }

    @Override
    public String toString() {
        return
                super.toString() +
                "Recruiter{}";
    }
}
