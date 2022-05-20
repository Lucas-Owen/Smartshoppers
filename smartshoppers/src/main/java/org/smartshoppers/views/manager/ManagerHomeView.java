package org.smartshoppers.views.manager;

import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerHomeView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;

@Route(value="manager-home",layout=ManagerHomeLayout.class)
public class ManagerHomeView extends CustomerHomeView{
	
	private Store store;
	
	public ManagerHomeView(@Autowired UserSessionService userService) {
		super(userService);
		
		User manager = service.getCurrentUser();
        store = service.findStoreByManager(manager);
        if(store == null) UI.getCurrent().navigate("");
	}
	
}
