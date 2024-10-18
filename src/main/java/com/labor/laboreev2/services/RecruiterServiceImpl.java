package com.labor.laboreev2.services;

import com.labor.laboreev2.models.*;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.repositories.interfaces.ApplicationRepository;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import com.labor.laboreev2.repositories.interfaces.RecruiterRepository;
import com.labor.laboreev2.services.interfaces.RecruiterService;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RecruiterServiceImpl implements RecruiterService {

    private final Logger logger = Logger.getLogger(RecruiterServiceImpl.class.getName());

    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final RecruiterRepository recruiterRepository;

    public RecruiterServiceImpl(JobOfferRepository jobOfferRepository, ApplicationRepository applicationRepository, RecruiterRepository recruiterRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.applicationRepository = applicationRepository;
        this.recruiterRepository = recruiterRepository;
    }

    @Override
    @Transactional
    public JobOffer createJobOffer(JobOffer jobOffer) {
        try {
            return jobOfferRepository.save(jobOffer);
        } catch (Exception e) {
            logger.severe("Failed to create job offer: " + e.getMessage());
            throw new RuntimeException("Failed to create job offer", e);
        }
    }

    @Override
    @Transactional
    public JobOffer updateJobOffer(JobOffer jobOffer) {
        try {
            return jobOfferRepository.save(jobOffer);
        } catch (Exception e) {
            logger.severe("Failed to update job offer: " + e.getMessage());
            throw new RuntimeException("Failed to update job offer", e);
        }
    }

    @Override
    @Transactional
    public void deleteJobOffer(Long jobOfferId) {
        try {
            jobOfferRepository.deleteById(jobOfferId);
        } catch (Exception e) {
            logger.severe("Failed to delete job offer: " + e.getMessage());
            throw new RuntimeException("Failed to delete job offer", e);
        }
    }

    @Override
    public JobOffer getJobOfferById(Long jobOfferId) {
        try {
            return jobOfferRepository.findById(jobOfferId).orElseThrow(() -> new RuntimeException("Job offer not found"));
        } catch (Exception e) {
            logger.severe("Failed to find job offer by id: " + e.getMessage());
            throw new RuntimeException("Failed to find job offer by id", e);
        }
    }

    @Override
    public List<JobOffer> getJobOffers() {
        try {
            return jobOfferRepository.findAll();
        } catch (Exception e) {
            logger.severe("Failed to get job offers: " + e.getMessage());
            throw new RuntimeException("Failed to get job offers", e);
        }
    }

    @Override
    public Recruiter getRecruiterById(Long recruiterId) {
        try {
            return recruiterRepository.findById(recruiterId);
        } catch (Exception e) {
            logger.severe("Failed to find recruiter by id: " + e.getMessage());
            throw new RuntimeException("Failed to find recruiter by id", e);
        }
    }

    @Override
    public List<Application> getApplicationsForJobOffer(Long jobOfferId) {
        try {
            return applicationRepository.findByJobOffer(jobOfferId);
        } catch (Exception e) {
            logger.severe("Failed to get applications for job offer: " + e.getMessage());
            throw new RuntimeException("Failed to get applications for job offer", e);
        }
    }

    @Override
    public Application getApplicationById(Long applicationId) {
        try {
            return applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));
        } catch (Exception e) {
            logger.severe("Failed to find application by id: " + e.getMessage());
            throw new RuntimeException("Failed to find application by id", e);
        }
    }

    @Override
    @Transactional
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        try {
            Application application = getApplicationById(applicationId);
            application.setStatus(newStatus);
            return applicationRepository.save(application);
        } catch (Exception e) {
            logger.severe("Failed to update application status: " + e.getMessage());
            throw new RuntimeException("Failed to update application status", e);
        }
    }

    @Override
    public List<Application> filterApplicationsByStatus(ApplicationStatus status) {
        try {
            return applicationRepository.findByStatus(status);
        } catch (Exception e) {
            logger.severe("Failed to filter applications by status: " + e.getMessage());
            throw new RuntimeException("Failed to filter applications by status", e);
        }
    }

    @Override
    public List<Application> filterApplicationsBySkill(String skill) {
        try {
            return applicationRepository.findBySkill(skill);
        } catch (Exception e) {
            logger.severe("Failed to filter applications by skill: " + e.getMessage());
            throw new RuntimeException("Failed to filter applications by skill", e);
        }
    }

}
