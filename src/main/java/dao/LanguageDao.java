/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.common.Language;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class LanguageDao {

    private static final Logger LOGGER = Logger.getLogger(LanguageDao.class.getName());
    private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM language";
    private static final String ADD_RESUME_LANGUAGE = "INSERT INTO language_resume (languageId, resumeId) values (?, ?);";
    private static final String SELECT_RESUME_LANGUAGES = "SELECT * FROM language_resume INNER JOIN language ON language_resume.languageId = language.languageId WHERE resumeId = ?;";
    private static final String DELETE_RESUME_LANGUAGES = "DELETE FROM language_resume WHERE resumeId = ?;";
    private static final String ADD_JOB_LANGUAGE = "INSERT INTO language_job (languageId, jobId) values (?, ?);";
    private static final String SELECT_JOB_LANGUAGES = "SELECT * FROM language_job INNER JOIN language ON language_job.languageId = language.languageId WHERE jobId = ?;";
    private static final String DELETE_JOB_LANGUAGES = "DELETE FROM language_job WHERE jobId = ?;";

    public List<Language> getAllLanguages() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_LANGUAGES); ResultSet rs = ps.executeQuery()) {
            List<Language> languages = new ArrayList<>();
            while (rs.next()) {
                languages.add(languageFromQueryResult(rs));
            }
            return languages;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void addResumeLanguage(List<Integer> languageIds, int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(ADD_RESUME_LANGUAGE)) {
            for (Integer languageId : languageIds) {
                ps.setInt(1, languageId);
                ps.setInt(2, resumeId);
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void addJobLanguage(List<Integer> languageIds, int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(ADD_JOB_LANGUAGE)) {
            for (Integer languageId : languageIds) {
                ps.setInt(1, languageId);
                ps.setInt(2, jobId);
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void deleteResumeLanguages(int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_RESUME_LANGUAGES)) {
            ps.setInt(1, resumeId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public List<Language> getResumeLanguages(int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_RESUME_LANGUAGES)) {
            ps.setInt(1, resumeId);
            List<Language> languages = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    languages.add(languageFromQueryResult(rs));
                }
            }
            return languages;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void deleteJobLanguages(int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_JOB_LANGUAGES)) {
            ps.setInt(1, jobId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public List<Language> getJobLanguages(int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_JOB_LANGUAGES)) {
            ps.setInt(1, jobId);
            List<Language> languages = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    languages.add(languageFromQueryResult(rs));
                }
            }
            return languages;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private Language languageFromQueryResult(ResultSet rs) throws SQLException {
        Language language = new Language();
        language.setLanguageId(rs.getInt("languageId"));
        language.setName(rs.getString("name"));
        return language;
    }

}
