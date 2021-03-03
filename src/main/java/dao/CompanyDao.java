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
import models.job.Company;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class CompanyDao {

    private static final Logger LOGGER = Logger.getLogger(CompanyDao.class.getName());
    private static final String SELECT_COMPANY = "SELECT * FROM company WHERE companyId = ?;";
    private static final String INSERT_COMPANY = "INSERT INTO company (companyId, name, imageUri, address, description) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_COMPANY = "UPDATE company SET name = ?, imageUri = ?, address = ?, description = ? WHERE companyId = ?;";

    public Optional<Company> getCompany(int companyId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_COMPANY)) {
            ps.setInt(1, companyId);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.ofNullable(rs.next() ? companyFromQueryResult(rs) : null);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void addCompany(Company company) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_COMPANY)) {
            ps.setInt(1, company.getCompanyId());
            ps.setString(2, company.getName());
            ps.setString(3, company.getImageUri());
            ps.setString(4, company.getAddress());
            ps.setString(5, company.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public void updateCompany(Company company) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_COMPANY)) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getImageUri());
            ps.setString(3, company.getAddress());
            ps.setString(4, company.getDescription());
            ps.setInt(5, company.getCompanyId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private Company companyFromQueryResult(ResultSet rs) throws SQLException {
        Company company = new Company();
        company.setName(rs.getString("name"));
        company.setImageUri(rs.getString("imageUri"));
        company.setAddress(rs.getString("address"));
        company.setDescription(rs.getString("description"));
        company.setCompanyId(rs.getInt("companyId"));

        return company;
    }
}
