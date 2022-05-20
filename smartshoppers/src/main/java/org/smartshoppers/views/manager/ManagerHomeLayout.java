package org.smartshoppers.views.manager;

import org.smartshoppers.model.User;
import org.smartshoppers.security.SecurityService;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerHomeLayout;
import org.smartshoppers.views.customer.CustomizeUserView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;

public class ManagerHomeLayout extends CustomerHomeLayout{
	public ManagerHomeLayout(@Autowired SecurityService service, @Autowired UserSessionService sessionService) {
		super(service, sessionService);
	}
	
	@Override
	protected void verifyUser(BeforeEnterEvent event) {
    	User currentUser = sessionService.getCurrentUser();
    	if(!currentUser.getRole().equals("MANAGER")) {
    		event.rerouteTo("");
    	}
    }
	
	@Override
    protected void createDrawer() {
        
        Button myStoreButton = new Button("My Store", event -> UI.getCurrent().navigate(ManagerAddItemView.class)); 
        myStoreButton.setWidth("100%");
        
        Button viewStoresButton = new Button("View Stores", event -> UI.getCurrent().navigate(ManagerHomeView.class)); 
        viewStoresButton.setWidth("100%");
        
        Button displayShoppingListButton = new Button("My Shopping List", event -> UI.getCurrent().navigate(ManagerShoppingListView.class)); 
        displayShoppingListButton.setWidth("100%");
        
        Button customizeUserButton = new Button("Customize Details", event -> UI.getCurrent().navigate(ManagerCustomizeUserView.class)); 
        customizeUserButton.setWidth("100%");
                
        addToDrawer(new VerticalLayout(
            myStoreButton, 
            viewStoresButton,
            displayShoppingListButton,
            customizeUserButton
        ));
    }
}
