package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.Department;
import com.labor.laboreev2.repositories.interfaces.DepartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final Logger logger = Logger.getLogger(DepartmentRepositoryImpl.class.getName());
    private EntityManager em;

    public DepartmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Department save(Department department) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (department.getId() == null) {
                 em.persist(department);
            } else {
                 em.merge(department);
            }
            transaction.commit();
            return department;
        } catch (Exception e) {
            logger.severe("Failed to save department: " + e.getMessage());
            throw new RuntimeException("Failed to save department", e);
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        try {
            Department department = em.find(Department.class, id);
            return Optional.ofNullable(department);
        } catch (Exception e) {
            logger.severe("Failed to find department by id: " + e.getMessage());
            throw new RuntimeException("Failed to find department by id", e);
        }
    }

    @Override
    public List<Department> findAll() {
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        } catch (Exception e) {
            logger.severe("Failed to find all departments: " + e.getMessage());
            throw new RuntimeException("Failed to find all departments", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Department> department = findById(id);
            department.ifPresent(value -> em.remove(value));
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Failed to delete department by id: " + e.getMessage());
            throw new RuntimeException("Failed to delete department by id", e);
        }
    }
}
