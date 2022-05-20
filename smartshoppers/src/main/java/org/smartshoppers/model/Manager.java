package org.smartshoppers.model;

import javax.persistence.Entity;

@Entity
public class Manager extends User{
	protected Manager() {
		super();
	}
	public Manager(String firstName, String lastName, String userName, String password) {
		super(firstName, lastName, userName, password, "MANAGER");
	}


}
