package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.PayRoll;
import com.labor.laboreev2.repositories.interfaces.PayRollRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PayRollRepositoryImpl implements PayRollRepository {

    private final Logger logger = Logger.getLogger(PayRollRepositoryImpl.class.getName());
    private EntityManager em;

    public PayRollRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public PayRoll save(PayRoll payroll) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (payroll.getPayrollId() == null) {
                em.persist(payroll);
            } else {
                em.merge(payroll);
            }
            transaction.commit();
            return payroll;
        } catch (Exception e) {
            logger.severe("Failed to save payroll: " + e.getMessage());
            throw new RuntimeException("Failed to save payroll", e);
        }
    }

    @Override
    public Optional<PayRoll> findById(Long id) {
        try {
            PayRoll payroll = em.find(PayRoll.class, id);
            return Optional.ofNullable(payroll);
        } catch (Exception e) {
            logger.severe("Failed to find payroll by id: " + e.getMessage());
            throw new RuntimeException("Failed to find payroll by id", e);
        }
    }

    @Override
    public List<PayRoll> findAll() {
        try {
            return em.createQuery("SELECT p FROM PayRoll p", PayRoll.class).getResultList();
        } catch (Exception e) {
            logger.severe("Failed to find all payrolls: " + e.getMessage());
            throw new RuntimeException("Failed to find all payrolls", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            PayRoll payroll = em.find(PayRoll.class, id);
            em.remove(payroll);
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Failed to delete payroll by id: " + e.getMessage());
            throw new RuntimeException("Failed to delete payroll by id", e);
        }
    }
}
