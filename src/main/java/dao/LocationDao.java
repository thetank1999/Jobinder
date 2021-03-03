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
import models.common.Location;
import utils.JDBCUtils;

/**
 *
 * @author Admin
 */
public class LocationDao {

    private static final Logger LOGGER = Logger.getLogger(LocationDao.class.getName());
    private static final String SELECT_ALL_LOCATIONS = "SELECT * FROM location;";
    private static final String SELECT_LOCATION = "SELECT * FROM location WHERE locationId = ?;";

    public List<Location> getAllLocations() throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_LOCATIONS); ResultSet rs = ps.executeQuery()) {
            List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                locations.add(locationFromQueryResult(rs));
            }
            return locations;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    public Optional<Location> getLocation(int locationId) throws DaoException {
        try (Connection c = JDBCUtils.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_LOCATION)) {
            ps.setInt(1, locationId);
            try (ResultSet rs = ps.executeQuery();) {
                return Optional.ofNullable(rs.next() ? locationFromQueryResult(rs) : null);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new DaoException();
        }
    }

    private Location locationFromQueryResult(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setLocationId(rs.getInt("locationId"));
        location.setName(rs.getString("name"));
        return location;
    }
}
