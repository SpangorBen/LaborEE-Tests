package com.labor.laboreev2.utils;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;

import java.util.List;

public class LeaveRequestUtil {

    public int calculateUsedLeaveDays(Employee employee, int year, LeaveRequestRepository leaveRequestRepository) {
        List<LeaveRequest> approvedLeaveRequests = leaveRequestRepository.findByUserAndStatus(employee, LeaveRequestStatus.APPROVED);
        int usedLeaveDays = 0;
        for (LeaveRequest requests : approvedLeaveRequests) {
            if (requests.getStartDate().getYear() == year) {
                usedLeaveDays += requests.calculateDuration();
            }
        }

        return usedLeaveDays;
    }
}
