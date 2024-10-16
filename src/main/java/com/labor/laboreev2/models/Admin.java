package com.labor.laboreev2.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin() {
    }

    public Admin(String name, LocalDate birthDate, String socialSecurityNumber, String email, String phone, LocalDate hireDate, Integer numOfChildren, Double salary, Address address, String password, String salt) {
        super(name, birthDate, socialSecurityNumber, email, password, salt, phone, hireDate, numOfChildren, salary, address);
    }

    @Override
    public String toString() {
        return
                super.toString() +
                "Admin{}";
    }

}
