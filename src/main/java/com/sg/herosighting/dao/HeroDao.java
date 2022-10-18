package com.sg.herosighting.dao;

import java.util.List;

import com.sg.herosighting.model.Hero;

public interface HeroDao {

    Hero getHeroById(int id);

    List<Hero> getAllHeroes();

    Hero addHero(Hero hero);
    
    Hero addTestHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);
    
}
