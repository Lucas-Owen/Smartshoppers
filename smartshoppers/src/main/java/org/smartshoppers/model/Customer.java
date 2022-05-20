package org.smartshoppers.model;

import javax.persistence.Entity;

@Entity
public class Customer extends User{

	protected Customer() {
		super();
	}

	public Customer(String firstName, String lastName, String userName, String password) {
		super(firstName, lastName, userName, password, "USER");
	}
	
}
