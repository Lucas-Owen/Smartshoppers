package org.smartshoppers.views.admin;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerHomeView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;

@Route(value="admin-home", layout=AdminHomeLayout.class)
public class AdminHomeView extends CustomerHomeView{

	public AdminHomeView(@Autowired UserSessionService service) {
		super(service);
	}

}
