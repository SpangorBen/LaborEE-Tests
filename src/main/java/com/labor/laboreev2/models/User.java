package com.labor.laboreev2.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthd_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "social_security_number", nullable = false, unique = true)
    private String socialSecurityNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "num_of_children")
    private Integer numOfChildren;

    @Column(name = "salary")
    private Double salary;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeaveRequest> leaveRequests;

    public User() {
    }

    public User(String name, LocalDate birthDate, String socialSecurityNumber, String email, String password, String salt, String phone, LocalDate hireDate, Integer numOfChildren, Double salary, Address address, List<LeaveRequest> leaveRequests) {
        this.name = name;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.phone = phone;
        this.hireDate = hireDate;
        this.numOfChildren = numOfChildren;
        this.salary = salary;
        this.address = address;
        this.leaveRequests = leaveRequests;
    }

    public User(String name, LocalDate birthDate, String socialSecurityNumber, String email, String password, String salt, String phone, LocalDate hireDate, Integer numOfChildren, Double salary, Address address) {
        this.name = name;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.phone = phone;
        this.hireDate = hireDate;
        this.numOfChildren = numOfChildren;
        this.salary = salary;
        this.address = address;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate=" + hireDate +
                ", numOfChildren=" + numOfChildren +
                ", salary=" + salary +
                ", address=" + address +
                ", leaveRequests=" + leaveRequests +
                '}';
    }
}
