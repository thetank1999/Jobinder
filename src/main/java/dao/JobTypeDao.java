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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.job.JobType;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class JobTypeDao {

    private static final Logger LOGGER = Logger.getLogger(JobTypeDao.class.getName());
    private static final String SELECT_ALL_JOB_TYPES = "SELECT * FROM jobType;";
    private static final String SELECT_JOB_TYPE = "SELECT * FROM jobType WHERE jobTypeId = ?;";

    public List<JobType> getAllJobTypes() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_JOB_TYPES); ResultSet rs = ps.executeQuery()) {
            List<JobType> jobTypes = new ArrayList<>();
            while (rs.next()) {
                jobTypes.add(jobTypeFromQueryResult(rs));
            }
            return jobTypes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public Optional<JobType> getJobType(int jobTypeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_JOB_TYPE)) {
            ps.setInt(1, jobTypeId);
            try (ResultSet rs = ps.executeQuery();) {
                return Optional.ofNullable(rs.next() ? jobTypeFromQueryResult(rs) : null);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private JobType jobTypeFromQueryResult(ResultSet rs) throws SQLException {
        JobType jobType = new JobType();
        jobType.setJobTypeId(rs.getInt("jobTypeId"));
        jobType.setName(rs.getString("name"));
        return jobType;
    }
}
