package com.sg.herosighting.dao;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
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
public class SightingDaoImplTest {

	  @Autowired
	    HeroDao heroDao;

	    @Autowired
	    OrganisationDao organisationDao;

	    @Autowired
	    LocationDao locationDao;

	    @Autowired
	    SightingDao sightingDao;

	    @BeforeEach
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
	    public void testAddGetSighting() {
	    	

	    	Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

	        assertEquals(sighting, fromDao);
	    }

	    @Test
	    public void testGetAllSightings() {

	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        Hero superhero2 = new Hero();
	        superhero2.setName("Test Name2");
	        superhero2.setDescription("Test Description2");
	        superhero2.setSuperpower("Test Superpower2");
	        superhero2 = heroDao.addTestHero(superhero2);

	        Location location2 = new Location();
	        location2.setName("Test Name2");
	        location2.setDescription("Test Description2");
	        location2.setAddress("Test Address2");
	        location2.setLatitude(23.129862);
	        location2.setLongitude(23.129862);

	        location2 = locationDao.addLocation(location2);

	        Sighting sighting2 = new Sighting();
	        sighting2.setDate(LocalDate.parse("2020-03-10"));
	        sighting2.setLocation(location2);
	        sighting2.setHero(superhero2);

	        sighting2 = sightingDao.addSighting(sighting2);
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        List<Sighting> listFromDao = sightingDao.getAllSightings();

	        assertTrue(listFromDao.contains(sighting));
	        assertTrue(listFromDao.contains(sighting2));

	    }

	    @Test
	    public void testUpdateSighting() {
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

	        assertEquals(sighting, fromDao);

	        sighting.setDate(LocalDate.parse("2019-09-15"));
	        sightingDao.updateSighting(sighting);
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);
	    }

	    @Test
	    public void testDeleteSightingById() {
	    	Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

	        assertEquals(sighting, fromDao);

	        sightingDao.deleteSightingById(sighting.getId());
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertNull(fromDao);
	    }

	    @Test
	    public void testGetSightingsForDate() {
	        Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        Hero superhero2 = new Hero();
	        superhero2.setName("Test Name2");
	        superhero2.setDescription("Test Description2");
	        superhero2.setSuperpower("Test Superpower2");
	        superhero2 = heroDao.addTestHero(superhero2);

	        Location location2 = new Location();
	        location2.setName("Test Name2");
	        location2.setDescription("Test Description2");
	        location2.setAddress("Test Address2");
	        location2.setLatitude(23.129862);
	        location2.setLongitude(23.129862);

	        location2 = locationDao.addLocation(location2);

	        Sighting sighting2 = new Sighting();
	        sighting2.setDate(LocalDate.parse("2019-09-12"));
	        sighting2.setLocation(location2);
	        sighting2.setHero(superhero2);

	        sighting2 = sightingDao.addSighting(sighting2);
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        List<Sighting> listFromDao = sightingDao.getSightingsForDate(LocalDate.parse("2019-09-12"));
	        assertTrue(listFromDao.contains(sighting));
	        assertTrue(listFromDao.contains(sighting2));

	        sighting2.setDate(LocalDate.parse("2018-08-18"));
	        sightingDao.updateSighting(sighting2);
	        listFromDao = sightingDao.getSightingsForDate(LocalDate.parse("2019-09-12"));
	        assertTrue(listFromDao.contains(sighting));
	        assertFalse(listFromDao.contains(sighting2));

	    }

	    @Test
	    public void testGetSightingsForSuperhero() {
	    	Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        Hero superhero2 = new Hero();
	        superhero2.setName("Test Name2");
	        superhero2.setDescription("Test Description2");
	        superhero2.setSuperpower("Test Superpower2");
	        superhero2 = heroDao.addTestHero(superhero2);

	        Location location2 = new Location();
	        location2.setName("Test Name2");
	        location2.setDescription("Test Description2");
	        location2.setAddress("Test Address2");
	        location2.setLatitude(23.129862);
	        location2.setLongitude(23.129862);

	        location2 = locationDao.addLocation(location2);

	        Sighting sighting2 = new Sighting();
	        sighting2.setDate(LocalDate.parse("2019-09-12"));
	        sighting2.setLocation(location2);
	        sighting2.setHero(superhero2);

	        sighting2 = sightingDao.addSighting(sighting2);
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        List<Sighting> listFromDao = sightingDao.getSightingsForHero(superhero);
	        assertTrue(listFromDao.contains(sighting));
	        assertFalse(listFromDao.contains(sighting2));

	        sighting2.setHero(superhero);
	        sightingDao.updateSighting(sighting2);
	        listFromDao = sightingDao.getSightingsForHero(superhero);
	        assertTrue(listFromDao.contains(sighting));
	        assertTrue(listFromDao.contains(sighting2));

	    }

	    @Test
	    public void testGetSightingsForLocation() {

	    	Hero superhero = new Hero();
	        superhero.setName("Test Name");
	        superhero.setDescription("Test Description");
	        superhero.setSuperpower("Test Superpower");
	        superhero = heroDao.addTestHero(superhero);

	        Location location = new Location();
	        location.setName("Test Name");
	        location.setDescription("Test Description");
	        location.setAddress("Test Address");
	        location.setLatitude(23.129864);
	        location.setLongitude(23.129864);

	        location = locationDao.addLocation(location);

	        Sighting sighting = new Sighting();
	        sighting.setDate(LocalDate.parse("2019-09-12"));
	        sighting.setLocation(location);
	        sighting.setHero(superhero);

	        sighting = sightingDao.addSighting(sighting);
	        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        Hero superhero2 = new Hero();
	        superhero2.setName("Test Name2");
	        superhero2.setDescription("Test Description2");
	        superhero2.setSuperpower("Test Superpower2");
	        superhero2 = heroDao.addTestHero(superhero2);

	        Location location2 = new Location();
	        location2.setName("Test Name2");
	        location2.setDescription("Test Description2");
	        location2.setAddress("Test Address2");
	        location2.setLatitude(23.129862);
	        location2.setLongitude(23.129862);

	        location2 = locationDao.addLocation(location2);

	        Sighting sighting2 = new Sighting();
	        sighting2.setDate(LocalDate.parse("2019-09-12"));
	        sighting2.setLocation(location2);
	        sighting2.setHero(superhero2);

	        sighting2 = sightingDao.addSighting(sighting2);
	        fromDao = sightingDao.getSightingById(sighting.getId());
	        assertEquals(sighting, fromDao);

	        List<Sighting> listFromDao = sightingDao.getSightingsForLocation(location);
	        assertTrue(listFromDao.contains(sighting));
	        assertFalse(listFromDao.contains(sighting2));

	        sighting2.setLocation(location);
	        sightingDao.updateSighting(sighting2);
	        listFromDao = sightingDao.getSightingsForLocation(location);
	        assertTrue(listFromDao.contains(sighting));
	        assertTrue(listFromDao.contains(sighting2));

	    }
}
