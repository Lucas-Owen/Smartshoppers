package org.smartshoppers.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="USERS")
public class User {
	
	
	@NotEmpty
	protected String firstName;
	
	@NotEmpty
	protected String lastName;
	
	@Id
	@NotEmpty
	protected String userName;

	@NotEmpty
	protected String password;
	
	@NotEmpty
	protected String role;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	protected Store store;
	
	@ManyToMany(fetch=FetchType.EAGER)
	protected Set<ShoppingItem> shoppingList = new HashSet<>();
	
	
	public User() {
		shoppingList = new HashSet<ShoppingItem>();
	}
	protected User(String firstName, String lastName, String userName,
			String password, String role) {
		super();
		shoppingList = new HashSet<ShoppingItem>();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store myStore) {
		this.store = myStore;
	}
	public Set<ShoppingItem> getShoppingList() {
		return shoppingList;
	}
	public void setShoppingList(Set<ShoppingItem> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return userName;
	}

	public boolean equals(User user) {
		return this.userName.equals(user.userName);
	}
	
	
	
	
}
