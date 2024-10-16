package com.labor.laboreev2.listeners;

import com.labor.laboreev2.repositories.*;
import com.labor.laboreev2.repositories.interfaces.*;
import com.labor.laboreev2.security.CustomJDBCRealm;
import com.labor.laboreev2.services.AdminServiceImpl;
import com.labor.laboreev2.services.EmployeeServiceImpl;
import com.labor.laboreev2.services.RecruiterServiceImpl;
import com.labor.laboreev2.services.interfaces.AdminService;
import com.labor.laboreev2.services.interfaces.EmployeeService;
import com.labor.laboreev2.services.interfaces.RecruiterService;
import com.labor.laboreev2.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    private ServletContextEvent sce;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.sce = sce;
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        ApplicationRepository applicationRepository = new ApplicationRepositoryImpl(em);
        DepartmentRepository departmentRepository = new DepartmentRepositoryImpl(em);
        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(em);
        JobOfferRepository jobOfferRepository = new JobOfferRepositoryImpl(em);
        JobTitleRepository jobTitleRepository = new JobTitleRepositoryImpl(em);
        LeaveRequestRepository leaveRequestRepository = new LeaveRequestRepositoryImpl(em);
        PayRollRepository payRollRepository = new PayRollRepositoryImpl(em);
        RecruiterRepository recruiterRepository = new RecruiterRepositoryImpl(em);
        UserRepository userRepository = new UserRepositoryImpl(em);

        CustomJDBCRealm.setUserRepository(userRepository);

        AdminService adminService = new AdminServiceImpl(leaveRequestRepository, employeeRepository);
        EmployeeService employeeService = new EmployeeServiceImpl(leaveRequestRepository, employeeRepository);
        RecruiterService recruiterService = new RecruiterServiceImpl(jobOfferRepository, applicationRepository, recruiterRepository);

        sce.getServletContext().setAttribute("applicationRepository", applicationRepository);
        sce.getServletContext().setAttribute("departmentRepository", departmentRepository);
        sce.getServletContext().setAttribute("employeeRepository", employeeRepository);
        sce.getServletContext().setAttribute("jobOfferRepository", jobOfferRepository);
        sce.getServletContext().setAttribute("jobTitleRepository", jobTitleRepository);
        sce.getServletContext().setAttribute("leaveRequestRepository", leaveRequestRepository);
        sce.getServletContext().setAttribute("payRollRepository", payRollRepository);
        sce.getServletContext().setAttribute("recruiterRepository", recruiterRepository);
        sce.getServletContext().setAttribute("userRepository", userRepository);

        sce.getServletContext().setAttribute("adminService", adminService);
        sce.getServletContext().setAttribute("employeeService", employeeService);
        sce.getServletContext().setAttribute("recruiterService", recruiterService);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.closeEntityManagerFactory();
    }
}
