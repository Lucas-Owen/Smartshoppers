package org.smartshoppers.views.forms;

import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

@Component
public class AdminAddStoreForm extends FormLayout{
	protected UserSessionService sessionService;
	protected Store store;
	protected Binder<Store> binder = new BeanValidationBinder<>(Store.class);
	
	protected TextField name = new TextField("Name"); 
	protected TextField location = new TextField("Location");
	protected ComboBox<User> manager = new ComboBox<>("Manager");
	
	protected Button delete = new Button("Delete");
	protected Button save = new Button("Add Shop");
	protected Button close = new Button("Cancel");
	
	public AdminAddStoreForm(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		addClassName("store-form"); 
	    binder.bindInstanceFields(this);
	    store = new Store();
	    initLayout();
	    
	}
	private void initLayout() {
		manager.setItemLabelGenerator(User::getUserName);
		manager.setItems(sessionService.findAllManagers());
		
		add(name, 
			location,
			manager,
			createButtonsLayout());
	}
	public void updateValues(Store updated) {
		name.setValue(updated.getName());
		location.setValue(updated.getLocation());
		manager.setValue(sessionService.findManagerByStore(updated));
	}
	
	protected HorizontalLayout createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickShortcut(Key.ENTER); 
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(event -> validateAndSave()); 
		delete.addClickListener(event -> validateAndDelete()); 
		close.addClickListener(event -> validateAndClose()); 
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		return new HorizontalLayout(save, close, delete); 
	}
	
	protected void validateAndSave() {
		try {
			binder.writeBean(store); 
			User user = manager.getValue();
			fireEvent(new SaveEvent(this, store, user)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	protected void validateAndDelete() {
		try {
			binder.writeBean(store); 
			User user = manager.getValue();
			fireEvent(new DeleteEvent(this, store, user)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	protected void validateAndClose() {
		User user = manager.getValue();
		fireEvent(new CloseEvent(this, store, user)); 
	}
	
	public void setStore(Store shop) {
		this.store = shop;
		binder.readBean(shop);
	}
	//Events
	public static abstract class StoreFormEvent extends ComponentEvent<AdminAddStoreForm> {
		private Store shop;
		private User manager;
		
		protected StoreFormEvent(AdminAddStoreForm source, Store shop, User manager) { 
			super(source, false);
			this.shop = shop;
			this.manager = manager;
		}
		
		public Store getStore() {
			return shop;
		}
		public User getManager() {
			return manager;
		}
	}
	
	public static class SaveEvent extends StoreFormEvent {
		SaveEvent(AdminAddStoreForm managerShopForm, Store shop, User manager) {
			super(managerShopForm, shop, manager);
		}
	}
	
	
	public static class CloseEvent extends StoreFormEvent {
		CloseEvent(AdminAddStoreForm managerShopForm, Store store, User manager) {
			super(managerShopForm, store, manager);
		}
	}
	
	public static class DeleteEvent extends StoreFormEvent {
		DeleteEvent(AdminAddStoreForm managerShopForm, Store store, User manager) {
			super(managerShopForm, store, manager);
		}
		
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) { 
		return getEventBus().addListener(eventType, listener);
	}
}
