package com.labor.laboreev2.services;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import com.labor.laboreev2.utils.FamilyAllowance;
import com.labor.laboreev2.utils.LeaveRequestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private LeaveRequestUtil leaveRequestUtil;

    @Mock
    private FamilyAllowance familyAllowance;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private LeaveRequest leaveRequest;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setNumOfChildren(2);

        leaveRequest = new LeaveRequest();
        leaveRequest.setUser(employee);
        leaveRequest.setStartDate(LocalDate.now().plusDays(1));
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));
    }

    @Test
    void testSubmitLeaveRequest_Success() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(leaveRequestRepository.findByUserAndStatus(eq(employee), eq(LeaveRequestStatus.APPROVED)))
                .thenReturn(List.of());
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        LeaveRequest result = employeeService.submitLeaveRequest(leaveRequest);

        assertNotNull(result);
        assertEquals(LeaveRequestStatus.PENDING, result.getStatus());
        verify(leaveRequestRepository, times(1)).save(leaveRequest);
    }

    @Test
    void testSubmitLeaveRequest_EmployeeNotFound() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.submitLeaveRequest(leaveRequest));

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testSubmitLeaveRequest_OverlappingLeave() {
        LeaveRequest overlappingLeave = new LeaveRequest();
        overlappingLeave.setStartDate(LocalDate.now().plusDays(2));
        overlappingLeave.setEndDate(LocalDate.now().plusDays(4));
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(leaveRequestRepository.findByUserAndStatus(any(Employee.class), eq(LeaveRequestStatus.APPROVED)))
                .thenReturn(List.of(overlappingLeave));

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.submitLeaveRequest(leaveRequest));

        assertEquals("Leave request overlaps with an approved leave request", exception.getMessage());
    }

    @Test
    void testSubmitLeaveRequest_NotEnoughDays() {
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));

        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(leaveRequestRepository.findByUserAndStatus(any(Employee.class), eq(LeaveRequestStatus.APPROVED)))
                .thenReturn(List.of());
        when(leaveRequestUtil.calculateUsedLeaveDays(any(Employee.class), anyInt(), any(LeaveRequestRepository.class)))
                .thenReturn(18);

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.submitLeaveRequest(leaveRequest));

        assertEquals("Not enough leave days left", exception.getMessage());
    }

    @Test
    void testGetEmployeeLeaveRequests_Success() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(leaveRequestRepository.findByUser(any(Employee.class))).thenReturn(List.of(leaveRequest));

        List<LeaveRequest> result = employeeService.getEmployeeLeaveRequests(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(leaveRequestRepository, times(1)).findByUser(employee);
    }

    @Test
    void testGetEmployeeLeaveRequests_EmployeeNotFound() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.getEmployeeLeaveRequests(1L));

        assertEquals("Employee not found with ID: 1", exception.getMessage());
    }

    @Test
    void testCalculateFamilyAllowance_Success() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(familyAllowance.calculateFamilyAllowance(any(Employee.class))).thenReturn(100.0);

        double allowance = employeeService.calculateFamilyAllowance(1L);

        assertEquals(100.0, allowance);
    }

    @Test
    void testCalculateFamilyAllowance_EmployeeNotFound() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.calculateFamilyAllowance(1L));

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testGetById_Success() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
    }

    @Test
    void testGetById_Failure() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getById(1L);

        assertFalse(result.isPresent(), "Employee should not be found, expected an empty Optional.");
    }
}
