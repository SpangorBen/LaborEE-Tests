package com.labor.laboreev2.servlets;

import com.labor.laboreev2.models.Application;
import com.labor.laboreev2.models.JobOffer;
import com.labor.laboreev2.models.enums.ApplicationStatus;
import com.labor.laboreev2.repositories.ApplicationRepositoryImpl;
import com.labor.laboreev2.repositories.JobOfferRepositoryImpl;
import com.labor.laboreev2.repositories.interfaces.ApplicationRepository;
import com.labor.laboreev2.repositories.interfaces.JobOfferRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/applyForJob")
@MultipartConfig
public class ApplyForJobServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(ApplyForJobServlet.class.getName());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("labor");
    private final EntityManager em = emf.createEntityManager();
    private ApplicationRepository applicationRepository = new ApplicationRepositoryImpl(em);
    private JobOfferRepository jobOfferRepository = new JobOfferRepositoryImpl(em);



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Get the jobOfferId from the request parameter
            Long jobOfferId = Long.parseLong(request.getParameter("jobOfferId"));

            // 2. Fetch the JobOffer from the database
            JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                    .orElseThrow(() -> new RuntimeException("Job offer not found with ID: " + jobOfferId));

            // 3. Set the jobOffer as a request attribute
            request.setAttribute("jobOffer", jobOffer);

            // 4. Forward the request to the applyForJob.jsp
            request.getRequestDispatcher("/recruits/application.jsp").forward(request, response);

        } catch (NumberFormatException ex) {
            // Handle parsing error for jobOfferId
            logger.log(Level.SEVERE, "Invalid job offer ID", ex);
            // ... display an error message to the user ...
        } catch (Exception ex) {
            // Handle other exceptions (database errors, etc.)
            logger.log(Level.SEVERE, "Error retrieving job offer", ex);
            // ... display an error message to the user ...
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Retrieve form data
            Long jobOfferId = Long.parseLong(request.getParameter("jobOfferId"));
            String applicantName = request.getParameter("applicantName");
            String email = request.getParameter("email");
            String skills = request.getParameter("skills");
            String coverLetter = request.getParameter("coverLetter");

            // 2. Get the uploaded resume file
            Part filePart = request.getPart("resume");
            String fileName = getFileName(filePart);

            // 3. Save the resume file to a designated directory
            String uploadDirectory = getServletContext().getRealPath("/uploads"); // Adjust path if needed
            String filePath = uploadDirectory + File.separator + fileName;
            saveFile(filePart.getInputStream(), filePath);

            // 4. Retrieve the JobOffer
            JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                    .orElseThrow(() -> new RuntimeException("Job Offer not found"));

            // 5. Create the Application object
            Application application = new Application(jobOffer, applicantName, email, LocalDate.now(), filePath, coverLetter, ApplicationStatus.SUBMITTED, skills);

            // 6. Save the application to the database
            applicationRepository.save(application);

            // 7. Redirect to a success page
            response.sendRedirect(request.getContextPath() + "/jobOffers");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error processing job application", ex);
            // ... Handle exceptions (display error message to the user) ...
        }
    }

    // Helper methods for file handling
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    private void saveFile(InputStream inputStream, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.copy(inputStream, path);
    }
}