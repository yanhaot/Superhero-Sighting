package com.sg.herosighting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sg.herosighting.model.Location;

@Repository
public class LocationDaoImpl implements LocationDao{

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM Location WHERE idLocation = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

	@Override
	public List<Location> getAllLocation() {
		final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
	}

	@Override
	@Transactional
	public Location addLocation(Location location) {
		final String ADD_LOCATIONS = "INSERT INTO Location(name, description, address, latitude, longitude)"
				+ "VALUES(?, ?, ?, ?, ?)";
		jdbc.update(ADD_LOCATIONS,
				location.getName(),
				location.getDescription(),
				location.getAddress(),
				location.getLatitude(),
				location.getLongitude());
		
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
		return location;
	}

	@Override
	public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE idlocation = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
	}

	@Override
	@Transactional
	public void deleteLocationById(int id) {
        final String DELETE_SIGHTING_LOCATION = "DELETE FROM Sighting WHERE idlocation = ?";
        jdbc.update(DELETE_SIGHTING_LOCATION, id);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE idlocation = ?";
        jdbc.update(DELETE_LOCATION, id);		
	}
	
    public static final class LocationMapper implements RowMapper<Location> {

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location newLocation = new Location();
			newLocation.setId(rs.getInt("idLocation"));
			newLocation.setName(rs.getString("name"));
			newLocation.setDescription(rs.getString("description"));
			newLocation.setAddress(rs.getString("address"));
			newLocation.setLatitude(rs.getDouble("latitude"));
			newLocation.setLongitude(rs.getDouble("longitude"));
			return newLocation;
		}

    }

}
