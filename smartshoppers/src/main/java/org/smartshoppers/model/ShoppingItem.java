package org.smartshoppers.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="SHOPPING_ITEMS")
public class ShoppingItem {
	@Id
	@NotEmpty
	protected String id;
	
	@NotEmpty
	protected String name = "";
	
	protected String store = "";
	
	protected double price;
	
	@NotEmpty
	protected String category = "";
	
	@ManyToMany(fetch=FetchType.EAGER)
	protected Set<User> users = new HashSet<>();
	
	@ManyToMany(targetEntity=Store.class)
	protected Set<Store> stores;
	
	public ShoppingItem() {
		users = new HashSet<User>();
	}
	
	public ShoppingItem(String name, String store, double price, String category) {
		super();
		users = new HashSet<User>();
		this.name = name;
		this.store = store;
		this.price = price;
		this.category = category;
		setId();
	}

	public String getCategory() {
		return category;
	}
	public void setStore(String store) {
		this.store = store;
		setId();
	}
	public String getStore() {
		return store;
	}
	public String getId() {
		return id;
	}
	private void setId() {
		id = name.toLowerCase() + store.toLowerCase();
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setId();
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	

}
