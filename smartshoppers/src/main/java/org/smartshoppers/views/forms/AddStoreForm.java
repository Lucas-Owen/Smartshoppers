package org.smartshoppers.views.forms;

import org.smartshoppers.model.Store;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

@Component
public class AddStoreForm extends FormLayout { 
  protected Store store;
  protected Binder<Store> binder = new BeanValidationBinder<>(Store.class);
	
  protected TextField name = new TextField("Name"); 
  protected TextField location = new TextField("Location");

  protected Button save = new Button("Add Shop");
  protected Button close = new Button("Cancel");
  
  public AddStoreForm() {
    addClassName("shop-form"); 
    binder.bindInstanceFields(this);
    store = new Store();
    add(name, 
        location,
        createButtonsLayout());
  }

  protected HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER); 
    close.addClickShortcut(Key.ESCAPE);
    
    save.addClickListener(event -> validateAndSave());  
    close.addClickListener(event -> validateAndClose()); 

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
    
    return new HorizontalLayout(save, close); 
  }
  
  public void updateValues(Store updated) {
	  name.setValue(updated.getName());
	  location.setValue(updated.getLocation());
  }
  
  protected void validateAndSave() {
		try {
			binder.writeBean(store); 
			fireEvent(new SaveEvent(this, store)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	protected void validateAndClose() {
		try {
			binder.writeBean(store); 
			fireEvent(new CloseEvent(this, store)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
  
  public void setStore(Store store) {
	  this.store = store;
	  binder.readBean(store);
  }
  //Events
	public static abstract class ShopFormEvent extends ComponentEvent<AddStoreForm> {
	  private Store store;
	
	  protected ShopFormEvent(AddStoreForm source, Store store) { 
	    super(source, false);
	    this.store = store;
	  }
	
	  public Store getStore() {
	    return store;
	  }
	}
	
	public static class SaveEvent extends ShopFormEvent {
	  SaveEvent(AddStoreForm source, Store shop) {
	    super(source, shop);
	  }
	}
	
	
	public static class CloseEvent extends ShopFormEvent {
	  CloseEvent(AddStoreForm source, Store store) {
	    super(source, store);
	  }
	}
	
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
	    ComponentEventListener<T> listener) { 
	  return getEventBus().addListener(eventType, listener);
	}
}