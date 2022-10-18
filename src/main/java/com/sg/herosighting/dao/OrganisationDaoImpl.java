package com.sg.herosighting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sg.herosighting.dao.LocationDaoImpl.LocationMapper;
import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;

@Repository
public class OrganisationDaoImpl implements OrganisationDao{

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public Organisation getOrganisationById(int id) {
        try {
            final String GET_ORGANISATION_BY_ID = "SELECT * FROM organisation WHERE idOrganisation = ?";
            return jdbc.queryForObject(GET_ORGANISATION_BY_ID, new OrganisationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<Organisation> getAllOrganisations() {
        final String SELECT_ALL_ORGANISATIONS = "SELECT * FROM organisation";
        List<Organisation> organisations = jdbc.query(SELECT_ALL_ORGANISATIONS, new OrganisationMapper());
        return organisations;
	}

	@Override
	public Organisation addOrganisation(Organisation organisation) {
        final String INSERT_ORGANISATION = "INSERT INTO organisation(name, description, address, contact) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANISATION,
        		organisation.getName(),
        		organisation.getDescription(),
        		organisation.getAddress(),
        		organisation.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organisation.setId(newId);
        return organisation;
	}
	
    private List<Hero> getHeroesForOrganisation(int id) {
        final String SELECT_HEROS_FOR_ORGANISATION = "SELECT h.* FROM Hero h "
                + "JOIN HeroOrganisation oho ON ho.idHero= h.idHero WHERE hs.idOrganisation = ?";
        return jdbc.query(SELECT_HEROS_FOR_ORGANISATION, new HeroDaoImpl.HeroMapper(), id);
    }

	@Override
	public void updateOrganisation(Organisation organisation) {
        final String UPDATE_ORGANISATION = "UPDATE organisation SET name = ?, description = ?, address = ?, contact = ? "
                + "WHERE idOrganisation = ?";
        jdbc.update(UPDATE_ORGANISATION,
        		organisation.getName(),
        		organisation.getDescription(),
        		organisation.getAddress(),
        		organisation.getContact(),
        		organisation.getId());
        final String DELETE_HEROORGANISATION = "DELETE FROM HeroOrganisation WHERE idOrganisation = ?";
        jdbc.update(DELETE_HEROORGANISATION, organisation.getId());		
	}

	@Override
	@Transactional
	public void deleteOrganisationById(int id) {
        final String DELETE_HEROORGANISATION = "DELETE FROM HeroOrganisation WHERE idOrganisation = ?";
        jdbc.update(DELETE_HEROORGANISATION, id);

        final String DELETE_ORGANISATION = "DELETE FROM organisation WHERE idOrganisation = ?";
        jdbc.update(DELETE_ORGANISATION, id);
	}
	
    public static final class OrganisationMapper implements RowMapper<Organisation> {

		@Override
		public Organisation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Organisation newOrganisation = new Organisation();
			newOrganisation.setId(rs.getInt("idOrganisation"));
			newOrganisation.setName(rs.getString("name"));
			newOrganisation.setDescription(rs.getString("description"));
			newOrganisation.setAddress(rs.getString("address"));
			newOrganisation.setContact(rs.getString("contact"));
			return newOrganisation;
		}

    }

}
