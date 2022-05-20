package org.smartshoppers.views.manager;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomizeUserView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;

@Route(value="manager-customize-user-view", layout=ManagerHomeLayout.class)
public class ManagerCustomizeUserView extends CustomizeUserView {

	public ManagerCustomizeUserView(@Autowired UserSessionService sessionService) {
		super(sessionService);
		
	}

}
