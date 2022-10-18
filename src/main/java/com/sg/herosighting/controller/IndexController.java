package com.sg.herosighting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sg.herosighting.dao.HeroDao;
import com.sg.herosighting.dao.LocationDao;
import com.sg.herosighting.dao.OrganisationDao;
import com.sg.herosighting.dao.SightingDao;
import com.sg.herosighting.model.Sighting;

@Controller
public class IndexController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("/")
    public String displayFeedView(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.sort((s1, s2) -> s2.getDate().compareTo(s1.getDate()));
        if (sightings.size() > 10) {
            sightings = sightings.subList(0, 10);
        }
        model.addAttribute("sightings", sightings);
        return "index";
    }
    
}
