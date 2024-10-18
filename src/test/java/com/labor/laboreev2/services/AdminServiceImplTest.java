package com.labor.laboreev2.services;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import com.labor.laboreev2.utils.LeaveRequestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveRequestUtil leaveRequestUtil;

    @InjectMocks
    private AdminServiceImpl adminService;

    private LeaveRequest leaveRequest;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        leaveRequest = new LeaveRequest();
        leaveRequest.setLeaveRequestId(1L);
        leaveRequest.setUser(employee);
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));
        leaveRequest.setStatus(LeaveRequestStatus.PENDING);
    }

    @Test
    void testGetAllPendingLeaveRequests() {
        List<LeaveRequest> pendingRequests = new ArrayList<>();
        pendingRequests.add(leaveRequest);

        when(leaveRequestRepository.findPendingLeaveRequests()).thenReturn(pendingRequests);

        List<LeaveRequest> result = adminService.getAllPendingLeaveRequests();

        assertEquals(1, result.size());
        verify(leaveRequestRepository, times(1)).findPendingLeaveRequests();
    }

    @Test
    void testApproveLeaveRequest() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.of(leaveRequest));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(leaveRequestUtil.calculateUsedLeaveDays(any(Employee.class), anyInt(), any())).thenReturn(0);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        LeaveRequest result = adminService.approveLeaveRequest(1L);

        assertEquals(LeaveRequestStatus.APPROVED, result.getStatus());
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
    }

    @Test
    void testApproveLeaveRequest_NotFound() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.approveLeaveRequest(1L));

        assertEquals("Leave request not found", exception.getMessage());
        verify(leaveRequestRepository, times(1)).findById(anyLong());
    }

    @Test
    void testApproveLeaveRequest_EmployeeNotFound() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.of(leaveRequest));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.approveLeaveRequest(1L));

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testApproveLeaveRequest_InsufficientLeaveBalance() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.of(leaveRequest));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(leaveRequestUtil.calculateUsedLeaveDays(any(Employee.class), anyInt(), any())).thenReturn(20); // Simulating used days
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.approveLeaveRequest(1L));

        assertEquals("Cannot approve leave request. Insufficient leave balance for employee.", exception.getMessage());
    }

    @Test
    void testRejectLeaveRequest() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.of(leaveRequest));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        LeaveRequest result = adminService.rejectLeaveRequest(1L);

        assertEquals(LeaveRequestStatus.REJECTED, result.getStatus());
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
    }

    @Test
    void testRejectLeaveRequest_NotFound() {
        when(leaveRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.rejectLeaveRequest(1L));

        assertEquals("Leave request not found", exception.getMessage());
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = adminService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_Failure() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.createEmployee(employee));

        assertEquals("Failed to create employee", exception.getMessage());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = adminService.updateEmployee(employee);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_Failure() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.updateEmployee(employee));

        assertEquals("Failed to update employee", exception.getMessage());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(anyLong());

        adminService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_Failure() {
        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteById(anyLong());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.deleteEmployee(1L));

        assertEquals("Failed to delete employee", exception.getMessage());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        Employee result = adminService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminService.getEmployeeById(1L));

        assertEquals("Employee not found", exception.getMessage());
    }
}
