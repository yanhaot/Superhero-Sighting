package com.sg.herosighting.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sg.herosighting.dao.HeroDao;
import com.sg.herosighting.dao.LocationDao;
import com.sg.herosighting.dao.OrganisationDao;
import com.sg.herosighting.dao.SightingDao;
import com.sg.herosighting.model.Hero;
import com.sg.herosighting.model.Organisation;

@Controller
public class HeroController {

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();


    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("/heros")
    public String displaySuperheroes(Model model) {
        List<Hero> heroList = heroDao.getAllHeroes();
        List<Organisation> organisations = organisationDao.getAllOrganisations();
        model.addAttribute("heroList", heroList);
        model.addAttribute("organisations", organisations);
        model.addAttribute("errors", violations);
        System.out.println(heroList);
        return "heros";
    }
  

    @PostMapping("/addHero")
    public String addSuperhero(HttpServletRequest request) {
    	Hero hero = new Hero();
    	hero.setName(request.getParameter("name"));
    	hero.setDescription(request.getParameter("description"));
    	hero.setSuperpower(request.getParameter("superpower"));

        String[] organisationIds = request.getParameterValues("idOrganisation");

        List<Organisation> organisations = new ArrayList<>();
        if (organisationIds != null) {
            for (String organisationId : organisationIds) {
                organisations.add(organisationDao.getOrganisationById(Integer.parseInt(organisationId)));
            }
        }
        hero.setOrganisations(organisations);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.addHero(hero);
        }


        return "redirect:/heros";
    }
  

    @GetMapping("/deleteHero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteHeroById(id);

        return "redirect:/heros";
    }

    @GetMapping("/editHero")
    public String editSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        List<Organisation> organisations = organisationDao.getAllOrganisations();
        model.addAttribute("hero", hero);
        model.addAttribute("organisations", organisations);

        return "editHero";
    }

    @PostMapping("/editHero")
    public String performEditSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);

        hero.setName(request.getParameter("name"));
        hero.setDescription(request.getParameter("description"));
        hero.setSuperpower(request.getParameter("superpower"));

        String[] organisationIds = request.getParameterValues("idOrganisation");

        List<Organisation> organisations = new ArrayList<>();
        if (organisationIds != null) {
            for (String orgId : organisationIds) {
                organisations.add(organisationDao.getOrganisationById(Integer.parseInt(orgId)));
            }
        }

        hero.setOrganisations(organisations);
        heroDao.updateHero(hero);

        return "redirect:/heros";
    }
}
