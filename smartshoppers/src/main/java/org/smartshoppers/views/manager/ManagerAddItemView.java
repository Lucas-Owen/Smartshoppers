package org.smartshoppers.views.manager;

import org.smartshoppers.model.ShoppingItem;
import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.forms.AddShoppingItemForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="manager-add-item",layout=ManagerHomeLayout.class)
public class ManagerAddItemView extends VerticalLayout{
	
	private AddShoppingItemForm shoppingItemForm = new AddShoppingItemForm();
	private UserSessionService sessionService;
	private Store store;
	
	public ManagerAddItemView(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		User manager = sessionService.getCurrentUser();
		store = manager.getStore();
		init();
		configureForm();
        
	}
	
	protected void configureForm() {
		shoppingItemForm.addListener(AddShoppingItemForm.SaveEvent.class, event ->{
			ShoppingItem shoppingItem = event.getShoppingItem();
			shoppingItem.setStore(store.getId());
			sessionService.addShoppingItem(shoppingItem);
		}
		);
		shoppingItemForm.addListener(AddShoppingItemForm.UpdateEvent.class, event -> {
			ShoppingItem shoppingItem = event.getShoppingItem();
			shoppingItem.setStore(store.getId());
			sessionService.updateShoppingItem(shoppingItem);
		}
		);		
		shoppingItemForm.setTitle(store.getId().toUpperCase());
	}
	
	protected void init() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		shoppingItemForm.setMaxWidth("26em");
		add(shoppingItemForm);
	}
}
