package com.labor.laboreev2.services.interfaces;

import com.labor.laboreev2.models.Application;
import com.labor.laboreev2.models.JobOffer;
import com.labor.laboreev2.models.Recruiter;
import com.labor.laboreev2.models.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RecruiterService {
    JobOffer createJobOffer(JobOffer jobOffer);
    JobOffer updateJobOffer(JobOffer jobOffer);
    void deleteJobOffer(Long jobOfferId);
    JobOffer getJobOfferById(Long jobOfferId);
    List<JobOffer> getJobOffers();

    Recruiter getRecruiterById(Long recruiterId);

    List<Application> getApplicationsForJobOffer(Long jobOfferId);
    Application getApplicationById(Long applicationId);
    Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus);

    List<Application> filterApplicationsByStatus(ApplicationStatus status);
    List<Application> filterApplicationsBySkill(String skill);


}
