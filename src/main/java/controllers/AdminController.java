/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AcademicLevelDao;
import dao.CompanyDao;
import dao.FieldDao;
import dao.JobDao;
import dao.JobTypeDao;
import dao.LanguageDao;
import dao.LocationDao;
import dao.ResumeDao;
import exceptions.DaoException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.account.Account;
import models.account.AccountType;
import models.common.Language;
import models.common.WorkDetails;
import models.job.Company;
import models.job.Job;
import models.job.JobType;
import models.resume.Resume;
import utils.NotificationType;
import utils.NotificationUtils;
import validation.ModelValidator;

/**
 *
 * @author Admin
 */
@MultipartConfig(
        fileSizeThreshold = 1000000 * 5, // 5mb
        maxFileSize = 1000000 * 10, // 10mb
        maxRequestSize = 1000000 * 15) // 15mb
@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        try {
            if (account.getAccountTypeId() == AccountType.JOB_SEEKER) {
                processSeekerGet(request, response);
            } else {
                processRecruiterGet(request, response);
            }
        } catch (DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        
    }
    
    private void processRecruiterGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String action = request.getParameter("action");
        Optional<Job> existingJob;
        if (action == null) {
            sendJobAdminPage(request, response);
        } else {
            request.setAttribute("action", action);
            switch (action) {
                case "notifications":
                case "applications":
                case "jobs":
                    // Not implemented
                    request.getRequestDispatcher("not_found.html").forward(request, response);
                    break;
                case "createjob":
                    if (hasCompanyInfo(getCurrentUser(request).getAccountId())) {
                        sendJobForm(request, response);
                    } else {
                        request.getRequestDispatcher("no_company.html").forward(request, response);
                    }
                    break;
                case "editjob":
                    existingJob = jobFromRequestIdParam(request);
                    if (existingJob.isPresent()) {
                        sendJobEditForm(request, response, existingJob.get());
                    } else {
                        sendNotFound(request, response);
                    }
                    break;
                case "company":
                    CompanyDao companyDao = new CompanyDao();
                    Company company = companyDao.getCompany(getCurrentUser(request).getAccountId()).orElse(null);
                    request.setAttribute("company", company);
                    request.getRequestDispatcher("company_form.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("not_found.html").forward(request, response);
            }
        }
        
    }
    
    private void sendResumeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        injectWorkDetailsOptions(request);
        request.getRequestDispatcher("resume_form.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        try {
            if (account.getAccountTypeId() == AccountType.JOB_SEEKER) {
                processSeekerPost(request, response);
            } else {
                processRecruiterPost(request, response);
            }
        } catch (DaoException ex) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    private void processRecruiterPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, DaoException {
        String action = request.getParameter("action");
        Optional<Job> existingJob;
        if (action == null) {
            request.getRequestDispatcher("not_found.html").forward(request, response);
        } else {
            request.setAttribute("action", action);
            switch (action) {
                case "createjob":
                    createJob(request, response);
                    break;
                case "editjob":
                    existingJob = jobFromRequestIdParam(request);
                    if (existingJob.isPresent()) {
                        editJob(request, response, existingJob.get());
                    } else {
                        sendNotFound(request, response);
                    }
                    break;
                case "deletejob":
                    existingJob = jobFromRequestIdParam(request);
                    if (existingJob.isPresent()) {
                        deleteJob(request, response, existingJob.get());
                    } else {
                        sendNotFound(request, response);
                    }
                    break;
                case "company":
                    saveOrUpdateCompany(request, response);
                    break;
                default:
                    request.getRequestDispatcher("not_found.html").forward(request, response);
            }
        }
        
    }
    
    private void processSeekerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String action = request.getParameter("action");
        if (action == null) {
            sendResumeAdminPage(request, response);
        } else {
            request.setAttribute("action", action);
            switch (action) {
                case "notifications":
                case "applications":
                case "jobs":
                    // Not implemented
                    request.getRequestDispatcher("not_found.html").forward(request, response);
                    break;
                case "createresume":
                    sendResumeForm(request, response);
                    break;
                case "editresume":
                    Optional<Resume> existingResume = resumeFromRequestIdParam(request);
                    if (existingResume.isPresent()) {
                        sendResumeEditForm(request, response, existingResume.get());
                    } else {
                        request.getRequestDispatcher("not_found.html").forward(request, response);
                    }
                    break;
                default:
                    sendNotFound(request, response);
            }
        }
    }
    
    private void processSeekerPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String action = request.getParameter("action");
        Optional<Resume> existingResume;
        if (action == null) {
            request.getRequestDispatcher("not_found.html").forward(request, response);
        } else {
            request.setAttribute("action", action);
            switch (action) {
                case "createresume":
                    createResume(request, response);
                    break;
                case "editresume":
                    existingResume = resumeFromRequestIdParam(request);
                    if (existingResume.isPresent()) {
                        editResume(request, response, existingResume.get());
                    } else {
                        request.getRequestDispatcher("not_found.html").forward(request, response);
                    }
                    break;
                case "deleteresume":
                    existingResume = resumeFromRequestIdParam(request);
                    if (existingResume.isPresent()) {
                        deleteResume(request, response, existingResume.get());
                    } else {
                        request.getRequestDispatcher("not_found.html").forward(request, response);
                    }
                    break;
                default:
                    sendNotFound(request, response);
            }
        }
    }
    
    private void sendNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("not_found.html").forward(request, response);
    }
    
    private void createResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException, NumberFormatException {
        Resume resume = parseResume(request);
        ModelValidator md = new ModelValidator();
        Map<String, String> resumeConstraints = md.validate(resume);
        resumeConstraints.putAll(md.validate(resume.getWorkDetails()));
        
        if (!resumeConstraints.isEmpty()) {
            deleteFile(resume.getWorkDetails().getImageUri());
            request.setAttribute("constraints", resumeConstraints);
            request.setAttribute("resume", resume);
            sendResumeForm(request, response);
            return;
        }
        ResumeDao resumeDao = new ResumeDao();
        resumeDao.addResume(resume);
        LanguageDao languageDao = new LanguageDao();
        languageDao.addResumeLanguage(resume.getWorkDetails().getLanguageIds(), resume.getResumeId());
        response.sendRedirect(request.getContextPath() + "/resumes?resumeId=" + resume.getResumeId());
    }
    
    private Resume parseResume(HttpServletRequest request) throws IOException, NumberFormatException, ServletException {
        String title = request.getParameter("title");
        String position = request.getParameter("position");
        Integer yearOfExperience = Integer.parseInt(request.getParameter("yearOfExp"));
        String bio = request.getParameter("bio");
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        Resume resume = new Resume();
        resume.setTitle(title);
        resume.setAccountId(getCurrentUser(request).getAccountId());
        resume.setPosition(position);
        resume.setYearOfExperience(yearOfExperience);
        resume.setBio(bio);
        resume.setStatus(status);
        resume.setViews(0);
        
        WorkDetails workDetails = parseWorkDetails(request);
        resume.setWorkDetails(workDetails);
        return resume;
    }
    
    private WorkDetails parseWorkDetails(HttpServletRequest request) throws ServletException, IOException, NumberFormatException {
        List<Integer> selectedLanguages = request.getParameterValues("language") != null
                ? Stream.of(request.getParameterValues("language")).map(Integer::parseInt).collect(Collectors.toList())
                : new ArrayList<>();
        Part imagePart = request.getPart("image");
        String imageUri = savePart(imagePart);
        Integer locationId = Integer.parseInt(request.getParameter("location"));
        Integer fieldId = Integer.parseInt(request.getParameter("field"));
        Integer levelId = Integer.parseInt(request.getParameter("level"));
        WorkDetails workDetails = new WorkDetails();
        workDetails.setLanguageIds(selectedLanguages);
        workDetails.setImageUri(imageUri);
        workDetails.setLocationId(locationId);
        workDetails.setFieldId(fieldId);
        workDetails.setLevelId(levelId);
        return workDetails;
    }
    
    private static Account getCurrentUser(HttpServletRequest request) {
        return (Account) request.getSession().getAttribute("account");
    }
    
    private String savePart(Part part) throws IOException {
        String saveDirectory = getServletContext().getInitParameter("UPLOAD_DIR");
        String uploadPath = getServletContext().getRealPath("") + saveDirectory;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String fileName = Long.toString(System.currentTimeMillis()) + getExtension(part.getSubmittedFileName());
        String absolutePath = uploadPath + File.separator + fileName;
        part.write(absolutePath);
        String relativePath = saveDirectory + File.separator + fileName;
        return relativePath;
    }
    
    private void deleteFile(String filename) {
        new File(getServletContext().getRealPath("") + filename).delete();
    }
    
    private String getExtension(String filename) {
        String[] parts = filename.split("\\.");
        return "." + parts[parts.length - 1];
    }
    
    private void sendResumeEditForm(HttpServletRequest request, HttpServletResponse response, Resume resume) throws DaoException, ServletException, IOException {
        LanguageDao languageDao = new LanguageDao();
        List<Language> resumeLanguages = languageDao.getResumeLanguages(resume.getResumeId());
        resume.getWorkDetails().setLanguageIds(resumeLanguages.stream().map(Language::getLanguageId).collect(Collectors.toList()));
        request.setAttribute("resume", resume);
        sendResumeForm(request, response);
    }
    
    private void injectWorkDetailsOptions(HttpServletRequest request) throws DaoException {
        LanguageDao languageDao = new LanguageDao();
        LocationDao locationDao = new LocationDao();
        FieldDao fieldDao = new FieldDao();
        AcademicLevelDao academicLevelDao = new AcademicLevelDao();
        request.setAttribute("languages", languageDao.getAllLanguages());
        request.setAttribute("locations", locationDao.getAllLocations());
        request.setAttribute("fields", fieldDao.getAllFields());
        request.setAttribute("academicLevels", academicLevelDao.getAllLevels());
    }
    
    private void editResume(HttpServletRequest request, HttpServletResponse response, Resume existingResume) throws IOException, NumberFormatException, ServletException, DaoException {
        ResumeDao resumeDao = new ResumeDao();
        Resume resume = parseResume(request);
        resume.setResumeId(existingResume.getResumeId());
        ModelValidator md = new ModelValidator();
        Map<String, String> resumeConstraints = md.validate(resume);
        resumeConstraints.putAll(md.validate(resume.getWorkDetails()));
        
        if (!resumeConstraints.isEmpty()) {
            deleteFile(getServletContext().getRealPath("") + resume.getWorkDetails().getImageUri());
            request.setAttribute("constraints", resumeConstraints);
            request.setAttribute("resume", resume);
            sendResumeForm(request, response);
            return;
        }
        
        LanguageDao languageDao = new LanguageDao();
        languageDao.deleteResumeLanguages(existingResume.getResumeId());
        languageDao.addResumeLanguage(resume.getWorkDetails().getLanguageIds(), resume.getResumeId());
        resumeDao.updateResume(resume);
        deleteFile(getServletContext().getRealPath("") + existingResume.getWorkDetails().getImageUri());
        response.sendRedirect(request.getContextPath() + "/resumes?resumeId=" + resume.getResumeId());
    }
    
    private void deleteResume(HttpServletRequest request, HttpServletResponse response, Resume resume) throws ServletException, ServletException, IOException, IOException, DaoException {
        LanguageDao languageDao = new LanguageDao();
        ResumeDao resumeDao = new ResumeDao();
        languageDao.deleteResumeLanguages(resume.getResumeId());
        resumeDao.deleteResume(resume.getResumeId());
        deleteFile(resume.getWorkDetails().getImageUri());
        response.sendRedirect("admin");
    }
    
    private void sendResumeAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException, IOException, DaoException {
        ResumeDao resumeDao = new ResumeDao();
        List<Resume> accountResumes = resumeDao.getResumesOfAccount(getCurrentUser(request).getAccountId());
        request.setAttribute("resumes", accountResumes);
        request.getRequestDispatcher("admin_seeker.jsp").forward(request, response);
    }
    
    private void sendJobAdminPage(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServletException, IOException {
        JobDao jobDao = new JobDao();
        List<Job> accountJobs = jobDao.getAllJobsOfAccount(getCurrentUser(request).getAccountId());
        request.setAttribute("jobs", accountJobs);
        request.getRequestDispatcher("admin_recruiter.jsp").forward(request, response);
    }
    
    private void sendJobForm(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServletException, IOException {
        JobTypeDao jobTypeDao = new JobTypeDao();
        List<JobType> jobTypes = jobTypeDao.getAllJobTypes();
        request.setAttribute("jobTypes", jobTypes);
        injectWorkDetailsOptions(request);
        request.getRequestDispatcher("job_form.jsp").forward(request, response);
    }
    
    private boolean hasCompanyInfo(int accountId) throws DaoException {
        CompanyDao companyDao = new CompanyDao();
        return companyDao.getCompany(accountId).isPresent();
    }
    
    private void saveOrUpdateCompany(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DaoException {
        Company company = parseCompany(request);
        
        ModelValidator modelValidator = new ModelValidator();
        Map<String, String> constraints = modelValidator.validate(company);
        request.setAttribute("company", company);
        
        if (!constraints.isEmpty()) {
            deleteFile(company.getImageUri());
            request.setAttribute("constraints", constraints);
        } else {
            addOrUpdateCompany(company);
            NotificationUtils.setNotification(request, NotificationType.success, "Thông tin doanh nghiệp cập nhật thành công");
        }
        request.getRequestDispatcher("company_form.jsp").forward(request, response);
        
    }
    
    private Company parseCompany(HttpServletRequest request) throws ServletException, IOException, NumberFormatException {
        int companyId = Integer.parseInt(request.getParameter("companyId"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        Part imagePart = request.getPart("image");
        String imageUri = savePart(imagePart);
        Company company = new Company();
        company.setCompanyId(companyId);
        company.setName(name);
        company.setAddress(address);
        company.setDescription(description);
        company.setImageUri(imageUri);
        return company;
    }
    
    private void addOrUpdateCompany(Company company) throws DaoException {
        CompanyDao companyDao = new CompanyDao();
        Optional<Company> existingCompanyProfile = companyDao.getCompany(company.getCompanyId());
        if (existingCompanyProfile.isPresent()) {
            deleteFile(existingCompanyProfile.get().getImageUri());
            companyDao.updateCompany(company);
        } else {
            companyDao.addCompany(company);
        }
        
    }
    
    private void createJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException, NumberFormatException {
        Job job = parseJob(request);
        System.out.println(job.getWorkDetails().getLanguageIds().toString());
        ModelValidator md = new ModelValidator();
        Map<String, String> jobConstraints = md.validate(job);
        jobConstraints.putAll(md.validate(job.getWorkDetails()));
        
        if (!jobConstraints.isEmpty()) {
            deleteFile(job.getWorkDetails().getImageUri());
            request.setAttribute("constraints", jobConstraints);
            request.setAttribute("job", job);
            sendJobForm(request, response);
            return;
        }
        JobDao jobDao = new JobDao();
        jobDao.addJob(job);
        LanguageDao languageDao = new LanguageDao();
        languageDao.addJobLanguage(job.getWorkDetails().getLanguageIds(), job.getJobId());
        response.sendRedirect(request.getContextPath() + "/jobs?jobId=" + job.getJobId());
    }
    
    private Job parseJob(HttpServletRequest request) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        BigDecimal startingSalary = BigDecimal.valueOf(Double.parseDouble(request.getParameter("startingSalary")));
        Integer jobTypeId = Integer.parseInt(request.getParameter("jobType"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setStartingSalary(startingSalary);
        job.setJobTypeId(jobTypeId);
        job.setStatus(status);
        job.setLastModified(LocalDate.now());
        job.setAccountId(getCurrentUser(request).getAccountId());
        
        WorkDetails workDetails = parseWorkDetails(request);
        job.setWorkDetails(workDetails);
        return job;
    }
    
    private void sendJobEditForm(HttpServletRequest request, HttpServletResponse response, Job job) throws DaoException, ServletException, IOException {
        LanguageDao languageDao = new LanguageDao();
        List<Language> jobLanguages = languageDao.getJobLanguages(job.getJobId());
        job.getWorkDetails().setLanguageIds(jobLanguages.stream().map(Language::getLanguageId).collect(Collectors.toList()));
        request.setAttribute("job", job);
        sendJobForm(request, response);
    }
    
    private void editJob(HttpServletRequest request, HttpServletResponse response, Job existingJob) throws DaoException, IOException, ServletException {
        JobDao jobDao = new JobDao();
        Job job = parseJob(request);
        job.setJobId(existingJob.getJobId());
        ModelValidator md = new ModelValidator();
        Map<String, String> jobConstraints = md.validate(job);
        jobConstraints.putAll(md.validate(job.getWorkDetails()));
        
        if (!jobConstraints.isEmpty()) {
            deleteFile(getServletContext().getRealPath("") + job.getWorkDetails().getImageUri());
            request.setAttribute("constraints", jobConstraints);
            request.setAttribute("resume", job);
            sendJobForm(request, response);
            return;
        }
        
        LanguageDao languageDao = new LanguageDao();
        languageDao.deleteJobLanguages(existingJob.getJobId());
        languageDao.addJobLanguage(job.getWorkDetails().getLanguageIds(), job.getJobId());
        jobDao.updateJob(job);
        deleteFile(getServletContext().getRealPath("") + existingJob.getWorkDetails().getImageUri());
        response.sendRedirect(request.getContextPath() + "/jobs?jobId=" + job.getJobId());
    }
    
    private Optional<Resume> resumeFromRequestIdParam(HttpServletRequest request) throws DaoException {
        ResumeDao resumeDao = new ResumeDao();
        Optional<Resume> opResume = null;
        String resumeId = request.getParameter("resumeId");
        if (resumeId == null || !(opResume = resumeDao.getResume(Integer.parseInt(resumeId))).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(opResume.get());
    }
    
    private Optional<Job> jobFromRequestIdParam(HttpServletRequest request) throws DaoException {
        JobDao jobDao = new JobDao();
        Optional<Job> opJob = null;
        String jobId = request.getParameter("jobId");
        if (jobId == null || !(opJob = jobDao.getJob(Integer.parseInt(jobId))).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(opJob.get());
    }
    
    private void deleteJob(HttpServletRequest request, HttpServletResponse response, Job job) throws DaoException, IOException {
        LanguageDao languageDao = new LanguageDao();
        JobDao jobDao = new JobDao();
        languageDao.deleteJobLanguages(job.getJobId());
        jobDao.deleteJob(job.getJobId());
        deleteFile(job.getWorkDetails().getImageUri());
        response.sendRedirect("admin");
    }
}
