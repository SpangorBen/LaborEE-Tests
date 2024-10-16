package com.labor.laboreev2.servlets;

import com.labor.laboreev2.models.*;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.models.enums.JobOfferStatus;
import com.labor.laboreev2.repositories.interfaces.DepartmentRepository;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import com.labor.laboreev2.repositories.interfaces.JobTitleRepository;
import com.labor.laboreev2.services.interfaces.RecruiterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/recruiter/*")
public class RecruiterServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(RecruiterServlet.class.getName());

    private RecruiterService recruiterService;
    private DepartmentRepository departmentRepository;
    private JobTitleRepository jobTitleRepository;

    @Override
    public void init() throws ServletException {
        super.init();

        recruiterService = (RecruiterService) getServletContext().getAttribute("recruiterService");
        departmentRepository = (DepartmentRepository) getServletContext().getAttribute("departmentRepository");
        jobTitleRepository = (JobTitleRepository) getServletContext().getAttribute("jobTitleRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getPathInfo();
        logger.severe("action: " + action);

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/createJobOffer":
                showCreateJobOffre(request, response);
                break;
            case "/deleteJobOffer":
                deleteJobOffer(request, response);
                break;
            default:
                showDashboard(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String action = request.getPathInfo();
            logger.severe("action: " + action);

            switch (action) {
                case "/createJobOffer":
                    createJobOffer(request, response);
                    break;
                default:
                    showDashboard(request, response);
                    break;
            }
    }

    private void createJobOffer(HttpServletRequest request, HttpServletResponse response) {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            Long jobTitleId = Long.parseLong(request.getParameter("jobTitleId"));
            JobOfferStatus status = JobOfferStatus.valueOf(request.getParameter("status"));
            LocalDate publicationDate = LocalDate.parse(request.getParameter("publicationDate"));
            Integer validityDurationDays = Integer.parseInt(request.getParameter("validityDurationDays"));

            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            JobTitle jobTitle = jobTitleRepository.findById(jobTitleId)
                    .orElseThrow(() -> new RuntimeException("Job Title not found"));
            Recruiter recruiter = recruiterService.getRecruiterById(2L);

            JobOffer jobOffer = new JobOffer(title, description, department, jobTitle, status, publicationDate, validityDurationDays, recruiter);

            recruiterService.createJobOffer(jobOffer);
            response.sendRedirect(request.getContextPath() + "/recruiter/dashboard");

        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Error parsing input data", ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error creating employee", ex);
        }
    }

    private void showCreateJobOffre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentRepository.findAll();
        List<JobTitle> jobTitles = jobTitleRepository.findAll();
        List<String> jobOfferStatuses = Arrays.stream(JobOfferStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        request.setAttribute("departments", departments);
        request.setAttribute("jobTitles", jobTitles);
        request.setAttribute("jobOfferStatuses", jobOfferStatuses);

        request.getRequestDispatcher("/recruits/createJobOffer.jsp").forward(request, response);
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<JobOffer> jobOffers = recruiterService.getJobOffers(); // Assuming you'll add this to RecruiterService
        request.setAttribute("jobOffers", jobOffers);

        request.getRequestDispatcher("/recruits/recruiter-dashboard.jsp").forward(request, response);
    }

    private void showJobApplications(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long jobOfferId = Long.parseLong(request.getParameter("jobOfferId"));
        List<Application> applications = recruiterService.getJobOfferById(jobOfferId).getApplications();
        request.setAttribute("applications", applications);
        List<String> applicationStatuses = Arrays.stream(ApplicationStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        request.setAttribute("applicationStatuses", applicationStatuses);
        request.getRequestDispatcher("/recruiter/viewApplications.jsp").forward(request, response);
    }

    private void deleteJobOffer(HttpServletRequest request, HttpServletResponse response) {
        Long jobOfferId = Long.parseLong(request.getParameter("id"));
        try {
            recruiterService.deleteJobOffer(jobOfferId);
            response.sendRedirect("dashboard");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting job offer", ex);
        }
    }
}
