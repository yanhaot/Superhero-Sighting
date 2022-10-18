package com.sg.herosighting.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Organisation {

	private int id;
	
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message="Name must be less than 45 characters.")
    private String name;
    
    @Size(max = 255, message="Description must be within than 255 characters.")
	private String description;
    
    @Size(max = 255, message="Address must be within than 255 characters.")
	private String address;
    
    @Size(max = 50, message="Contact must be within than 50 characters.")
	private String contact;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(address, contact, description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organisation other = (Organisation) obj;
		return Objects.equals(address, other.address) && Objects.equals(contact, other.contact)
				&& Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Organisation [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", contact=" + contact + "]";
	}
	
}
