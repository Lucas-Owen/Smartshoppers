package org.smartshoppers.views.customer;

import java.util.HashSet;
import java.util.Set;

import org.smartshoppers.model.ShoppingItem;
import org.smartshoppers.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="customer-shopping-list",layout=CustomerHomeLayout.class)
public class CustomerShoppingListView extends VerticalLayout {
	
	protected Double total = 0.0;
	
	protected UserSessionService sessionService;
	protected Grid<ShoppingItem> shoppingItems = new Grid<>(ShoppingItem.class); 
	
	public CustomerShoppingListView(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		
		initLayout();
	}
	
	protected void initLayout() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		Button checkOutButton = new Button("Checkout");
		checkOutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		configureShoppingList();
		Div summary = new Div();
		summary.add(String.format("Total: %.2f", total));
		HorizontalLayout layout = new HorizontalLayout(summary, checkOutButton);
		layout.setSizeFull();
		layout.setHeight("3em");
		layout.expand(summary);
		
        add(shoppingItems, layout);
        expand(shoppingItems);
	}
	
	protected void configureShoppingList() {
		shoppingItems.setSizeFull();
        shoppingItems.setColumns("name", "price", "category"); 
        shoppingItems.getColumns().forEach(col -> col.setAutoWidth(true)); 
        Set<ShoppingItem> items = sessionService.getCurrentUser().getShoppingList();
        total = items.stream().mapToDouble((ShoppingItem shoppingItem) -> shoppingItem.getPrice()).sum();
        shoppingItems.setItems(items);
	}
}
