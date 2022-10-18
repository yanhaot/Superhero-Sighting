package com.sg.herosighting.dao;

import java.util.List;

import com.sg.herosighting.model.Location;

public interface LocationDao {

	Location getLocationById(int id);
	
	List<Location> getAllLocation();
	
	Location addLocation(Location location);
	
	void updateLocation(Location location);
	
	void deleteLocationById(int id);
}
