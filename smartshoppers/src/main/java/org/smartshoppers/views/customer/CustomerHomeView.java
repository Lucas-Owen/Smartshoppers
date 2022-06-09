package org.smartshoppers.views.customer;

import org.smartshoppers.model.ShoppingItem;
import org.smartshoppers.model.Store;
import org.smartshoppers.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value="customer_homepage", layout=CustomerHomeLayout.class)
public class CustomerHomeView extends VerticalLayout{
	protected Grid<Store> stores = new Grid<>(Store.class); 
	protected Grid<ShoppingItem> shoppingItems = new Grid<>(ShoppingItem.class); 
    protected TextField filterText1 = new TextField();
    protected TextField filterText2 = new TextField();
    protected ShoppingItem currentSelectedShoppingItem;
    protected Button addToCartButton = new Button("Add to cart");
    
	protected final UserSessionService service;
	
	public CustomerHomeView(@Autowired UserSessionService service) {
		this.service = service;
		currentSelectedShoppingItem = null;
		
		initLayout();
		updateList();
	}
	
	public void initLayout() {
		
		setSizeFull();
		configureGrid();
		
		addToCartButton.setWidth("100%");
		addToCartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addToCartButton.addClickListener(event -> {
			String currentUserName = service.getCurrentUserName();
			service.addShoppingItemToCurrentUserShoppingList(currentSelectedShoppingItem);
		});

		VerticalLayout l1 = new VerticalLayout(getToolbar1(), stores);
		VerticalLayout l2 = new VerticalLayout(getToolbar2(), shoppingItems, addToCartButton);
		HorizontalLayout content = new HorizontalLayout(l1, l2);

		l1.setSizeFull();
		l2.setSizeFull();
		content.setFlexGrow(2, l1);
		content.setFlexGrow(1, l2);
		content.setSizeFull();
		
		add(content);
	}
	
	

	private void configureGrid() {
        stores.addClassNames("shop-grid");
        stores.setSizeFull();
        stores.setColumns("name", "location"); 
        stores.getColumns().forEach(col -> col.setAutoWidth(true)); 
        stores.asSingleSelect().addValueChangeListener(event ->
        	updateShoppingItems(event.getValue())
        );
        
        shoppingItems.setSizeFull();
        shoppingItems.setColumns("name", "price", "category"); 
        shoppingItems.getColumns().forEach(col -> col.setAutoWidth(true)); 
        shoppingItems.asSingleSelect().addValueChangeListener(event ->
        	currentSelectedShoppingItem = event.getValue()
        );
        
        
        
    }
	
	private void updateShoppingItems(Store store) {
		shoppingItems.setItems(service.findAllShoppingItemsInStore(store.getId()));
	}

	private void updateList() { 
		stores.setItems(service.findAllStores());
    }

    private HorizontalLayout getToolbar1() {
        filterText1.setPlaceholder("Filter by name...");
        filterText1.setClearButtonVisible(true);
        filterText1.setValueChangeMode(ValueChangeMode.LAZY); 
        filterText1.setMaxWidth("30%");
        
        H4 heading = new H4("Stores");
        HorizontalLayout toolbar = new HorizontalLayout(heading, filterText1); 
        toolbar.setWidth("100%");
        toolbar.addClassName("toolbar");
        toolbar.setFlexGrow(2, heading);
        toolbar.setFlexGrow(1, filterText1);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }
    
    private HorizontalLayout getToolbar2() {
        filterText2.setPlaceholder("Filter by name...");
        filterText2.setClearButtonVisible(true);
        filterText2.setValueChangeMode(ValueChangeMode.LAZY);
        filterText2.addValueChangeListener(event -> updateItemsAvailableList(event.getValue()));
        filterText2.setMaxWidth("30%");
        
        H4 heading = new H4("Items Available");
        HorizontalLayout toolbar = new HorizontalLayout(heading, filterText2); 
        toolbar.setWidth("100%");
        toolbar.addClassName("toolbar");
        toolbar.setFlexGrow(2, heading);
        toolbar.setFlexGrow(1, filterText2);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }

	private String updateItemsAvailableList(String value) {
		
		return null;
	}
}
