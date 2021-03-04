/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dao.JobDao;
import dao.ResumeDao;
import exceptions.DaoException;
import java.io.IOException;
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
import models.job.Job;
import models.resume.Resume;

/**
 *
 * @author Admin
 */
public class AuthFilter implements Filter {

    public static final String SESSION_ACCOUNT_KEY = "account";

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
                String resumeId = request.getParameter("resumeId");
                if (resumeId != null) {
                    ResumeDao resumeDao = new ResumeDao();
                    Optional<Resume> opResume = resumeDao.getResume(Integer.parseInt(resumeId));
                    if (opResume.isPresent() && opResume.get().getAccountId() != account.getAccountId()) {
                        request.getRequestDispatcher("unauthorized.html").forward(request, response);
                        return;
                    }
                }
                String jobId = request.getParameter("jobId");

                if (jobId != null && !httpRequest.getParameter("action").equals("apply")) {
                    JobDao jobDao = new JobDao();
                    Optional<Job> opJob = jobDao.getJob(Integer.parseInt(jobId));
                    if (opJob.isPresent() && opJob.get().getAccountId() != account.getAccountId()) {
                        request.getRequestDispatcher("unauthorized.html").forward(request, response);
                        return;
                    }
                }
                chain.doFilter(request, response);

            } catch (DaoException ex) {
                Logger.getLogger(AuthFilter.class.getName()).log(Level.SEVERE, null, ex);
                request.getRequestDispatcher("error.html").forward(request, response);
            }

        }
    }

    @Override
    public void destroy() {
    }

}
