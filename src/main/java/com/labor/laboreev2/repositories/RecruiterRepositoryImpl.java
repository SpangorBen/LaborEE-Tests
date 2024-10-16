package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.Recruiter;
import com.labor.laboreev2.repositories.interfaces.RecruiterRepository;
import jakarta.persistence.EntityManager;

import java.util.logging.Logger;

public class RecruiterRepositoryImpl implements RecruiterRepository {

    private Logger logger = Logger.getLogger(RecruiterRepositoryImpl.class.getName());
    private EntityManager em;

    public RecruiterRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public Recruiter findById(Long id) {
        try {
            return em.find(Recruiter.class, id);
        } catch (Exception e) {
            logger.severe("Failed to find recruiter by id: " + e.getMessage());
            throw new RuntimeException("Failed to find recruiter by id", e);
        }
    }
}
