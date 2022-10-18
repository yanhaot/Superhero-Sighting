package com.sg.herosighting.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sg.herosighting.dao.HeroDao;
import com.sg.herosighting.dao.LocationDao;
import com.sg.herosighting.dao.OrganisationDao;
import com.sg.herosighting.dao.SightingDao;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Organisation;

@Controller
public class LocationController {

	  Set<ConstraintViolation<Location>> violations = new HashSet<>();

	    @Autowired
	    HeroDao superheroDao;

	    @Autowired
	    OrganisationDao organisationDao;

	    @Autowired
	    LocationDao locationDao;

	    @Autowired
	    SightingDao sightingDao;
	    
	    
	    @GetMapping("locations")
	    public String displayLocations(Model model) {
	        List<Location> locations = locationDao.getAllLocation();
	        model.addAttribute("locations", locations);
	        model.addAttribute("errors", violations);
	        return "locations";
	    }

	    @PostMapping("addLocation")
	    public String addLocation(HttpServletRequest request) {
	    	Location location = new Location();
	        location.setName(request.getParameter("name"));
	        location.setDescription(request.getParameter("description"));
	        location.setAddress(request.getParameter("address"));
	        location.setLatitude(Double.parseDouble(request.getParameter("latitude")));
	        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));

	        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
	        violations = validate.validate(location);

	        if (violations.isEmpty()) {
	            locationDao.addLocation(location);
	        }

	        return "redirect:/locations";
	    }

	    @GetMapping("deleteLocation")
	    public String deleteLocation(HttpServletRequest request) {
	        int id = Integer.parseInt(request.getParameter("id"));
	        locationDao.deleteLocationById(id);

	        return "redirect:/locations";
	    }

	    @GetMapping("editLocation")
	    public String editLocation(HttpServletRequest request, Model model) {
	        int id = Integer.parseInt(request.getParameter("id"));
	        Location location = locationDao.getLocationById(id);

	        model.addAttribute("location", location);
	        return "editLocation";
	    }

	    @PostMapping("editLocation")
	    public String performEditLocation(HttpServletRequest request) {
	        int id = Integer.parseInt(request.getParameter("id"));
	        Location location = locationDao.getLocationById(id);

	        location.setName(request.getParameter("name"));
	        location.setDescription(request.getParameter("description"));
	        location.setAddress(request.getParameter("address"));
	        location.setLatitude(Double.parseDouble(request.getParameter("latitude")));
	        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));

	        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
	        violations = validate.validate(location);

	        if (violations.isEmpty()) {
	            locationDao.updateLocation(location);
	        }

	        return "redirect:/locations";
	    }
}
