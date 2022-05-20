package org.smartshoppers.views.manager;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerShoppingListView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;

@Route(value="manager-shopping-list", layout=ManagerHomeLayout.class)
public class ManagerShoppingListView extends CustomerShoppingListView{

	public ManagerShoppingListView(@Autowired UserSessionService sessionService) {
		super(sessionService);
	}

}
