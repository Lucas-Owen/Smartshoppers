package org.smartshoppers.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="STORES")
public class Store {
	@Id
	private String id;
      
	@NotEmpty
	private String name = "";
	@NotEmpty
	private String location = "";
	
	
	@ManyToMany(targetEntity=ShoppingItem.class)
	protected Set<ShoppingItem> shoppingItems;
	
	public Store() {
		location = "";
		name = "";
		shoppingItems = new HashSet<ShoppingItem>();
	}
	
	public Store(String name, String location, User manager) {
		super();
		shoppingItems = new HashSet<ShoppingItem>();
		this.name = name;
		this.location = location;
	}
	
	public boolean equals(Store other) {
		return this.name.equals(other.name) && this.location.equals(other.location); 
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setId();
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
		setId();
	}
	
	private void setId() {
		id = name.toLowerCase() + " " + location.toLowerCase();
	}
	
	public String getId() { 
		return id;
	}
	public String toString() {
		return id;
	}
}
