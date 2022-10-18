package com.sg.herosighting.dao;

import java.util.List;

import com.sg.herosighting.model.Organisation;

public interface OrganisationDao {

    Organisation getOrganisationById(int id);

    List<Organisation> getAllOrganisations();

    Organisation addOrganisation(Organisation org);

    void updateOrganisation(Organisation org);

    void deleteOrganisationById(int id);
    
}
