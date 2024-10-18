package com.labor.laboreev2.services;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import com.labor.laboreev2.services.interfaces.EmployeeService;
import com.labor.laboreev2.utils.FamilyAllowance;
import com.labor.laboreev2.utils.LeaveRequestUtil;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeServiceImpl implements EmployeeService {

    private final Logger logger = Logger.getLogger(EmployeeServiceImpl.class.getName());
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestUtil leaveRequestUtil;
    private final FamilyAllowance familyAllowance;

    public EmployeeServiceImpl(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository, LeaveRequestUtil leaveRequestUtil, FamilyAllowance familyAllowance) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestUtil = leaveRequestUtil;
        this.familyAllowance = familyAllowance;
    }

    @Override
    @Transactional
    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findById(leaveRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<LeaveRequest> approvedLeaveRequests = leaveRequestRepository.findByUserAndStatus(employee, LeaveRequestStatus.APPROVED);
        for (LeaveRequest approvedLeaveRequest : approvedLeaveRequests) {
            if (leaveRequest.getStartDate().isBefore(approvedLeaveRequest.getEndDate()) &&
                    leaveRequest.getEndDate().isAfter(approvedLeaveRequest.getStartDate())) {
                throw new RuntimeException("Leave request overlaps with an approved leave request");
            }
        }

        int totalDays = 21;
        int usedLeaveDays = leaveRequestUtil.calculateUsedLeaveDays(employee, leaveRequest.getStartDate().getYear(), leaveRequestRepository);
        int requestedLeaveDays = leaveRequest.calculateDuration();
        logger.info("Requested leave days: " + requestedLeaveDays);
        logger.info("Used leave days: " + usedLeaveDays);
        if (totalDays - usedLeaveDays < requestedLeaveDays) {
            logger.severe("Not enough leave days left; you have: " + (totalDays - usedLeaveDays) + " days left");
            throw new RuntimeException("Not enough leave days left");
        }

        leaveRequest.setStatus(LeaveRequestStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getEmployeeLeaveRequests(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        return leaveRequestRepository.findByUser(employee);
    }


    public double calculateFamilyAllowance(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }
        return familyAllowance.calculateFamilyAllowance(employee.get());

    }

    @Override
    public Optional<Employee> getById(Long Id) {
        try {
        return employeeRepository.findById(Id);
        } catch (Exception e) {
            logger.severe("Failed to find employee by id: " + e.getMessage());
            throw new RuntimeException("Failed to find employee by id", e);
        }
    }

}
