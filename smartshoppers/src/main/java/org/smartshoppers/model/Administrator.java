package org.smartshoppers.model;

import javax.persistence.Entity;

@Entity
public class Administrator extends User{

	protected Administrator() {
		super();
	}

	public Administrator(String firstName, String lastName, String userName, String password) {
		super(firstName, lastName, userName, password, "ADMIN");
	}



}
