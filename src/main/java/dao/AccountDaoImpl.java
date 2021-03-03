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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.account.Account;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class AccountDaoImpl implements AccountDao {

    private static final Logger LOGGER = Logger.getLogger(AccountDao.class.getName());
    private static final String INSERT_ACCOUNT = "INSERT INTO account (activated, email, password, dateJoined, accountTypeId, phoneNumber, name) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String ACTIVATE_ACCOUNT = "UPDATE account SET activated = 1 WHERE accountId = ?;";
    private static final String RESET_PASSWORD = "UPDATE account SET password = ? WHERE accountId = ?;";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM account WHERE accountId = ?;";
    private static final String SELECT_ACCOUNT_BY_EMAIL = "SELECT * FROM account WHERE email = ?;";

    @Override
    public void createAccount(Account account) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, account.isActivated());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPassword());
            ps.setDate(4, Date.valueOf(account.getDateJoined()));
            ps.setInt(5, account.getAccountTypeId());
            ps.setString(6, account.getPhoneNumber());
            ps.setString(7, account.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    account.setAccountId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public void activate(int accountId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(ACTIVATE_ACCOUNT)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public void resetPassword(int accountId, String newPassword) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(RESET_PASSWORD)) {
            ps.setString(1, newPassword);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public Optional<Account> getAccountById(int accountId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            return Optional.ofNullable(rs.next() ? accountFromQueryResult(rs) : null);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ACCOUNT_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return Optional.ofNullable(rs.next() ? accountFromQueryResult(rs) : null);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private Account accountFromQueryResult(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getInt("accountId"));
        account.setActivated(rs.getBoolean("activated"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setDateJoined(rs.getDate("dateJoined").toLocalDate());
        account.setAccountTypeId(rs.getInt("accountTypeId"));
        account.setPhoneNumber(rs.getString("phoneNumber"));
        account.setName(rs.getString("name"));
        return account;
    }
}
