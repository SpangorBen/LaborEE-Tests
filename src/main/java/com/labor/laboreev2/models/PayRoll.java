package com.labor.laboreev2.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "PayRolls")
public class PayRoll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id")
    private Long payrollId;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "family_allowance", nullable = false)
    private Double familyAllowance;

    @Column(name = "total_pay", nullable = false)
    private Double totalPay;

    @Column(name = "generated_at", nullable = false)
    private Date generatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PayRoll() {
    }

    public PayRoll(Double salary, Double familyAllowance, Double totalPay, Date generatedAt, User user) {
        this.salary = salary;
        this.familyAllowance = familyAllowance;
        this.totalPay = totalPay;
        this.generatedAt = generatedAt;
        this.user = user;
    }

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getFamilyAllowance() {
        return familyAllowance;
    }

    public void setFamilyAllowance(Double familyAllowance) {
        this.familyAllowance = familyAllowance;
    }

    public Double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Double totalPay) {
        this.totalPay = totalPay;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
