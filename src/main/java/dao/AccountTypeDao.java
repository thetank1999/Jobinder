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
import models.account.AccountType;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class AccountTypeDao {

    private static final Logger LOGGER = Logger.getLogger(AccountTypeDao.class.getName());
    private static final String SELECT_ALL_TYPES = "SELECT * FROM accountType;";

    public List<AccountType> getAllTypes() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_TYPES); ResultSet rs = ps.executeQuery()) {
            List<AccountType> types = new ArrayList<>();
            while (rs.next()) {
                types.add(accountTypeFromQueryResult(rs));
            }
            return types;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new DaoException();
        }
    }

    private AccountType accountTypeFromQueryResult(ResultSet rs) throws SQLException {
        AccountType type = new AccountType();
        type.setAccountTypeId(rs.getInt("accountTypeId"));
        type.setName(rs.getString("name"));
        return type;
    }
}
