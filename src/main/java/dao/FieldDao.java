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
import models.common.Field;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class FieldDao {

    private static final Logger LOGGER = Logger.getLogger(FieldDao.class.getName());
    private static final String SELECT_ALL_FIELDS = "SELECT * FROM field;";
    private static final String SELECT_FIELD = "SELECT * FROM field WHERE fieldId = ?;";

    public List<Field> getAllFields() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_FIELDS); ResultSet rs = ps.executeQuery()) {
            List<Field> fields = new ArrayList<>();
            while (rs.next()) {
                fields.add(fieldFromQueryResult(rs));
            }
            return fields;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public Optional<Field> getField(int fieldId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_FIELD)) {
            ps.setInt(1, fieldId);
            try (ResultSet rs = ps.executeQuery();) {
                return Optional.ofNullable(rs.next() ? fieldFromQueryResult(rs) : null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private Field fieldFromQueryResult(ResultSet rs) throws SQLException {
        Field field = new Field();
        field.setFieldId(rs.getInt("fieldId"));
        field.setName(rs.getString("name"));
        return field;
    }
}
