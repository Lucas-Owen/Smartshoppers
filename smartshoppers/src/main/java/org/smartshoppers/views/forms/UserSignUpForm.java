package org.smartshoppers.views.forms;

import org.smartshoppers.model.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class UserSignUpForm extends FormLayout {
	protected User user;
	protected Binder<User> binder;
	
	protected TextField firstName;
	protected TextField lastName;
	protected TextField userName;
	protected PasswordField password;
	
	
	protected Button save;
	protected Button remove;
	protected Button close;
	
	public UserSignUpForm() {
		
		
		init();
	}
	
	protected void init() {
		
		
		binder = new BeanValidationBinder<>(User.class);
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		userName = new TextField("Username");
		password = new PasswordField("Password");
		
		user = new User();
		save = new Button("Sign Up");
		close = new Button("Cancel");
		remove = new Button("Delete User");
		
		binder.bindInstanceFields(this);
		binder.removeBinding("role");
		add(firstName,
				lastName,
				userName,
				password,
				configureButtons());
	}
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	protected HorizontalLayout configureButtons() {
		
		
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		remove.addThemeVariants(ButtonVariant.LUMO_ERROR);
		
		save.addClickShortcut(Key.ENTER); 
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(event -> validateAndSave());  
		close.addClickListener(event -> fireEvent(new CloseEvent(this))); 
		remove.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		return addButtons();
	}
	
	
	
	protected HorizontalLayout addButtons() {
		HorizontalLayout layout = new HorizontalLayout(save, close); 
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		layout.setFlexGrow(1, save);
		layout.setFlexGrow(1, close);
		return layout;
		
	}
	
	protected void validateAndSave() {
		try {
			binder.writeBean(user); 
			fireEvent(new SaveEvent(this, user)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	public static abstract class UserSignUpFormEvent extends ComponentEvent<UserSignUpForm> {
		private User user;
		
		protected UserSignUpFormEvent(UserSignUpForm source, User user) { 
			super(source, false);
			this.user = user;
		}
		
		public User getUser() {
			return user;
		}
	}
	
	public static class SaveEvent extends UserSignUpFormEvent {
		SaveEvent(UserSignUpForm source, User user) {
			super(source, user);
		}
	}
	
	
	public static class CloseEvent extends UserSignUpFormEvent {
		CloseEvent(UserSignUpForm source) {
			super(source, null);
		}
	}
	
	public static class DeleteEvent extends UserSignUpFormEvent {
		DeleteEvent(UserSignUpForm source, User user) {
			super(source, user);
		}
		
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) { 
		return getEventBus().addListener(eventType, listener);
	}
	
}
