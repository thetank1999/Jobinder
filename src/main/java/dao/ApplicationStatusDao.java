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
import models.application.ApplicationStatus;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class ApplicationStatusDao {

    private static final Logger LOGGER = Logger.getLogger(ApplicationStatusDao.class.getName());

    private static final String SELECT_ALL_STATUSES = "SELECT * FROM applicationStatus;";

    public List<ApplicationStatus> getAllApplicationStatues() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_STATUSES); ResultSet rs = ps.executeQuery()) {
            List<ApplicationStatus> statues = new ArrayList<>();
            while (rs.next()) {
                statues.add(statusFromQueryResult(rs));
            }
            return statues;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new DaoException();
        }
    }

    private ApplicationStatus statusFromQueryResult(ResultSet rs) throws SQLException {
        ApplicationStatus status = new ApplicationStatus();
        status.setApplicationStatusId(rs.getInt("statusId"));
        status.setName(rs.getString("name"));
        return status;
    }
}
