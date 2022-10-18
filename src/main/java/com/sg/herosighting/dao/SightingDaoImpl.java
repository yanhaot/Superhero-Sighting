package com.sg.herosighting.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;
import com.sg.herosighting.model.Sighting;

@Repository
public class SightingDaoImpl implements SightingDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE idSighting = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setHero(getHeroForSighting(id));
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
	}
	
    private Hero getHeroForSighting(int id) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.idHero = h.idHero WHERE s.idSighting = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroDaoImpl.HeroMapper(), id);
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.idLocation = l.idLocation WHERE s.idSighting = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoImpl.LocationMapper(), id);
    }

	@Override
	public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateHeroAndLocation(sightings);
        return sightings;
	}
	
    private void associateHeroAndLocation(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroForSighting(sighting.getId()));
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }

	@Override
    @Transactional
	public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT INTO Sighting(idHero, idLocation, date) "
                + "VALUES(?,?,?)";
        jdbc.update(ADD_SIGHTING,
                sighting.getHero().getId(),
                sighting.getLocation().getId(),
        		Date.valueOf(sighting.getDate()));

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
	}

	@Override
	public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET date = ?, idLocation = ?, idHero = ? "
                + "WHERE idSighting = ?";
        jdbc.update(UPDATE_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getLocation().getId(),
                sighting.getHero().getId(),
                sighting.getId());
	}

	@Override
    @Transactional
	public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE idSighting = ?";
        jdbc.update(DELETE_SIGHTING, id);
	}

	@Override
	public List<Sighting> getSightingsForDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM Sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE,
                new SightingMapper(), date);
        associateHeroAndLocation(sightings);
        return sightings;
	}

	@Override
	public List<Sighting> getSightingsForHero(Hero hero) {
	    final String SELECT_SIGHTINGS_FOR_HERO = "SELECT * FROM Sighting WHERE idHero= ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_HERO,
                new SightingMapper(), hero.getId());
        associateHeroAndLocation(sightings);
        return sightings;
	}

	@Override
	public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM Sighting WHERE idLocation= ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION,
                new SightingMapper(), location.getId());
        associateHeroAndLocation(sightings);
        return sightings;
	}
	
    public static final class SightingMapper implements RowMapper<Sighting> {

		@Override
		public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sighting newSighting = new Sighting();
			newSighting.setId(rs.getInt("idSighting"));
			newSighting.setDate(rs.getDate("date").toLocalDate());
			return newSighting;
		}

    }

}
