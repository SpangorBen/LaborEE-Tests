package com.labor.laboreev2.services;

import com.labor.laboreev2.models.*;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.repositories.interfaces.ApplicationRepository;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import com.labor.laboreev2.repositories.interfaces.RecruiterRepository;
import com.labor.laboreev2.services.interfaces.RecruiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecruiterServiceImplTest {

    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private RecruiterRepository recruiterRepository;

    @InjectMocks
    private RecruiterServiceImpl recruiterService;

    private JobOffer jobOffer;
    private Application application;
    private Recruiter recruiter;

    @BeforeEach
    void setUp() {
        jobOffer = new JobOffer();
        jobOffer.setJobOfferId(1L);
        jobOffer.setTitle("Software Engineer");

        application = new Application();
        application.setApplicationId(1L);
        application.setJobOffer(jobOffer);
        application.setStatus(ApplicationStatus.SUBMITTED);

        recruiter = new Recruiter();
        recruiter.setId(1L);
        recruiter.setName("John Doe");
    }

    @Test
    void createJobOffer_shouldReturnSavedJobOffer() {
        when(jobOfferRepository.save(any(JobOffer.class))).thenReturn(jobOffer);

        JobOffer savedJobOffer = recruiterService.createJobOffer(jobOffer);

        assertNotNull(savedJobOffer);
        assertEquals(jobOffer.getJobOfferId(), savedJobOffer.getJobOfferId());
        verify(jobOfferRepository, times(1)).save(jobOffer);
    }

    @Test
    void updateJobOffer_shouldReturnUpdatedJobOffer() {
        when(jobOfferRepository.save(any(JobOffer.class))).thenReturn(jobOffer);

        JobOffer updatedJobOffer = recruiterService.updateJobOffer(jobOffer);

        assertNotNull(updatedJobOffer);
        assertEquals(jobOffer.getJobOfferId(), updatedJobOffer.getJobOfferId());
        verify(jobOfferRepository, times(1)).save(jobOffer);
    }

    @Test
    void deleteJobOffer_shouldCallRepositoryDeleteById() {
        doNothing().when(jobOfferRepository).deleteById(jobOffer.getJobOfferId());

        recruiterService.deleteJobOffer(jobOffer.getJobOfferId());

        verify(jobOfferRepository, times(1)).deleteById(jobOffer.getJobOfferId());
    }

    @Test
    void getJobOfferById_shouldReturnJobOffer() {
        when(jobOfferRepository.findById(jobOffer.getJobOfferId())).thenReturn(Optional.of(jobOffer));

        JobOffer foundJobOffer = recruiterService.getJobOfferById(jobOffer.getJobOfferId());

        assertNotNull(foundJobOffer);
        assertEquals(jobOffer.getJobOfferId(), foundJobOffer.getJobOfferId());
        verify(jobOfferRepository, times(1)).findById(jobOffer.getJobOfferId());
    }

    @Test
    void getJobOffers_shouldReturnListOfJobOffers() {
        when(jobOfferRepository.findAll()).thenReturn(List.of(jobOffer));

        List<JobOffer> jobOffers = recruiterService.getJobOffers();

        assertNotNull(jobOffers);
        assertEquals(1, jobOffers.size());
        assertEquals(jobOffer.getJobOfferId(), jobOffers.get(0).getJobOfferId());
        verify(jobOfferRepository, times(1)).findAll();
    }

    @Test
    void getRecruiterById_shouldReturnRecruiter() {
        when(recruiterRepository.findById(recruiter.getId())).thenReturn(recruiter);

        Recruiter foundRecruiter = recruiterService.getRecruiterById(recruiter.getId());

        assertNotNull(foundRecruiter);
        assertEquals(recruiter.getId(), foundRecruiter.getId());
        verify(recruiterRepository, times(1)).findById(recruiter.getId());
    }

    @Test
    void updateApplicationStatus_shouldReturnUpdatedApplication() {
        when(applicationRepository.findById(application.getApplicationId())).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application updatedApplication = recruiterService.updateApplicationStatus(application.getApplicationId(), ApplicationStatus.ACCEPTED);

        assertNotNull(updatedApplication);
        assertEquals(ApplicationStatus.ACCEPTED, updatedApplication.getStatus());
        verify(applicationRepository, times(1)).save(application);
    }

    @Test
    void getApplicationsForJobOffer_shouldReturnListOfApplications() {
        when(applicationRepository.findByJobOffer(jobOffer.getJobOfferId())).thenReturn(List.of(application));

        List<Application> applications = recruiterService.getApplicationsForJobOffer(jobOffer.getJobOfferId());

        assertNotNull(applications);
        assertEquals(1, applications.size());
        assertEquals(application.getApplicationId(), applications.get(0).getApplicationId());
        verify(applicationRepository, times(1)).findByJobOffer(jobOffer.getJobOfferId());
    }

    @Test
    void getApplicationById_shouldReturnApplication() {
        when(applicationRepository.findById(application.getApplicationId())).thenReturn(Optional.of(application));

        Application foundApplication = recruiterService.getApplicationById(application.getApplicationId());

        assertNotNull(foundApplication);
        assertEquals(application.getApplicationId(), foundApplication.getApplicationId());
        verify(applicationRepository, times(1)).findById(application.getApplicationId());
    }

    @Test
    void filterApplicationsByStatus_shouldReturnFilteredApplications() {
        when(applicationRepository.findByStatus(ApplicationStatus.SUBMITTED)).thenReturn(List.of(application));

        List<Application> applications = recruiterService.filterApplicationsByStatus(ApplicationStatus.SUBMITTED);

        assertNotNull(applications);
        assertEquals(1, applications.size());
        assertEquals(application.getApplicationId(), applications.get(0).getApplicationId());
        verify(applicationRepository, times(1)).findByStatus(ApplicationStatus.SUBMITTED);
    }

    @Test
    void filterApplicationsBySkill_shouldReturnFilteredApplications() {
        String skill = "Java";
        when(applicationRepository.findBySkill(skill)).thenReturn(List.of(application));

        List<Application> applications = recruiterService.filterApplicationsBySkill(skill);

        assertNotNull(applications);
        assertEquals(1, applications.size());
        assertEquals(application.getApplicationId(), applications.get(0).getApplicationId());
        verify(applicationRepository, times(1)).findBySkill(skill);
    }
}
