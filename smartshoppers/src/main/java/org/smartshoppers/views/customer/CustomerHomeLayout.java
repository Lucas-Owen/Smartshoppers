package org.smartshoppers.views.customer;

import org.smartshoppers.model.User;
import org.smartshoppers.security.SecurityService;
import org.smartshoppers.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

@CssImport("./styles/shared-styles.css")
public class CustomerHomeLayout extends AppLayout implements BeforeEnterObserver {
	private final SecurityService service;
	protected final UserSessionService sessionService;
	
    public CustomerHomeLayout(@Autowired SecurityService service, @Autowired UserSessionService sessionService) {
    	this.service = service;
    	this.sessionService = sessionService;
    	createHeader();
        createDrawer();
    }
    
    protected void verifyUser(BeforeEnterEvent event) {
    	User currentUser = sessionService.getCurrentUser();
    	if(!currentUser.getRole().equals("USER")) {
    		event.rerouteTo("");
    	}
    }
    
    protected void createHeader() {
        H1 logo = new H1("SmartShoppers");
        Button logoutButton = new Button("Logout", event -> service.logout()); 

        HorizontalLayout header = new HorizontalLayout(
          new DrawerToggle(), 
          logo,
          logoutButton
        );
        logo.setWidth("80%");
        logoutButton.setWidth("10%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
        header.setWidth("100%");

        addToNavbar(header); 

    }

    protected void createDrawer() {
    	
        Button addShopButton = new Button("Add Store", event -> UI.getCurrent().navigate(CustomerAddShopView.class)); 
        addShopButton.setWidth("100%");
        
        Button viewShopsButton = new Button("View Stores", event -> UI.getCurrent().navigate(CustomerHomeView.class)); 
        viewShopsButton.setWidth("100%");
        
        Button displayShoppingListButton = new Button("My Shopping List", event -> UI.getCurrent().navigate(CustomerShoppingListView.class)); 
        displayShoppingListButton.setWidth("100%");
        
        Button customizeUserButton = new Button("Customize Details", event -> UI.getCurrent().navigate(CustomizeUserView.class)); 
        customizeUserButton.setWidth("100%");
        
        addToDrawer(new VerticalLayout( 
            addShopButton, 
            viewShopsButton,
            displayShoppingListButton,
            customizeUserButton
        ));
    }

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		verifyUser(event);
		
	}
    
}
