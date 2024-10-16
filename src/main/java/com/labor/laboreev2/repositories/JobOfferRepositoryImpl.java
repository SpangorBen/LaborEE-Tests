package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.JobOffer;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class JobOfferRepositoryImpl implements JobOfferRepository {

    private final Logger logger = Logger.getLogger(JobOfferRepositoryImpl.class.getName());

//    @PersistenceContext(unitName = "labor")
    private EntityManager em;

    public JobOfferRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public JobOffer save(JobOffer jobOffer) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (jobOffer.getJobOfferId() == null) {
                em.persist(jobOffer);
            } else {
                em.merge(jobOffer);
            }
            transaction.commit();
            return jobOffer;
        } catch (Exception e) {
            logger.severe("Failed to save job offer: " + e.getMessage());
            throw new RuntimeException("Failed to save job offer", e);
        }
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        try {
            JobOffer jobOffer = em.find(JobOffer.class, id);
            return Optional.ofNullable(jobOffer);
        } catch (Exception e) {
            logger.severe("Failed to find job offer by id: " + e.getMessage());
            throw new RuntimeException("Failed to find job offer by id", e);
        }
    }

    @Override
    public List<JobOffer> findAll() {
        try {
            return em.createQuery("SELECT j FROM JobOffer j", JobOffer.class).getResultList();
        } catch (Exception e) {
            logger.severe("Failed to find all job offers: " + e.getMessage());
            throw new RuntimeException("Failed to find all job offers", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<JobOffer> jobOffer = findById(id);
            jobOffer.ifPresent(em::remove);
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Failed to delete job offer by id: " + e.getMessage());
            throw new RuntimeException("Failed to delete job offer by id", e);
        }
    }
}
