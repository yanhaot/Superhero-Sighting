package com.sg.herosighting.dao;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;
import com.sg.herosighting.model.Sighting;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoImplTest {

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
	    
	    @Test()
	    public void testGetAllLocations() {
	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Location location2 = new Location();
	        location2.setName("Test Name2");
	        location2.setDescription("Test Description2");
	        location2.setAddress("Test Address2");
	        location2.setLatitude(23.129864);
	        location2.setLongitude(23.129864);

	        location2 = locationDao.addLocation(location2);

	        List<Location> fromDao = locationDao.getAllLocation();

	        assertTrue(fromDao.contains(location));
	        assertTrue(fromDao.contains(location2));
	    }

	    @Test
	    public void testAddGetLocation() {
	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Location fromDao = locationDao.getLocationById(location.getId());

	        assertEquals(location, fromDao);
	    }

	    @Test
	    public void testUpdateLocation() {

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);
	        Location fromDao = locationDao.getLocationById(location.getId());

	        assertEquals(location, fromDao);

	        location.setAddress("New Address");
	        location.setLatitude(33.12464);

	        locationDao.updateLocation(location);

	        fromDao = locationDao.getLocationById(location.getId());

	        assertEquals(location, fromDao);
	    }

	    @Test
	    public void testDeleteLocationById() {

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);
	        Location fromDao = locationDao.getLocationById(location.getId());

	        assertEquals(location, fromDao);


	        locationDao.deleteLocationById(location.getId());

	        fromDao = locationDao.getLocationById(location.getId());

	        assertNull(fromDao);
	    }
	
}
