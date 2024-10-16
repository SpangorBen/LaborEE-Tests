package com.labor.laboreev2.models;

import com.labor.laboreev2.models.enums.ApplicationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", nullable = false)
    private JobOffer jobOffer;

    @Column(name = "applicant", nullable = false)
    private String applicant;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "resume_url", nullable = false)
    private String resumeUrl;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "skills")
    private String skills;

    public Application() {
    }

    public Application(JobOffer jobOffer, String applicant, String email, LocalDate applicationDate, String resumeUrl, String coverLetter, ApplicationStatus status, String skills) {
        this.jobOffer = jobOffer;
        this.applicant = applicant;
        this.email = email;
        this.applicationDate = applicationDate;
        this.resumeUrl = resumeUrl;
        this.coverLetter = coverLetter;
        this.status = status;
        this.skills = skills;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
