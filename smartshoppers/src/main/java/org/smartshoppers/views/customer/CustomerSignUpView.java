package org.smartshoppers.views.customer;

import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.EntryLayout;
import org.smartshoppers.views.LoginView;
import org.smartshoppers.views.forms.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;

@Route(value="sign-up")
public class CustomerSignUpView extends VerticalLayout {
	
	private UserSignUpForm form = new UserSignUpForm();
	private UserSessionService sessionService;
	
	public CustomerSignUpView(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		
		setId("sign-up-view");
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		form.addListener(UserSignUpForm.SaveEvent.class, event -> {
			try {
				User user = event.getUser();
				user.setRole("USER");
				sessionService.addUser(user);
				UI.getCurrent().navigate(LoginView.class);
			}
			catch(Throwable e) {
				e.printStackTrace();
			}
		});
		
		form.setMaxWidth("20em");
		add(new H1("SmartShoppers"), new Div(form));
	}
}
