package com.sg.herosighting.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Location;
import com.sg.herosighting.model.Sighting;

@Controller
public class SightingController {

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Hero> heros = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocation();

        model.addAttribute("sightings", sightings);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        System.out.println("HEROS: " + heros);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        Sighting sighting = new Sighting();

        try {
            sighting.setDate(LocalDate.parse(request.getParameter("date")));
        } catch (DateTimeParseException e) {
            sighting.setDate(null);
        }


        String idHero = request.getParameter("idHero");
        String idLocation = request.getParameter("idlocation");

        sighting.setHero(heroDao.getHeroById(Integer.parseInt(idHero)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(idLocation)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocation();

        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);

        sighting.setDate(LocalDate.parse(request.getParameter("date")));

        String idHero = request.getParameter("idHero");
        String idLocation = request.getParameter("idLocation");

        sighting.setHero(heroDao.getHeroById(Integer.parseInt(idHero)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(idLocation)));

        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }
    
}
