package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.Application;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.repositories.interfaces.ApplicationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ApplicationRepositoryImpl implements ApplicationRepository {

    private final Logger logger = Logger.getLogger(ApplicationRepositoryImpl.class.getName());
    private final EntityManager em;

    public ApplicationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Application save(Application application) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (application.getApplicationId() == null) {
                em.persist(application);
            } else {
                em.merge(application);
            }
            transaction.commit();
            return application;
        } catch (Exception e) {
            logger.severe("Error saving application: " + e.getMessage());
            throw new RuntimeException("Error saving application", e);
        }
    }

    @Override
    public Optional<Application> findById(Long id) {
        try {
            Application application = em.find(Application.class, id);
            return Optional.ofNullable(application);
        } catch (Exception e) {
            logger.severe("Error finding application by id: " + e.getMessage());
            throw new RuntimeException("Error finding application by id", e);
        }
    }

    @Override
    public List<Application> findAll() {
        try {
            return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
        } catch (Exception e) {
            logger.severe("Error finding all applications: " + e.getMessage());
            throw new RuntimeException("Error finding all applications", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Application> application = findById(id);
            application.ifPresent(a -> em.remove(a));
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Error deleting application by id: " + e.getMessage());
            throw new RuntimeException("Error deleting application by id", e);
        }
    }

    @Override
    public List<Application> findByStatus(ApplicationStatus status) {
        try {
            return em.createQuery("SELECT a FROM Application a WHERE a.status = :status", Application.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            logger.severe("Error finding applications by status: " + e.getMessage());
            throw new RuntimeException("Error finding applications by status", e);
        }
    }

    @Override
    public List<Application> findBySkill(String skill) {
        try {
            return em.createQuery("SELECT a FROM Application a WHERE a.skills = :skill", Application.class)
                    .setParameter("skill", "%" + skill + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.severe("Error finding applications by skill: " + e.getMessage());
            throw new RuntimeException("Error finding applications by skill", e);
        }
    }

    @Override
    public List<Application> findByJobOffer(Long jobOfferId) {
        try {
            return em.createQuery("SELECT a FROM Application a WHERE a.jobOffer.jobOfferId = :jobOfferId", Application.class)
                    .setParameter("jobOfferId", jobOfferId)
                    .getResultList();
        } catch (Exception e) {
            logger.severe("Error finding applications by job offer: " + e.getMessage());
            throw new RuntimeException("Error finding applications by job offer", e);
        }
    }
}
