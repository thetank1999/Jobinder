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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.common.Token;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class TokenDao {
    
    private static final Logger LOGGER = Logger.getLogger(TokenDao.class.getName());
    private static final String INSERT_TOKEN = "INSERT INTO token (accountId, value, createdTimeMillis) VALUES (?, ?, ?);";
    private static final String SELECT_TOKEN = "SELECT * FROM token WHERE value = ?";
    
    public void createToken(Token token) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_TOKEN)) {
            ps.setInt(1, token.getAccountId());
            ps.setString(2, token.getValue());
            ps.setLong(3, token.getCreatedTimeMillis());
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    public Optional<Token> getTokenOfValue(String value) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_TOKEN)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            return Optional.ofNullable(rs.next() ? tokenFromQueryResult(rs) : null);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }
    
    private Token tokenFromQueryResult(ResultSet rs) throws SQLException {
        Token token = new Token();
        token.setAccountId(rs.getInt("accountId"));
        token.setValue(rs.getString("value"));
        token.setCreatedTimeMillis(rs.getLong("createdTimeMillis"));
        return token;
    }
}
