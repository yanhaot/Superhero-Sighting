package com.sg.herosighting.dao;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;
import com.sg.herosighting.model.Sighting;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganisationDaoImplTest {

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
	    public void testGetAllOrganisations() {

	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);

	        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertEquals(organisation, fromDao);

	        Organisation organisation2 = new Organisation();
	        organisation2.setName("Test Organisation2");
	        organisation2.setDescription("Test Description2");
	        organisation2.setAddress("Test Address2");
	        organisation2.setContact("07610101020");

	        organisation2 = organisationDao.addOrganisation(organisation2);

	        fromDao = organisationDao.getOrganisationById(organisation2.getId());
	        assertEquals(organisation2, fromDao);

	        List<Organisation> listFromDao = organisationDao.getAllOrganisations();
	        assertTrue(listFromDao.contains(organisation));
	        assertTrue(listFromDao.contains(organisation2));
	    }

	    @Test
	    public void testAddGetOrganisation() {


	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertEquals(organisation, fromDao);
	    }

	    @Test
	    public void testUpdateOrganisation() {

	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertEquals(organisation, fromDao);

	        organisation.setDescription("New Description");
	        organisation.setContact("07644551100");
	        organisationDao.updateOrganisation(organisation);
	        fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertEquals(organisation, fromDao);
	    }

	    @Test
	    public void testDeleteOrganisationById() {

	        Organisation organisation = new Organisation();
	        organisation.setName("Test Organisation");
	        organisation.setDescription("Test Description");
	        organisation.setAddress("Test Address");
	        organisation.setContact("07610101000");

	        organisation = organisationDao.addOrganisation(organisation);
	        Organisation fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertEquals(organisation, fromDao);

	        organisationDao.deleteOrganisationById(organisation.getId());
	        fromDao = organisationDao.getOrganisationById(organisation.getId());
	        assertNull(fromDao);
	    }
	
}
