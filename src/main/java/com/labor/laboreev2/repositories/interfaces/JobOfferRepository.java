package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.JobOffer;

import java.util.List;
import java.util.Optional;

public interface JobOfferRepository {
    JobOffer save(JobOffer jobOffer);
    Optional<JobOffer> findById(Long id);
    List<JobOffer> findAll();
    void deleteById(Long id);
}
