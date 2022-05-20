package org.smartshoppers.views.forms;

import java.util.Arrays;

import org.smartshoppers.model.ShoppingItem;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class AddShoppingItemForm extends FormLayout {
	protected ShoppingItem shoppingItem;
	protected Binder<ShoppingItem> binder = new BeanValidationBinder<>(ShoppingItem.class);
	
	protected TextField name = new TextField("Item name");
	protected NumberField price = new NumberField("Item price");
	protected ComboBox<String> category = new ComboBox<>("Item category");
	
	protected Button save;
	protected Button remove;
	protected Button update;
	private H4 title;
	
	public AddShoppingItemForm() {
		
		shoppingItem = new ShoppingItem();
		save = new Button("Add Item");
		update = new Button("Update Item");
		remove = new Button("Delete Item");
		title = new H4("No store");
		category.setItems(Arrays.asList("FoodStuff", "Electronic"));
		init();
	}
	
	protected void init() {
		binder.bindInstanceFields(this);
		
		add(title,
			name,
			price,
			category,
			configureButtons());
	}
	
	public ShoppingItem getUser() {
		return shoppingItem;
	}


	public void setUser(ShoppingItem shoppingItem) {
		this.shoppingItem = shoppingItem;
	}


	protected HorizontalLayout configureButtons() {
		
		
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
		update.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		remove.addThemeVariants(ButtonVariant.LUMO_ERROR);
		
		save.addClickShortcut(Key.ENTER); 
		update.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(event -> validateAndSave());  
		update.addClickListener(event -> fireEvent(new UpdateEvent(this, shoppingItem))); 
		remove.addClickListener(event -> fireEvent(new DeleteEvent(this, shoppingItem)));
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		return addButtons();
	}
	
	protected HorizontalLayout addButtons() {
		HorizontalLayout layout = new HorizontalLayout(save, update, remove); 
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		
		return layout;
		
	}
	
	protected void validateAndSave() {
		try {
			binder.writeBean(shoppingItem); 
			fireEvent(new SaveEvent(this, shoppingItem)); 
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	
	public static abstract class ShoppingItemFormEvent extends ComponentEvent<AddShoppingItemForm> {
		private ShoppingItem shoppingItem;
		
		protected ShoppingItemFormEvent(AddShoppingItemForm source, ShoppingItem shoppingItem) { 
			super(source, false);
			this.shoppingItem = shoppingItem;
		}
		
		public ShoppingItem getShoppingItem() {
			return shoppingItem;
		}
	}
	
	public static class SaveEvent extends ShoppingItemFormEvent {
		SaveEvent(AddShoppingItemForm source, ShoppingItem shoppingItem) {
			super(source, shoppingItem);
		}
	}
	
	
	public static class UpdateEvent extends ShoppingItemFormEvent {
		UpdateEvent(AddShoppingItemForm source, ShoppingItem shoppingItem) {
			super(source, shoppingItem);
		}
	}
	
	public static class DeleteEvent extends ShoppingItemFormEvent {
		DeleteEvent(AddShoppingItemForm source, ShoppingItem shoppingItem) {
			super(source, shoppingItem);
		}
		
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) { 
		return getEventBus().addListener(eventType, listener);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
}
