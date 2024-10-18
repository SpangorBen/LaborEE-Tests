package com.labor.laboreev2.services;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import com.labor.laboreev2.services.interfaces.AdminService;
import com.labor.laboreev2.utils.LeaveRequestUtil;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AdminServiceImpl implements AdminService {

    private final Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestUtil leaveRequestUtil;

    public AdminServiceImpl(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository, LeaveRequestUtil leaveRequestUtil) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestUtil = leaveRequestUtil;
    }

    @Override
    public List<LeaveRequest> getAllPendingLeaveRequests() {
        try {
            return leaveRequestRepository.findPendingLeaveRequests();
        } catch (Exception e) {
            logger.severe("Error getting all pending leave requests: " + e.getMessage());
            throw new RuntimeException("Error getting all pending leave requests", e);
        }
    }

    @Override
    @Transactional
    public LeaveRequest approveLeaveRequest(Long leaveRequestId) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            Employee employee = employeeRepository.findById(leaveRequest.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            int totalLeaveDays = 21;
            int usedLeaveDays = leaveRequestUtil.calculateUsedLeaveDays(employee, leaveRequest.getStartDate().getYear(), leaveRequestRepository);
            int requestedDays = leaveRequest.calculateDuration();

            if (requestedDays > (totalLeaveDays - usedLeaveDays)) {
                throw new RuntimeException("Cannot approve leave request. Insufficient leave balance for employee.");
            }

            leaveRequest.setStatus(LeaveRequestStatus.APPROVED);
            return leaveRequestRepository.save(leaveRequest);

        } catch (Exception e) {
            logger.severe("Error approving leave request: " + e.getMessage());
            throw new RuntimeException("Error approving leave request", e);
        }
    }

    @Override
    @Transactional
    public LeaveRequest rejectLeaveRequest(Long leaveRequestId) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));
            leaveRequest.setStatus(LeaveRequestStatus.REJECTED);
            return leaveRequestRepository.save(leaveRequest);
        } catch (Exception e) {
            logger.severe("Error rejecting leave request: " + e.getMessage());
            throw new RuntimeException("Error rejecting leave request", e);
        }
    }

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        try {
            Employee employee1 =  employeeRepository.save(employee);
            logger.info("Employee created successfully: " + employee1);
            return employee1;
        } catch (Exception e) {
            logger.severe("Failed to create employee: " + e.getMessage());
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            logger.severe("Failed to update employee: " + e.getMessage());
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (Exception e) {
            logger.severe("Failed to delete employee: " + e.getMessage());
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        try {
            return employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        } catch (Exception e) {
            logger.severe("Failed to find employee by id: " + e.getMessage());
            throw new RuntimeException("Failed to find employee by id", e);
        }
    }

}
