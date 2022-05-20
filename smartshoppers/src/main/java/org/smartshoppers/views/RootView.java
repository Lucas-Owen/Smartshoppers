package org.smartshoppers.views;

import org.smartshoppers.model.User;
import org.smartshoppers.security.SecurityUtils;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.admin.AdminHomeView;
import org.smartshoppers.views.customer.CustomerHomeView;
import org.smartshoppers.views.manager.ManagerHomeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value="", layout=EntryLayout.class)
public class RootView extends VerticalLayout implements BeforeEnterObserver{
	
	private final UserSessionService service;
		
	public RootView(@Autowired UserSessionService service) {
		this.service = service;
	}
	
		@Override
		public void beforeEnter(BeforeEnterEvent event) {
			if(!SecurityUtils.isUserLoggedIn()) {
				event.rerouteTo(LoginView.class);
				return;
			}
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
	        String username = auth.getName();
	        User user = service.findUserByUserName(username);
	        String role = user.getRole();

			if("USER".equals(role)) {
				event.forwardTo(CustomerHomeView.class);
				return;
			}
			if("MANAGER".equals(role)) {
				event.forwardTo(ManagerHomeView.class);
				return;
			}
			if("ADMIN".equals(role)) {
				event.forwardTo(AdminHomeView.class);
				return;
			}
		}

	    
	
}
