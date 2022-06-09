package org.smartshoppers.security;

import org.smartshoppers.views.LoginView;
import org.smartshoppers.views.customer.CustomerSignUpView;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) { 
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	private void authenticateNavigation(BeforeEnterEvent event) { 
		if (!SecurityUtils.isUserLoggedIn()) {
			if(CustomerSignUpView.class.equals(event.getNavigationTarget()))
				event.rerouteTo(CustomerSignUpView.class);
			else event.rerouteTo(LoginView.class);
		}
	}
}