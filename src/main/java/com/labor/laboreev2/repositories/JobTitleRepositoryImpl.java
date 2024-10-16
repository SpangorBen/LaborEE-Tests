package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.JobTitle;
import com.labor.laboreev2.repositories.interfaces.JobTitleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class JobTitleRepositoryImpl implements JobTitleRepository {

    private final Logger logger = Logger.getLogger(JobTitleRepositoryImpl.class.getName());
    private EntityManager em;

    public JobTitleRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public JobTitle save(JobTitle jobTitle) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (jobTitle.getId() == null) {
                em.persist(jobTitle);
            } else {
                em.merge(jobTitle);
            }
            transaction.commit();
            return jobTitle;
        } catch (Exception e) {
            logger.severe("Failed to save job title: " + e.getMessage());
            throw new RuntimeException("Failed to save job title", e);
        }
    }

    @Override
    public Optional<JobTitle> findById(Long id) {
        try {
            JobTitle jobTitle = em.find(JobTitle.class, id);
            return Optional.ofNullable(jobTitle);
        } catch (Exception e) {
            logger.severe("Failed to find job title by id: " + e.getMessage());
            throw new RuntimeException("Failed to find job title by id", e);
        }
    }

    @Override
    public List<JobTitle> findAll() {
        try {
            return em.createQuery("SELECT jt FROM JobTitle jt", JobTitle.class).getResultList();
        } catch (Exception e) {
            logger.severe("Failed to find all job titles: " + e.getMessage());
            throw new RuntimeException("Failed to find all job titles", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<JobTitle> jobTitle = findById(id);
            jobTitle.ifPresent(value -> em.remove(value));
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Failed to delete job title by id: " + e.getMessage());
            throw new RuntimeException("Failed to delete job title by id", e);
        }
    }
}
