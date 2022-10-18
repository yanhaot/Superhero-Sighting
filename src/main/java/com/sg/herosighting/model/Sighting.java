package com.sg.herosighting.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Sighting {

	private int id;
	
    @NotNull(message = "Superhero cannot be null")
	private Hero hero;
	
    @NotNull(message = "Location cannot be null")
	private Location location;
	
    @NotNull(message = "Please choose a valid date.")
    @Past(message = "Date must be in the past!")
	private LocalDate date;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Hero getHero() {
		return hero;
	}
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Sighting [id=" + id + ", hero=" + hero + ", location=" + location + ", date=" + date + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, hero, id, location);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sighting other = (Sighting) obj;
		return Objects.equals(date, other.date) && Objects.equals(hero, other.hero) && id == other.id
				&& Objects.equals(location, other.location);
	}
	
}
