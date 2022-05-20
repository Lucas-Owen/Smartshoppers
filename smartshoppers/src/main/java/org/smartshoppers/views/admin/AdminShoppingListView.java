package org.smartshoppers.views.admin;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerShoppingListView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;

@Route(value="admin-shopping-list", layout=AdminHomeLayout.class)
public class AdminShoppingListView extends CustomerShoppingListView{

	public AdminShoppingListView(@Autowired UserSessionService sessionService) {
		super(sessionService);
	}

}