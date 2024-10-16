package com.labor.laboreev2.servlets;

import com.labor.laboreev2.models.*;
import com.labor.laboreev2.repositories.DepartmentRepositoryImpl;
import com.labor.laboreev2.repositories.EmployeeRepositoryImpl;
import com.labor.laboreev2.repositories.JobTitleRepositoryImpl;
import com.labor.laboreev2.repositories.LeaveRequestRepositoryImpl;
import com.labor.laboreev2.repositories.interfaces.DepartmentRepository;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.JobTitleRepository;
import com.labor.laboreev2.repositories.interfaces.LeaveRequestRepository;
import com.labor.laboreev2.services.AdminServiceImpl;
import com.labor.laboreev2.services.interfaces.AdminService;
import com.labor.laboreev2.utils.PasswordUtil;
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
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(AdminServlet.class.getName());

    private LeaveRequestRepository leaveRequestRepository;
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private JobTitleRepository jobTitleRepository;
    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        super.init();

        employeeRepository = (EmployeeRepository) getServletContext().getAttribute("employeeRepository");
        departmentRepository = (DepartmentRepository) getServletContext().getAttribute("departmentRepository");
        jobTitleRepository = (JobTitleRepository) getServletContext().getAttribute("jobTitleRepository");
        leaveRequestRepository = (LeaveRequestRepository) getServletContext().getAttribute("leaveRequestRepository");
        adminService = (AdminService) getServletContext().getAttribute("adminService");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            action = "/dashboard";
        }

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/createEmployee":
                showCreateEmployee(request, response);
                break;
            case "/deleteEmployee":
                deleteEmployee(request, response);
                break;
            case "/approveLeave":
                approveLeaveRequest(request, response);
                break;
            case "/rejectLeave":
                rejectLeaveRequest(request, response);
                break;
            default:
                logger.severe("Invalid action");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested action is not supported.");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        switch (action) {
            case "/createEmployee":
                createEmployee(request, response);
                break;
            default:
                logger.severe("Invalid action");
                break;
        }
    }

    private void showCreateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentRepository.findAll();
        List<JobTitle> jobTitles = jobTitleRepository.findAll();
        request.setAttribute("departments", departments);
        request.setAttribute("jobTitles", jobTitles);

        request.getRequestDispatcher("/admins/createEmployee.jsp").forward(request, response);
    }

    protected void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LeaveRequest> pendingLeaveRequests = adminService.getAllPendingLeaveRequests();
//        List<LeaveRequest> pendingLeaveRequests = leaveRequestRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        request.setAttribute("pendingLeaveRequests", pendingLeaveRequests);
        request.setAttribute("employees", employees);

        request.getRequestDispatcher("/admins/admin-dashboard.jsp").forward(request, response);
    }

    private void createEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
            String socialSecurityNumber = request.getParameter("socialSecurityNumber");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            LocalDate hireDate = LocalDate.parse(request.getParameter("hireDate"));
            Integer numOfChildren = Integer.parseInt(request.getParameter("numOfChildren"));
            Double salary = Double.parseDouble(request.getParameter("salary"));

            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String postalCode = request.getParameter("postalCode");
            String country = request.getParameter("country");

            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            Long jobTitleId = Long.parseLong(request.getParameter("jobTitleId"));

            Address address = new Address(street, city, postalCode, country);

            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            JobTitle jobTitle = jobTitleRepository.findById(jobTitleId)
                    .orElseThrow(() -> new RuntimeException("Job Title not found"));


            String generatedPassword = "password";
            byte[] salt = PasswordUtil.generateSalt();
            String saltBase64 = Base64.getEncoder().encodeToString(salt);

            String hashedPassword = PasswordUtil.hashPassword(generatedPassword, salt);

            Employee employee = new Employee(name, birthDate, socialSecurityNumber, email, generatedPassword,
                    saltBase64, phone, hireDate, numOfChildren, salary, address, department, jobTitle);

            try {
                logger.severe("Creating employee" + employee);
                adminService.createEmployee(employee);

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error creating employee", ex);
            }

            response.sendRedirect(request.getContextPath() + "/admin/dashboard");

        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Error parsing input data", ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error creating employee", ex);
        }
    }


    private void approveLeaveRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long leaveRequestId = Long.parseLong(request.getParameter("id"));
        adminService.approveLeaveRequest(leaveRequestId);
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }

    private void rejectLeaveRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long leaveRequestId = Long.parseLong(request.getParameter("id"));
        adminService.rejectLeaveRequest(leaveRequestId);
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long employeeId = Long.parseLong(request.getParameter("id"));
        try {
            adminService.deleteEmployee(employeeId);
            logger.severe("Deleting employee with id: " + employeeId);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting employee", ex);
        }
//        adminService.deleteEmployee(employeeId);
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
