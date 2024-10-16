package com.labor.laboreev2.models;

import com.labor.laboreev2.models.enums.JobOfferStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "JobOffers")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_offer_id")
    private Long jobOfferId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobOfferStatus status;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "validity_duration_days", nullable = false)
    private Integer validityDurationDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiter recruiter;


    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    public JobOffer() {
    }

    public JobOffer(String title, String description, Department department, JobTitle jobTitle, JobOfferStatus status, LocalDate publicationDate, Integer validityDurationDays, Recruiter recruiter) {
        this.title = title;
        this.description = description;
        this.department = department;
        this.jobTitle = jobTitle;
        this.status = status;
        this.publicationDate = publicationDate;
        this.validityDurationDays = validityDurationDays;
        this.recruiter = recruiter;
    }

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public JobOfferStatus getStatus() {
        return status;
    }

    public void setStatus(JobOfferStatus status) {
        this.status = status;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getValidityDurationDays() {
        return validityDurationDays;
    }

    public void setValidityDurationDays(Integer validityDurationDays) {
        this.validityDurationDays = validityDurationDays;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
