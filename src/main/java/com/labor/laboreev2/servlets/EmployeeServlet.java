package com.labor.laboreev2.servlets;

import com.labor.laboreev2.models.Employee;
import com.labor.laboreev2.models.LeaveRequest;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.services.interfaces.EmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/employee/*")
public class EmployeeServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(EmployeeServlet.class.getName());

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        super.init();

        employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            action = "/dashboard";
        }

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/calculateFamilyAllowance":
//                calculateFamilyAllowance(request, response);
                break;
            default:
                showDashboard(request, response);
                break;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null && action.equals("submitLeave")) {
            submitLeaveRequest(request, response);
        }
    }


    private void submitLeaveRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            String reason = request.getParameter("reason");
            LeaveRequestStatus status = LeaveRequestStatus.PENDING;

            Long employeeId = 3L;

            Employee employee = employeeService.getById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            LeaveRequest leaveRequest = new LeaveRequest(employee, startDate, endDate, reason, status);

            employeeService.submitLeaveRequest(leaveRequest);

            response.sendRedirect(request.getContextPath() + "/employee/employee-dashboard");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error submitting leave request", ex);
            request.setAttribute("errorMessage", "An error occurred. Please try again later.");
            showDashboard(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long employeeId = 3L;

        Employee employee = employeeService.getById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        List<LeaveRequest> leaveRequests = employeeService.getEmployeeLeaveRequests(employeeId);
        double familyAllowance = employeeService.calculateFamilyAllowance(employeeId);

        request.setAttribute("employee", employee);
        request.setAttribute("leaveRequests", leaveRequests);
        request.setAttribute("familyAllowance", familyAllowance);

        request.getRequestDispatcher("/employees/employee-dashboard.jsp").forward(request, response);
    }
}
