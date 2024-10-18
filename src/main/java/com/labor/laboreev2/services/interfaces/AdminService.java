package com.labor.laboreev2.services.interfaces;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdminService {
    List<LeaveRequest> getAllPendingLeaveRequests();
    LeaveRequest approveLeaveRequest(Long leaveRequestId);
    LeaveRequest rejectLeaveRequest(Long leaveRequestId);

    Employee createEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long employeeId);
    Employee getEmployeeById(Long employeeId);
    List<Employee> getAllEmployees();

//    Map<LocalDate, BigDecimal> getMonthlyFamilyAllowanceTotals();
    Map<LeaveRequestStatus, Long> getMonthlyLeaveRequestCounts(LocalDate month);
}
