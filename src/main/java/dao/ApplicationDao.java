/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.DaoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.application.Application;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class ApplicationDao {

    private static final Logger LOGGER = Logger.getLogger(ApplicationDao.class.getName());
    private static final String SELECT_APPLICATIONS_OF_SEEKER = "SELECT ap.* FROM account ac, resume re, application ap WHERE ac.accountId = re.accountId AND re.resumeId = ap.resumeId WHERE ac.accountId = ?;";
    private static final String SELECT_APPLICATIONS_OF_RECRUITER = "SELECT ap.* FROM account ac, job jo, application ap WHERE ac.accountId = jo.accountId AND jo.jobId = ap.jobId WHERE ac.accountId = ?;";
    private static final String INSERT_APPLICATION = "INSERT INTO application (jobId, resumeId, createdDate, message, statusId) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_APPLICATION_STATUS = "UPDATE application SET statusId = ? WHERE applicationId = ?;";
    private static final String CHECK_APPLICATION_STATUS = "SELECT * FROM application WHERE jobId = ? and ResumeId = ? AND statusId = ?";

    public List<Application> getSeekerApplications(int accountId) throws SQLException {
        List<Application> applications = new ArrayList<>();
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_APPLICATIONS_OF_SEEKER)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    applications.add(applicationFromQueryResult(rs));
                }
            }
        }
        return applications;
    }

    public List<Application> getRecruiterApplications(int accountId) throws SQLException {
        List<Application> applications = new ArrayList<>();
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_APPLICATIONS_OF_RECRUITER)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    applications.add(applicationFromQueryResult(rs));
                }
            }
        }
        return applications;
    }

    public boolean checkApplicationStatus(int jobId, int resumeId, int statusId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(CHECK_APPLICATION_STATUS)) {
            ps.setInt(1, jobId);
            ps.setInt(2, resumeId);
            ps.setInt(3, statusId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new DaoException();
        }
    }

    public void addApplication(Application application) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_APPLICATION)) {
            ps.setInt(1, application.getJobId());
            ps.setInt(2, application.getResumeId());
            ps.setDate(3, Date.valueOf(application.getCreatedTime()));
            ps.setString(4, application.getMessage());
            ps.setInt(5, application.getStatusId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new DaoException();
        }
    }

    public void updateApplicationStatus(int applicationId, int statusId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_APPLICATION_STATUS)) {
            ps.setInt(1, statusId);
            ps.setInt(2, applicationId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new DaoException();
        }
    }

    private Application applicationFromQueryResult(ResultSet rs) throws SQLException {
        Application application = new Application();
        application.setApplicationId(rs.getInt("applicationId"));
        application.setJobId(rs.getInt("jobId"));
        application.setResumeId(rs.getInt("resumeId"));
        application.setMessage(rs.getString("message"));
        application.setStatusId(rs.getInt("statusId"));
        return application;
    }
}
