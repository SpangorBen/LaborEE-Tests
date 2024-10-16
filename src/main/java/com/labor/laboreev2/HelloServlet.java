package com.labor.laboreev2;

import java.io.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.logging.Logger;

import com.labor.laboreev2.models.*;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.models.enums.JobOfferStatus;
import com.labor.laboreev2.models.enums.LeaveRequestStatus;
import com.labor.laboreev2.repositories.EmployeeRepositoryImpl;
import com.labor.laboreev2.repositories.JobOfferRepositoryImpl;
import com.labor.laboreev2.repositories.interfaces.EmployeeRepository;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import com.labor.laboreev2.services.AdminServiceImpl;
import com.labor.laboreev2.services.interfaces.AdminService;
import com.labor.laboreev2.utils.PasswordUtil;
import jakarta.persistence.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private final Logger logger = Logger.getLogger(HelloServlet.class.getName());

    public void init() {
//        try {
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("labor");
//            EntityManager em = emf.createEntityManager();
//            EntityTransaction transaction = em.getTransaction();
//            transaction.begin();
//
//            EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(em);
//            AdminService adminService = new AdminServiceImpl(null, employeeRepository);
//            JobOfferRepository jobOfferRepository = new JobOfferRepositoryImpl(em);
//            LocalDate date = LocalDate.of(2000, 12, 12);
//            Address address = new Address("Budapest", "1111", "Kossuth Lajos utca 1.", "Hungary");
//
//            Long id = 3L;
//            Employee emplowyee = employeeRepository.findById(id).get();
//            LeaveRequest leaveRequest = new LeaveRequest(
//                    emplowyee,
//                    date,
//                    date.plusDays(5),
//                    "Sickness leave",
//                    LeaveRequestStatus.PENDING
//            );
//            em.persist(leaveRequest);
//
////            Department department = new Department("IT");
////            em.persist(department);
////            JobTitle jobTitle = new JobTitle("Dev", "Developer");
////            em.persist(jobTitle);
////            transaction.commit();
////            Employee employee = new Employee(" Mine", date, "1234567o00k89","t2t@test.com", "45678", date, 0, 2300.0, address, department, jobTitle);
////            adminService.createEmployee(employee);
////            Recruiter recruiter = new Recruiter("John Doe", date, "123456789", "test@test.com", "", date, 0, address);
////            Department department = em.find(Department.class, 1);
////            JobTitle jobTitle = em.find(JobTitle.class, 1);
////            Recruiter recruiter = em.find(Recruiter.class, 1);
////            String title = "Software Developer";
////            String description = "We are looking for an experienced software developer to join our team.";
////            JobOfferStatus status = JobOfferStatus.OPEN;
////            LocalDate publicationDate = LocalDate.now();
////            Integer validityDurationDays = 30;
////            JobOffer jobOffer = new JobOffer(title, description, department, jobTitle, status, publicationDate, validityDurationDays, recruiter);
////            jobOfferRepository.save(jobOffer);
////            JobOffer jobOffer = em.find(JobOffer.class, 1);
////            Application application = new Application(
////                    jobOffer,
////                    "John Doe", // Applicant's name.
////                    "john.doe@example.com", // Applicant's email.
////                    LocalDate.now(), // Application date.
////                    "https://example.com/resume.pdf", // Resume URL.
////                    "I am excited to apply for this role.", // Cover letter.
////                    ApplicationStatus.SUBMITTED, // Example status.
////                    "Java, Spring, Hibernate" // Skills.
////            );
////
////            em.persist(application);
//
//            em.close();
//            emf.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        message = "Hello World!";
        String password = "adminadmin";
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);
        logger.info("Password: " + hashedPassword);
        logger.info("Salt: " + Base64.getEncoder().encodeToString(salt));

        String password2 = "recruiter";
        byte[] salt2 = PasswordUtil.generateSalt(); // New salt for each password
        String hashedPassword2 = PasswordUtil.hashPassword(password2, salt2);
        logger.info("Password Rec: " + hashedPassword2);
        logger.info("Salt: " + Base64.getEncoder().encodeToString(salt2));

        String password3 = "employee";
        byte[] salt3 = PasswordUtil.generateSalt(); // New salt for each password
        String hashedPassword3 = PasswordUtil.hashPassword(password3, salt3);
        logger.info("Password Emp: " + hashedPassword3);
        logger.info("Salt: " + Base64.getEncoder().encodeToString(salt3));

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}