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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.common.WorkDetails;
import models.resume.Resume;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class ResumeDao {
    
    private static final Logger LOGGER = Logger.getLogger(ResumeDao.class.getName());
    private static final String SELECT_RESUME = "SELECT * FROM resume WHERE resumeId = ?;";
    private static final String SELECT_RESUMES_OF_ACCOUNT = "SELECT * FROM resume WHERE accountId = ? AND deleted = 0;";
    private static final String INSERT_RESUME = "INSERT INTO resume "
            + "(title, accountId, position, yearOfExperience, bio, lastModified, imageUri, locationId, levelId, fieldId, status, views)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_RESUME = "UPDATE resume SET "
            + "title = ?, position = ?, yearOfExperience = ?, bio = ?, lastModified = ?, imageUri = ?, locationId = ?, levelId = ?, fieldId = ?, status = ? WHERE resumeId = ?;";
    private static final String DELETE_RESUME = "UPDATE resume SET deleted = 1 WHERE resumeId = ?";
    private static final String INCREASE_RESUME_VIEWS = "UPDATE resume SET views = views + ? WHERE resumeId = ?;";
    private static final String SWITCH_RESUME_STATUS = "UPDATE resume SET status = 1 ^ status WHERE resumeId =?;";
    
    public void addResume(Resume resume) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_RESUME, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, resume.getTitle());
            ps.setInt(2, resume.getAccountId());
            ps.setString(3, resume.getPosition());
            ps.setInt(4, resume.getYearOfExperience());
            ps.setString(5, resume.getBio());
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setString(7, resume.getWorkDetails().getImageUri());
            ps.setInt(8, resume.getWorkDetails().getLocationId());
            ps.setInt(9, resume.getWorkDetails().getLevelId());
            ps.setInt(10, resume.getWorkDetails().getFieldId());
            ps.setBoolean(11, resume.getStatus());
            ps.setInt(12, resume.getViews());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    resume.setResumeId(rs.getInt(1));
                }
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void updateResume(Resume resume) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_RESUME)) {
            ps.setString(1, resume.getTitle());
            ps.setString(2, resume.getPosition());
            ps.setInt(3, resume.getYearOfExperience());
            ps.setString(4, resume.getBio());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setString(6, resume.getWorkDetails().getImageUri());
            ps.setInt(7, resume.getWorkDetails().getLocationId());
            ps.setInt(8, resume.getWorkDetails().getLevelId());
            ps.setInt(9, resume.getWorkDetails().getFieldId());
            ps.setBoolean(10, resume.getStatus());
            ps.setInt(11, resume.getResumeId());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public void deleteResume(int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_RESUME)) {
            ps.setInt(1, resumeId);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public List<Resume> getResumesOfAccount(int accountId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_RESUMES_OF_ACCOUNT)) {
            ps.setInt(1, accountId);
            List<Resume> resumes = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resumes.add(resumeFromQueryResult(rs));
                }
            }
            return resumes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
        
    }
    
    public Optional<Resume> getResume(int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_RESUME)) {
            ps.setInt(1, resumeId);
            try (ResultSet rs = ps.executeQuery();) {
                return Optional.ofNullable(rs.next() ? resumeFromQueryResult(rs) : null);
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    private Resume resumeFromQueryResult(ResultSet rs) throws SQLException {
        Resume resume = new Resume();
        WorkDetails workDetails = new WorkDetails();
        
        workDetails.setFieldId(rs.getInt("fieldId"));
        workDetails.setLocationId(rs.getInt("locationId"));
        workDetails.setImageUri(rs.getString("imageUri"));
        workDetails.setLevelId(rs.getInt("levelId"));
        
        resume.setWorkDetails(workDetails);
        resume.setResumeId(rs.getInt("resumeId"));
        resume.setTitle(rs.getString("title"));
        resume.setAccountId(rs.getInt("accountId"));
        resume.setBio(rs.getString("bio"));
        resume.setLastModified(rs.getDate("lastModified").toLocalDate());
        resume.setPosition(rs.getString("position"));
        resume.setYearOfExperience(rs.getInt("yearOfExperience"));
        resume.setStatus(rs.getBoolean("status"));
        resume.setViews(rs.getInt("views"));
        resume.setDeleted(rs.getBoolean("deleted"));
        
        return resume;
    }
    
    public void increaseResumeViewCounts(Map<Integer, Integer> resumeIdToCount) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INCREASE_RESUME_VIEWS)) {
            for (Map.Entry<Integer, Integer> entry : resumeIdToCount.entrySet()) {
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
    
    public void switchResumeStatus(int resumeId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SWITCH_RESUME_STATUS)) {
            ps.setInt(1, resumeId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
        
    }
    
}
