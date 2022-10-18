package com.sg.herosighting.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Hero {

	private int id;
	
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message="Name must be less than 45 characters.")
	private String name;
    
    @Size(max = 255, message="Description must be less than 255 characters.")
    private String description;
	
    @NotBlank(message = "Superpower must not be empty.")
    @Size(max = 45, message = "Superpower must be less than 45 characters.")
	private String superpower;
	
    private List<Organisation> organisations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuperpower() {
		return superpower;
	}

	public void setSuperpower(String superpower) {
		this.superpower = superpower;
	}

	public List<Organisation> getOrganisations() {
		return organisations;
	}

	public void setOrganisations(List<Organisation> organisations) {
		this.organisations = organisations;
	}

	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + ", description=" + description + ", superpower=" + superpower
				+ ", organisations=" + organisations + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, organisations, superpower);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(organisations, other.organisations) && Objects.equals(superpower, other.superpower);
	}
	
}
