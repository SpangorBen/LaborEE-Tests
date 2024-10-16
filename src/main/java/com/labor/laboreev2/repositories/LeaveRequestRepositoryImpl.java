package com.labor.laboreev2.repositories;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.User;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class LeaveRequestRepositoryImpl implements LeaveRequestRepository {

    private final Logger logger = Logger.getLogger(LeaveRequestRepositoryImpl.class.getName());
    private final EntityManager em;

    public LeaveRequestRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public LeaveRequest save(LeaveRequest leaveRequest) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            if (leaveRequest.getLeaveRequestId() == null) {
                em.persist(leaveRequest);
            } else {
                em.merge(leaveRequest);
            }
            transaction.commit();
            return leaveRequest;
        } catch (Exception e) {
            logger.severe("Error saving leave request: " + e.getMessage());
            throw new RuntimeException("Error saving leave request", e);
        }
    }

    @Override
    public Optional<LeaveRequest> findById(Long id) {
        try {
            LeaveRequest leaveRequest = em.find(LeaveRequest.class, id);
            return Optional.ofNullable(leaveRequest);
        } catch (Exception e) {
            logger.severe("Error finding leave request by id: " + e.getMessage());
            throw new RuntimeException("Error finding leave request by id", e);
        }
    }

    @Override
    public List<LeaveRequest> findAll() {
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr", LeaveRequest.class).getResultList();
        } catch (Exception e) {
            logger.severe("Error finding all leave requests: " + e.getMessage());
            throw new RuntimeException("Error finding all leave requests", e);
        }
    }

    @Override
    public List<LeaveRequest> findPendingLeaveRequests() {
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.status = :status", LeaveRequest.class)
                    .setParameter("status", LeaveRequestStatus.PENDING)
                    .getResultList();
        } catch (Exception e) {
            logger.severe("Error finding pending leave requests: " + e.getMessage());
            throw new RuntimeException("Error finding pending leave requests", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<LeaveRequest> leaveRequest = findById(id);
            leaveRequest.ifPresent(em::remove);
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Error deleting leave request by id: " + e.getMessage());
            throw new RuntimeException("Error deleting leave request by id", e);
        }
    }

    @Override
    public List<LeaveRequest> findByStatus(LeaveRequestStatus status) {
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.status = :status", LeaveRequest.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            logger.severe("Error finding leave requests by status: " + e.getMessage());
            throw new RuntimeException("Error finding leave requests by status", e);
        }
    }

    @Override
    public List<LeaveRequest> findByMonth(LocalDate month) {
        try {
            LocalDate startOfMonth = month.withDayOfMonth(1);
            LocalDate endOfMonth = month.withDayOfMonth(month.lengthOfMonth());

            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.startDate BETWEEN :startOfMonth AND :endOfMonth", LeaveRequest.class)
                    .setParameter("startOfMonth", startOfMonth)
                    .setParameter("endOfMonth", endOfMonth)
                    .getResultList();
        } catch (Exception ex) {
            logger.severe("Error finding leave requests by month: " + ex.getMessage());
            throw new RuntimeException("Error finding leave requests by month", ex);
        }
    }

    @Override
    public List<LeaveRequest> findByUserAndStatus(Employee employee, LeaveRequestStatus status) {
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user = :employee AND lr.status = :status", LeaveRequest.class)
                    .setParameter("employee", employee)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception ex) {
            // Handle the exception
            throw new RuntimeException("Error finding leave requests by user and status", ex);
        }
    }

    @Override
    public List<LeaveRequest> findByUser(User user) {
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user", LeaveRequest.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception ex) {
            logger.severe("Error finding leave requests for user: " + ex.getMessage());
            throw new RuntimeException("Error finding leave requests for user", ex);
        }
    }

}
