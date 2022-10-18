package com.sg.herosighting.dao;

import static org.junit.Assert.*;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;
import com.sg.herosighting.model.Sighting;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HeroDaoImplTest {

    @Autowired
    HeroDao heroDao;
    
    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;
    
	 @Before
	    public void setUp() {
	        List<Hero> superheroes = heroDao.getAllHeroes();
	        for (Hero superhero : superheroes) {
	        	heroDao.deleteHeroById(superhero.getId());
	        }

	        List<Organisation> organisations = organisationDao.getAllOrganisations();
	        for (Organisation org : organisations) {
	            organisationDao.deleteOrganisationById(org.getId());
	        }

	        List<Location> locations = locationDao.getAllLocation();
	        for (Location location : locations) {
	            locationDao.deleteLocationById(location.getId());
	        }

	        List<Sighting> sightings = sightingDao.getAllSightings();
	        for (Sighting sighting : sightings) {
	            sightingDao.deleteSightingById(sighting.getId());
	        }
	    }

	    @Test
	    public void testAddGetSuperhero() {
	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");

	        List<Organisation> organisations = new ArrayList<>();
	        organisations.add(organisation);
	        superhero.setOrganisations(organisations);

	        superhero = heroDao.addHero(superhero);

	        Hero fromDao = heroDao.getHeroById(superhero.getId());
	        assertEquals(superhero, fromDao);
	    }

	    @Test
	    public void testGetAllSuperheroes() {

	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");

	        List<Organisation> organisations = new ArrayList<>();
	        organisations.add(organisation);
	        superhero.setOrganisations(organisations);

	        superhero = heroDao.addHero(superhero);

	        Hero fromDao = heroDao.getHeroById(superhero.getId());
	        assertEquals(superhero, fromDao);

	        Hero superhero2 = new Hero();
	        superhero2.setName("Test Name2");
	        superhero2.setDescription("Test Description2");
	        superhero2.setSuperpower("Test Superpower2");
	        superhero2.setOrganisations(organisations);
	        superhero2 = heroDao.addHero(superhero2);

	        fromDao = heroDao.getHeroById(superhero2.getId());
	        assertEquals(superhero2, fromDao);

	        List<Hero> listFromDao = heroDao.getAllHeroes();

	        assertTrue(listFromDao.contains(superhero));
	        assertTrue(listFromDao.contains(superhero2));

	    }

	    @Test
	    public void testUpdateSuperhero() {
	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");

	        List<Organisation> organisations = new ArrayList<>();
	        organisations.add(organisation);
	        superhero.setOrganisations(organisations);

	        superhero = heroDao.addHero(superhero);

	        Hero fromDao = heroDao.getHeroById(superhero.getId());
	        assertEquals(superhero, fromDao);

	        superhero.setSuperpower("New Superpower");
	        superhero.setDescription("New Description");

	        heroDao.updateHero(superhero);

	        fromDao = heroDao.getHeroById(superhero.getId());

	        assertEquals(superhero, fromDao);
	    }

	    @Test
	    public void testDeleteSuperheroById() {
	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");

	        List<Organisation> organisations = new ArrayList<>();
	        organisations.add(organisation);
	        superhero.setOrganisations(organisations);

	        superhero = heroDao.addHero(superhero);

	        Hero fromDao = heroDao.getHeroById(superhero.getId());
	        assertEquals(superhero, fromDao);

	        heroDao.deleteHeroById(superhero.getId());

	        fromDao = heroDao.getHeroById(superhero.getId());

	        assertNull(fromDao);
	    }
}
