package com.sg.herosighting.dao;

import java.time.LocalDate;
import java.util.List;

import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Sighting;

public interface SightingDao {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    List<Sighting> getSightingsForDate(LocalDate date);

    List<Sighting> getSightingsForHero(Hero hero);

    List<Sighting> getSightingsForLocation(Location location);
    
}
