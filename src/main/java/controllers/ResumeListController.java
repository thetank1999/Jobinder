/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AcademicLevelDao;
import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.FieldDao;
import dao.LanguageDao;
import dao.LocationDao;
import dao.ResumeDao;
import exceptions.DaoException;
import java.io.IOException;
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
import models.account.Account;
import models.common.AcademicLevel;
import models.common.Field;
import models.common.Language;
import models.common.Location;
import models.resume.Resume;
import dao.FilterableResumeDao;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ResumeListController", urlPatterns = {"/resumes"})
public class ResumeListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String resumeId = request.getParameter("resumeId");
            if (resumeId != null) {
                sendResumeDetails(request, response, Integer.parseInt(resumeId));
                return;
            }

            LanguageDao languageDao = new LanguageDao();
            LocationDao locationDao = new LocationDao();
            FieldDao fieldDao = new FieldDao();
            AcademicLevelDao academicLevelDao = new AcademicLevelDao();
            FilterableResumeDao resumeFilter = buildResumeFilter(request);

            List<Resume> resumes = resumeFilter.getFilteredResumes();

            setResumeLanguagesIds(resumes, languageDao);

            request.setAttribute("resumes", resumes);
            request.setAttribute("languages", languageDao.getAllLanguages());
            request.setAttribute("locations", locationDao.getAllLocations());
            request.setAttribute("fields", fieldDao.getAllFields());
            request.setAttribute("academicLevels", academicLevelDao.getAllLevels());
            request.getRequestDispatcher("resume_list.jsp").forward(request, response);

        } catch (DaoException | IOException | NumberFormatException | ServletException ex) {
            Logger.getLogger(ResumeListController.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void setResumeLanguagesIds(List<Resume> resumes, LanguageDao languageDao) throws DaoException {
        for (Resume resume : resumes) {
            resume.getWorkDetails().setLanguageIds(languageDao
                    .getResumeLanguages(resume.getResumeId())
                    .stream()
                    .map(Language::getLanguageId)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void sendResumeDetails(HttpServletRequest request, HttpServletResponse response, int resumeId) throws DaoException, ServletException, IOException {
        ResumeDao resumeDao = new ResumeDao();
        AccountDao accountDao = new AccountDaoImpl();
        LanguageDao languageDao = new LanguageDao();
        LocationDao locationDao = new LocationDao();
        AcademicLevelDao academicLevelDao = new AcademicLevelDao();
        FieldDao fieldDao = new FieldDao();
        Optional<Resume> opResume = resumeDao.getResume(resumeId);
        if (!opResume.isPresent()) {
            request.getRequestDispatcher("not_found.html").forward(request, response);
        }
        Resume resume = opResume.get();
        Account resumeOwner = accountDao.getAccountById(resume.getAccountId()).get();
        List<Language> resumeLanguages = languageDao.getResumeLanguages(resumeId);
        Location resumeLocation = locationDao.getLocation(resume.getWorkDetails().getLocationId()).get();
        AcademicLevel resumeLevel = academicLevelDao.getLevel(resume.getWorkDetails().getLevelId()).get();
        Field resumeField = fieldDao.getField(resume.getWorkDetails().getFieldId()).get();

        request.setAttribute("account", resumeOwner);
        request.setAttribute("resume", resume);
        request.setAttribute("resumeLanguages", resumeLanguages);
        request.setAttribute("resumeLocation", resumeLocation);
        request.setAttribute("resumeAcademicLevel", resumeLevel);
        request.setAttribute("resumeField", resumeField);

        request.getRequestDispatcher("resume_detail.jsp").forward(request, response);
    }

    private FilterableResumeDao buildResumeFilter(HttpServletRequest request) throws NumberFormatException {
        FilterableResumeDao resumeFilter = new FilterableResumeDao();
        String ftAccountId = request.getParameter("accountId");
        String ftKeyword = request.getParameter("keyword");
        String ftMinYearOfExperience = request.getParameter("minYearOfExperience");
        String ftLocationId = request.getParameter("locationId");
        String[] ftLanguageIds = request.getParameterValues("languageId");
        String ftFieldId = request.getParameter("fieldId");
        String ftLevelId = request.getParameter("levelId");
        if (ftAccountId != null) {
            int accountId = Integer.parseInt(ftAccountId);
            request.setAttribute("accountId", accountId);
            resumeFilter.setAccountId(accountId);
        }
        if (ftKeyword != null) {
            request.setAttribute("keyword", ftKeyword);
            resumeFilter.setKeywords(ftKeyword);
        }
        if (ftMinYearOfExperience != null) {
            int minYearOfExp = Integer.parseInt(ftMinYearOfExperience);
            request.setAttribute("minYearOfExperience", minYearOfExp);
            resumeFilter.setMinYearOfExperience(minYearOfExp);
        }
        if (ftLocationId != null) {
            int locationId = Integer.parseInt(ftLocationId);
            request.setAttribute("locationId", locationId);
            resumeFilter.setLocationId(locationId);
        }
        if (ftLanguageIds != null) {
            List<Integer> languageIds = Stream.of(ftLanguageIds).map(Integer::parseInt).collect(Collectors.toList());
            request.setAttribute("languageId", languageIds);
            resumeFilter.setLanguageIds(languageIds);
        }
        if (ftFieldId != null) {
            int fieldId = Integer.parseInt(ftFieldId);
            request.setAttribute("fieldId", fieldId);
            resumeFilter.setFieldId(fieldId);
        }
        if (ftLevelId != null) {
            int levelId = Integer.parseInt(ftLevelId);
            request.setAttribute("levelId", levelId);
            resumeFilter.setMinLevelId(levelId);
        }
        return resumeFilter;
    }
}
