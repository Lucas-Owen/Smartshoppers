package org.smartshoppers.views.customer;

import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.forms.CustomizeUserForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="customer-customize-details", layout=CustomerHomeLayout.class)
public class CustomizeUserView extends VerticalLayout {
	protected CustomizeUserForm customizeUserForm;
	protected UserSessionService sessionService;
	
	public CustomizeUserView(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		customizeUserForm = new CustomizeUserForm(sessionService);
		
		init();
	}
	
	protected void init() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(customizeUserForm);
		
		customizeUserForm.setMaxWidth("25em");
		
		customizeUserForm.addListener(CustomizeUserForm.SaveEvent.class, event -> {
			Notification.show("Customizing");
			sessionService.updateUser(event.getUser());
			UI.getCurrent().getPage().reload();
		});
	}
	
}
