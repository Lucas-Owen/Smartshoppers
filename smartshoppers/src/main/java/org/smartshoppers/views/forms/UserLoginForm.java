package org.smartshoppers.views.forms;

import com.vaadin.flow.component.login.LoginOverlay;

public class UserLoginForm extends LoginOverlay {
	
	public UserLoginForm() {
		super();
		setForgotPasswordButtonVisible(false);
		setAction("login");
		setTitle("SmartShoppers");
		setDescription("");
	}
}
