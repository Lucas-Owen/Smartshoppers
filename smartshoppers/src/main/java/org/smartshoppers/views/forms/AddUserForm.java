package org.smartshoppers.views.forms;

import java.util.Arrays;
import java.util.Collection;

import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

public class AddUserForm extends UserSignUpForm {
	UserSessionService sessionService;
	protected ComboBox<Store> store; 
	protected ComboBox<String> role;
	
	public AddUserForm(UserSessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}
	
	@Override
	protected void init() {
		binder = new BeanValidationBinder<>(User.class);
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		userName = new TextField("Username");
		password = new PasswordField("Password");
		role = new ComboBox<>("Role");
		store = new ComboBox<>("Store Managing");
		store.setItemLabelGenerator(Store::getId);
		
		role.setItems(Arrays.asList("USER", "MANAGER", "ADMIN"));
		
		user = new User();
		save = new Button("Add User");
		close = new Button("Update User");
		remove = new Button("Delete User");
		
		
		binder.bindInstanceFields(this);
		store.setVisible(false);
		add(firstName,
				lastName,
				userName,
				password,
				role,
				store,
				configureButtons());
		
		role.addValueChangeListener(event -> valueChangeHandler(event.getValue()));

	}
	
	@Override
	protected HorizontalLayout addButtons() {
		HorizontalLayout layout = new HorizontalLayout(save, close, remove); 
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		return layout;
	}
	
	@Override
	protected void validateAndSave() {
		try {
			binder.writeBean(user); 
			fireEvent(new SaveEvent(this, user)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected HorizontalLayout configureButtons() {
		
		
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		remove.addThemeVariants(ButtonVariant.LUMO_ERROR);
		
		save.addClickShortcut(Key.ENTER); 
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(event -> validateAndSave());  
		close.addClickListener(event -> updateClickHandler()); 
		remove.addClickListener(event -> deleteClickHandler());
		
		userName.addValueChangeListener(event -> configurePasswordField(event.getValue()));
		userName.setValueChangeMode(ValueChangeMode.LAZY);
		
		password.setRevealButtonVisible(false);
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		return addButtons();
	}
	
	private void configurePasswordField(String value) {
		boolean exists = sessionService.userNameExists(value);
		password.setEnabled(!exists);
		save.setEnabled(!exists);
		password.clear();

		if(exists) {
			User user = sessionService.findUserByUserName(value);
			Collection<Store> stores = sessionService.findAllStoresWithoutManager();
			if(user.getRole().equals("MANAGER")) {
				Store store = user.getStore();
				if(store != null) stores.add(store);
			}
			store.setItems(stores);
		}
	}

	protected void valueChangeHandler(String value) {
		if(value.equals("MANAGER")) {
			store.setVisible(true);
		}
		else{
			store.clear();
			store.setVisible(false);
		}
	}
	
	protected void updateClickHandler() {
		try {
			binder.writeBean(user);
			fireEvent(new UpdateEvent(this, user));
			
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	protected void deleteClickHandler() {
		try {
			binder.writeBean(user);
			fireEvent(new DeleteEvent(this, user));
			
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	public void updateFields(User user) {
		firstName.setValue(user.getFirstName());
		lastName.setValue(user.getLastName());
		userName.setValue(user.getUserName());
		password.setValue(user.getPassword());
		role.setValue(user.getRole());
		if(user.getRole().equals("MANAGER")) store.setValue(user.getStore());
		save.setEnabled(false);
	}
	
	public static class UpdateEvent extends UserSignUpFormEvent {
		UpdateEvent(UserSignUpForm source, User user) {
			super(source, user);
		}
	}
}