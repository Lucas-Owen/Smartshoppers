package org.smartshoppers.views.admin;

import org.smartshoppers.model.User;
import org.smartshoppers.security.SecurityService;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerHomeLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;

public class AdminHomeLayout extends CustomerHomeLayout{

    public AdminHomeLayout(@Autowired SecurityService service, @Autowired UserSessionService sessionService) {
		super(service, sessionService);
	}
    
    @Override
    protected void verifyUser(BeforeEnterEvent event) {
    	User currentUser = sessionService.getCurrentUser();
    	if(!currentUser.getRole().equals("ADMIN")) {
    		event.rerouteTo("");
    	}
    }

	@Override
    protected void createDrawer() {
        
        Button viewShopsButton = new Button("View Stores", event -> UI.getCurrent().navigate(AdminHomeView.class)); 
        viewShopsButton.setWidth("100%");
        
        Button manageShopsButton = new Button("Manage Stores", event -> UI.getCurrent().navigate(AdminManageStoresView.class)); 
        manageShopsButton.setWidth("100%");
        
        Button manageUsersButton = new Button("Manage Users", event -> UI.getCurrent().navigate(AdminManageUsersView.class)); 
        manageUsersButton.setWidth("100%");
        
        Button customizeUserButton = new Button("Customize Details", event -> UI.getCurrent().navigate(AdminCustomizeUserView.class)); 
        customizeUserButton.setWidth("100%");
        
        Button displayShoppingListButton = new Button("My Shopping List", event -> UI.getCurrent().navigate(AdminShoppingListView.class)); 
        displayShoppingListButton.setWidth("100%");
        
        addToDrawer(new VerticalLayout( 
        	viewShopsButton,
        	manageUsersButton,
            manageShopsButton,
            displayShoppingListButton,
            customizeUserButton
        ));
    }
}
