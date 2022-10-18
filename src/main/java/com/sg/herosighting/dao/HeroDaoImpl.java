package com.sg.herosighting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Organisation;

@Repository
public class HeroDaoImpl implements HeroDao{

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Hero WHERE idHero = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            hero.setOrganisations(getOrganisationsForHero(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
	}
	
    private List<Organisation> getOrganisationsForHero(int id) {
        final String SELECT_ORGANISATIONS_FOR_HERO = "SELECT o.* FROM Organisation o "
                + "JOIN HeroOrganisation ho ON ho.idOrganisation = o.idOrganisation WHERE ho.idHero= ?";
        return jdbc.query(SELECT_ORGANISATIONS_FOR_HERO, new OrganisationDaoImpl.OrganisationMapper(), id);
    }

	@Override
	public List<Hero> getAllHeroes() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM Hero";
        List<Hero> heros = jdbc.query(GET_ALL_SUPERHEROES, new HeroMapper());
        associateOrganisations(heros);
        return heros;
	}
	
    private void associateOrganisations(List<Hero> heros) {
        for (Hero hero : heros) {
        	hero.setOrganisations(getOrganisationsForHero(hero.getId()));
        }
    }

	@Override
	public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(name, description, superpower) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
        		hero.getName(),
        		hero.getDescription(),
        		hero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        insertOrganisationHero(hero);
        return hero;
	}
	
	@Override
	public Hero addTestHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(name, description, superpower) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
        		hero.getName(),
        		hero.getDescription(),
        		hero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        return hero;
	}
	
    private void insertOrganisationHero(Hero hero) {
        final String INSERT_ORGANISATION_HERO = "INSERT INTO "
                + "HeroOrganisation(idOrganisation, idHero) VALUES(?,?)";
        for (Organisation organisation : hero.getOrganisations()) {
            jdbc.update(INSERT_ORGANISATION_HERO,
            		organisation.getId(),
            		hero.getId());
        }
    }

	@Override
	public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET name = ?, description = ?, " +
                "superpower = ? WHERE idHero = ?";
        jdbc.update(UPDATE_HERO,
        		hero.getName(),
        		hero.getDescription(),
        		hero.getSuperpower(),
        		hero.getId());
        final String DELETE_ORGANISATION_HERO = "DELETE FROM HeroOrganisation WHERE idHero = ?";
        jdbc.update(DELETE_ORGANISATION_HERO, hero.getId());
        insertOrganisationHero(hero);
	}

	@Override
	public void deleteHeroById(int id) {
        final String DELETE_HERO_ORGANISATION = "DELETE FROM HeroOrganisation WHERE idHero = ?";
        jdbc.update(DELETE_HERO_ORGANISATION, id);

        final String DELETE_HERO_SIGHTING = "DELETE FROM Sighting WHERE idHero = ?";
        jdbc.update(DELETE_HERO_SIGHTING, id);

        final String DELETE_HERO = "DELETE FROM Hero WHERE idHero = ?";
        jdbc.update(DELETE_HERO, id);
	}
	
    public static final class HeroMapper implements RowMapper<Hero> {

		@Override
		public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
			Hero newHero = new Hero();
			newHero.setId(rs.getInt("idHero"));
			newHero.setName(rs.getString("name"));
			newHero.setDescription(rs.getString("description"));
			newHero.setSuperpower(rs.getString("superpower"));
			return newHero;
		}
    }

}
