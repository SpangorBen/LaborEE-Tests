package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

//@ApplicationScoped
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Logger logger = Logger.getLogger(EmployeeRepositoryImpl.class.getName());

//    @Inject
    private EntityManager em;

    public EmployeeRepositoryImpl(){
    }

    public EmployeeRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        try {
//            EntityTransaction transaction = em.getTransaction();
//            transaction.begin();
            if (employee.getId() == null) {
                em.persist(employee);
            } else {
                em.merge(employee);
            }
//            transaction.commit();
            return employee;
        } catch (Exception e) {
            logger.severe("Failed to save employee: " + e.getMessage());
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            Employee employee = em.find(Employee.class, id);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            logger.severe("Failed to find employee by id: " + e.getMessage());
            throw new RuntimeException("Failed to find employee by id", e);
        }
    }

    @Override
    public List<Employee> findAll() {
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } catch (Exception e) {
            logger.severe("Failed to find all employees: " + e.getMessage());
            throw new RuntimeException("Failed to find all employees", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Employee> employee = findById(id);
            employee.ifPresent(value -> em.remove(value));
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Failed to delete employee by id: " + e.getMessage());
            throw new RuntimeException("Failed to delete employee by id", e);
        }
    }
}
