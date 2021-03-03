/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AcademicLevelDao;
import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.CompanyDao;
import dao.FieldDao;
import dao.LanguageDao;
import dao.LocationDao;
import dao.JobDao;
import dao.JobTypeDao;
import exceptions.DaoException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.FilterableJobDao;
import models.account.Account;
import models.common.AcademicLevel;
import models.common.Field;
import models.common.Language;
import models.common.Location;
import models.job.Job;
import models.job.JobType;

/**
 *
 * @author Admin
 */
@WebServlet(name = "JobListController", urlPatterns = {"/jobs"})
public class JobListController extends HttpServlet {

    Optional<Job> opJob;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String jobId = request.getParameter("jobId");
            JobDao jobDao = new JobDao();
            if (jobId != null) {
                if ((opJob = jobDao.getJob(Integer.parseInt(jobId))).isPresent()) {
                    sendJobDetails(request, response, opJob.get());
                } else {
                    request.getRequestDispatcher("not_found.html").forward(request, response);
                }
                return;
            }

            LanguageDao languageDao = new LanguageDao();
            LocationDao locationDao = new LocationDao();
            FieldDao fieldDao = new FieldDao();
            AcademicLevelDao academicLevelDao = new AcademicLevelDao();
            JobTypeDao jobTypeDao = new JobTypeDao();
            FilterableJobDao jobFilter = buildJobFilter(request);
            List<Job> jobs = jobFilter.getFilteredJobs();
            setJobLanguagesIds(jobs, languageDao);

            request.setAttribute("jobs", jobs);
            request.setAttribute("jobTypes", jobTypeDao.getAllJobTypes());
            request.setAttribute("languages", languageDao.getAllLanguages());
            request.setAttribute("locations", locationDao.getAllLocations());
            request.setAttribute("fields", fieldDao.getAllFields());
            request.setAttribute("academicLevels", academicLevelDao.getAllLevels());
            request.getRequestDispatcher("job_list.jsp").forward(request, response);

        } catch (DaoException | IOException | NumberFormatException | ServletException ex) {
            Logger.getLogger(JobListController.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void setJobLanguagesIds(List<Job> jobs, LanguageDao languageDao) throws DaoException {
        for (Job job : jobs) {
            job.getWorkDetails().setLanguageIds(languageDao
                    .getJobLanguages(job.getJobId())
                    .stream()
                    .map(Language::getLanguageId)
                    .collect(Collectors.toList()));
        }
    }

    private void sendJobDetails(HttpServletRequest request, HttpServletResponse response, Job job) throws DaoException, ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();
        LanguageDao languageDao = new LanguageDao();
        LocationDao locationDao = new LocationDao();
        AcademicLevelDao academicLevelDao = new AcademicLevelDao();
        FieldDao fieldDao = new FieldDao();
        JobTypeDao jobTypeDao = new JobTypeDao();
        CompanyDao companyDao = new CompanyDao();
        JobType jobType = jobTypeDao.getJobType(job.getJobTypeId()).get();
        Account jobOwner = accountDao.getAccountById(job.getAccountId()).get();
        List<Language> jobLanguages = languageDao.getJobLanguages(job.getJobId());
        Location jobLocation = locationDao.getLocation(job.getWorkDetails().getLocationId()).get();
        AcademicLevel jobLevel = academicLevelDao.getLevel(job.getWorkDetails().getLevelId()).get();
        Field jobField = fieldDao.getField(job.getWorkDetails().getFieldId()).get();
        job.setCompany(companyDao.getCompany(job.getAccountId()).get());

        request.setAttribute("account", jobOwner);
        request.setAttribute("job", job);
        request.setAttribute("jobType", jobType);
        request.setAttribute("jobLanguages", jobLanguages);
        request.setAttribute("jobLocation", jobLocation);
        request.setAttribute("jobAcademicLevel", jobLevel);
        request.setAttribute("jobField", jobField);

        request.getRequestDispatcher("job_detail.jsp").forward(request, response);
    }

    private FilterableJobDao buildJobFilter(HttpServletRequest request) throws NumberFormatException {
        FilterableJobDao jobFilter = new FilterableJobDao();
        String ftAccountId = request.getParameter("accountId");
        String ftKeyword = request.getParameter("keyword");
        String ftMinStartingSalary = request.getParameter("minSalary");
        String ftLocationId = request.getParameter("locationId");
        String[] ftLanguageIds = request.getParameterValues("languageId");
        String ftFieldId = request.getParameter("fieldId");
        String ftLevelId = request.getParameter("levelId");
        if (ftAccountId != null) {
            int accountId = Integer.parseInt(ftAccountId);
            request.setAttribute("accountId", accountId);
            jobFilter.setAccountId(accountId);
        }
        if (ftKeyword != null) {
            request.setAttribute("keyword", ftKeyword);
            jobFilter.setKeywords(ftKeyword);
        }
        if (ftMinStartingSalary != null) {
            BigDecimal minSalary = BigDecimal.valueOf(Double.parseDouble(ftMinStartingSalary));
            request.setAttribute("minSalary", minSalary);
            jobFilter.setMinStartingSalary(minSalary);
        }
        if (ftLocationId != null) {
            int locationId = Integer.parseInt(ftLocationId);
            request.setAttribute("locationId", locationId);
            jobFilter.setLocationId(locationId);
        }
        if (ftLanguageIds != null) {
            List<Integer> languageIds = Stream.of(ftLanguageIds).map(Integer::parseInt).collect(Collectors.toList());
            request.setAttribute("languageId", languageIds);
            jobFilter.setLanguageIds(languageIds);
        }
        if (ftFieldId != null) {
            int fieldId = Integer.parseInt(ftFieldId);
            request.setAttribute("fieldId", fieldId);
            jobFilter.setFieldId(fieldId);
        }
        if (ftLevelId != null) {
            int levelId = Integer.parseInt(ftLevelId);
            request.setAttribute("levelId", levelId);
            jobFilter.setMinLevelId(levelId);
        }
        return jobFilter;
    }
}
