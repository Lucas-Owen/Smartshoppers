package org.smartshoppers.views.admin;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomizeUserView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;

@Route(value="admin-customize-user-view", layout=AdminHomeLayout.class)
public class AdminCustomizeUserView extends CustomizeUserView {

	public AdminCustomizeUserView(@Autowired UserSessionService sessionService) {
		super(sessionService);
		
	}

}
