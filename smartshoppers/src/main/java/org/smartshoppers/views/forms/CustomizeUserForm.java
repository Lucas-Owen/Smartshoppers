package org.smartshoppers.views.forms;

import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;

public class CustomizeUserForm extends UserSignUpForm {
	protected UserSessionService sessionService;

	protected PasswordField newPassword;
	protected User currentUser;
	
	public CustomizeUserForm(@Autowired UserSessionService sessionService) {
		super();
		this.sessionService = sessionService;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
        String username = auth.getName();
        this.currentUser = sessionService.findUserByUserName(username);
        initializeValues();
	}
	
	@Override
	protected void init() {
		
		
		binder = new BeanValidationBinder<>(User.class);
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		userName = new TextField("Username");
		userName.setEnabled(false);
		password = new PasswordField("Password");
		
		user = new User();
		save = new Button("Update Details");
		
		binder.bindInstanceFields(this);
		newPassword = new PasswordField("New Password");
		binder.removeBinding("role");
		
		add(firstName,
				lastName,
				userName,
				password,
				newPassword,
				configureButtons());
	}
	
	private void initializeValues() {
		firstName.setValue(currentUser.getFirstName());
		lastName.setValue(currentUser.getLastName());
		userName.setValue(currentUser.getUserName());
	}
	
	@Override
	protected HorizontalLayout configureButtons() {
		
		
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
		
		save.addClickShortcut(Key.ENTER); 
		
		save.addClickListener(event -> validateAndSave());  
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		
		HorizontalLayout layout = new HorizontalLayout(save); 
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		
		return layout;
	}
	
	@Override
	protected void validateAndSave() {
		try {
			binder.writeBean(user); 
			sessionService.verifyUser(user);
			if(!newPassword.getValue().equals("")) {
				user.setPassword(newPassword.getValue());
			}
			fireEvent(new SaveEvent(this, user)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			Notification.show("Failed to authenticate update");
		}
	}
	
}
