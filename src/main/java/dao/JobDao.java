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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.common.WorkDetails;
import models.job.Job;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class JobDao {
    
    private static final Logger LOGGER = Logger.getLogger(JobDao.class.getName());
    private static final String SELECT_JOB = "SELECT * FROM job WHERE jobId = ?;";
    private static final String SELECT_JOB_OF_ACCOUNT = "SELECT * FROM job WHERE accountId = ? AND deleted = 0;";
    private static final String INSERT_JOB = "INSERT INTO job (jobTypeId, title, status, views, description, accountId, startingSalary, imageUri, locationId, fieldId, levelId, lastModified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_JOB = "UPDATE job set jobTypeId = ?, title = ?, status = ?, description = ?, startingSalary = ?, imageUri = ?, locationId = ?, fieldId = ?, levelId = ?, lastModified = ? WHERE jobId = ?;";
    private static final String DELETE_JOB = "UPDATE job set deleted = 1 WHERE jobId = ?;";
    private static final String INCREASE_JOB_VIEWS = "UPDATE job SET views = views + ? WHERE jobId = ?;";
    private static final String SWITCH_JOB_STATUS = "UPDATE job SET status = 1 ^ status WHERE jobId =?;";
    
    public Optional<Job> getJob(int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_JOB)) {
            ps.setInt(1, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.ofNullable(rs.next() ? jobFromQueryResult(rs) : null);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public List<Job> getAllJobsOfAccount(int accountId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_JOB_OF_ACCOUNT)) {
            ps.setInt(1, accountId);
            List<Job> jobs = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    jobs.add(jobFromQueryResult(rs));
                }
            }
            return jobs;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void addJob(Job job) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_JOB, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, job.getJobTypeId());
            ps.setString(2, job.getTitle());
            ps.setBoolean(3, job.getStatus());
            ps.setInt(4, job.getViews());
            ps.setString(5, job.getDescription());
            ps.setInt(6, job.getAccountId());
            ps.setBigDecimal(7, job.getStartingSalary());
            ps.setString(8, job.getWorkDetails().getImageUri());
            ps.setInt(9, job.getWorkDetails().getLocationId());
            ps.setInt(10, job.getWorkDetails().getFieldId());
            ps.setInt(11, job.getWorkDetails().getLevelId());
            ps.setDate(12, Date.valueOf(job.getLastModified()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    job.setJobId(rs.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void updateJob(Job job) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_JOB)) {
            ps.setInt(1, job.getJobTypeId());
            ps.setString(2, job.getTitle());
            ps.setBoolean(3, job.getStatus());
            ps.setString(4, job.getDescription());
            ps.setBigDecimal(5, job.getStartingSalary());
            ps.setString(6, job.getWorkDetails().getImageUri());
            ps.setInt(7, job.getWorkDetails().getLocationId());
            ps.setInt(8, job.getWorkDetails().getFieldId());
            ps.setInt(9, job.getWorkDetails().getLevelId());
            ps.setDate(10, Date.valueOf(job.getLastModified()));
            ps.setInt(11, job.getJobId());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void deleteJob(int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_JOB)) {
            ps.setInt(1, jobId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void switchJobStatus(int jobId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SWITCH_JOB_STATUS)) {
            ps.setInt(1, jobId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    private Job jobFromQueryResult(ResultSet rs) throws SQLException {
        Job job = new Job();
        job.setJobId(rs.getInt("jobId"));
        job.setAccountId(rs.getInt("accountId"));
        job.setStatus(rs.getBoolean("status"));
        job.setViews(rs.getInt("views"));
        job.setDescription(rs.getString("description"));
        job.setTitle(rs.getString("title"));
        job.setLastModified(rs.getDate("lastModified").toLocalDate());
        job.setStatus(rs.getBoolean("status"));
        job.setStartingSalary(rs.getBigDecimal("startingSalary"));
        job.setJobTypeId(rs.getInt("jobTypeId"));
        job.setDeleted(rs.getBoolean("deleted"));
        
        WorkDetails workDetails = new WorkDetails();
        workDetails.setImageUri(rs.getString("imageUri"));
        workDetails.setLocationId(rs.getInt("locationId"));
        workDetails.setFieldId(rs.getInt("fieldId"));
        workDetails.setLevelId(rs.getInt("levelId"));
        
        job.setWorkDetails(workDetails);
        
        return job;
    }
    
    public void increaseJobViewCounts(Map<Integer, Integer> jobIdToCount) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INCREASE_JOB_VIEWS)) {
            for (Map.Entry<Integer, Integer> entry : jobIdToCount.entrySet()) {
                ps.setInt(1, entry.getValue());
                ps.setInt(2, entry.getKey());
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
}
