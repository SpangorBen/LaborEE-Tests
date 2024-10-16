package com.labor.laboreev2.services.interfaces;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest);
    List<LeaveRequest> getEmployeeLeaveRequests(Long employeeId);
    double calculateFamilyAllowance(Long employeeId);
    Optional<Employee> getById(Long Id);
}
