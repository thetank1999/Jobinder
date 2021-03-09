/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dao.ApplicationDao;
import dao.JobDao;
import dao.ResumeDao;
import exceptions.DaoException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.account.Account;
import models.account.AccountType;
import models.application.Application;
import models.job.Job;
import models.resume.Resume;

/**
 *
 * @author Admin
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        System.out.println("AuthFilter working...");
        // Not authenticated
        if (session.getAttribute("account") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/signin");
        } else {
            try {
                Account account = (Account) session.getAttribute("account");
                if (unauthorizedResumeActions(request, account, response)
                        || unauthorizedJobActions(request, account, response)
                        || unauthorizedApplicationActions(request, account)) {
                    request.getRequestDispatcher("unauthorized.html").forward(request, response);
                    return;
                }

                chain.doFilter(request, response);

            } catch (DaoException ex) {
                Logger.getLogger(AuthFilter.class.getName()).log(Level.SEVERE, null, ex);
                request.getRequestDispatcher("error.html").forward(request, response);
            }

        }
    }

    private boolean unauthorizedApplicationActions(ServletRequest request, Account account) throws NumberFormatException, DaoException {
        String applicationId = request.getParameter("applicationId");
        if (applicationId != null) {
            Integer applId = Integer.parseInt(applicationId);
            ApplicationDao applicationDao = new ApplicationDao();
            List<Application> userApplications = account.getAccountTypeId() == AccountType.JOB_SEEKER
                    ? applicationDao.getSeekerApplications(account.getAccountId())
                    : applicationDao.getRecruiterApplications(account.getAccountId());
            if (!userApplications.stream().anyMatch(ap -> ap.getApplicationId() == applId)) {
                return true;
            }
        }
        return false;
    }

    private boolean unauthorizedJobActions(ServletRequest request, Account account, ServletResponse response) throws DaoException, NumberFormatException, ServletException, IOException {
        String jobId = request.getParameter("jobId");
        if (jobId != null && !request.getParameter("action").equals("apply")) {
            JobDao jobDao = new JobDao();
            Optional<Job> opJob = jobDao.getJob(Integer.parseInt(jobId));
            if (opJob.isPresent() && opJob.get().getAccountId() != account.getAccountId()) {
                return true;
            }
        }
        return false;
    }

    private boolean unauthorizedResumeActions(ServletRequest request, Account account, ServletResponse response) throws DaoException, ServletException, IOException, NumberFormatException {
        String resumeId = request.getParameter("resumeId");
        if (resumeId != null) {
            ResumeDao resumeDao = new ResumeDao();
            Optional<Resume> opResume = resumeDao.getResume(Integer.parseInt(resumeId));
            if (opResume.isPresent() && opResume.get().getAccountId() != account.getAccountId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }

}
