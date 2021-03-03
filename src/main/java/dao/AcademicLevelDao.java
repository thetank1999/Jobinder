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
import models.common.AcademicLevel;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class AcademicLevelDao {

    private static final Logger LOGGER = Logger.getLogger(AcademicLevelDao.class.getName());
    private static final String SELECT_ALL_LEVELS = "SELECT * FROM academicLevel;";
    private static final String SELECT_LEVEL = "SELECT * FROM academicLevel WHERE levelId = ?;";

    public List<AcademicLevel> getAllLevels() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_LEVELS); ResultSet rs = ps.executeQuery()) {
            List<AcademicLevel> levels = new ArrayList<>();
            while (rs.next()) {
                levels.add(academicLevelFromQueryResult(rs));
            }
            return levels;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public Optional<AcademicLevel> getLevel(int levelId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_LEVEL)) {
            ps.setInt(1, levelId);
            try (ResultSet rs = ps.executeQuery();) {
                return Optional.ofNullable(rs.next() ? academicLevelFromQueryResult(rs) : null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private AcademicLevel academicLevelFromQueryResult(ResultSet rs) throws SQLException {
        AcademicLevel level = new AcademicLevel();
        level.setLevelId(rs.getInt("levelId"));
        level.setTitle(rs.getString("title"));
        return level;
    }
}
