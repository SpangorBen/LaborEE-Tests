package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.User;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository {
    LeaveRequest save(LeaveRequest leaveRequest);
    Optional<LeaveRequest> findById(Long id);
    List<LeaveRequest> findAll();
    void deleteById(Long id);
    List<LeaveRequest> findByStatus(LeaveRequestStatus status);
    List<LeaveRequest> findByMonth(LocalDate month);
    List<LeaveRequest> findByUserAndStatus(Employee employee, LeaveRequestStatus status);
    public List<LeaveRequest> findPendingLeaveRequests();
    List<LeaveRequest> findByUser(User user);
}
