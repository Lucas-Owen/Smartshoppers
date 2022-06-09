package org.smartshoppers.views;

import org.smartshoppers.model.Administrator;
import org.smartshoppers.model.Customer;
import org.smartshoppers.model.Manager;
import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.security.SecurityService;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.customer.CustomerSignUpView;
import org.smartshoppers.views.forms.UserLoginForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@CssImport("./styles/shared-styles.css")
@Route(value="login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	public final static String ROUTE = "login";
	
	
	protected final UserLoginForm loginForm = new UserLoginForm();
	private SecurityService securityService;
	private UserSessionService userService;
	
	public LoginView(@Autowired UserSessionService userService){
		this.userService = userService;

		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		init();
	}
	
	protected void init() {
		initTempRepo();
		
		Button loginButton = new Button("Log In", event -> {
			loginForm.setOpened(true);
		});
		Button signUp = new Button("Sign Up", event -> UI.getCurrent().navigate(CustomerSignUpView.class));
		H1 header = new H1("SmartShoppers");
		add(header, loginButton, signUp, loginForm);
		
	}
	
	private void initTempRepo() {
		User admin = new Administrator("admin", "Admin", "admin", "password");
		
		User manager = new Manager("manager", "Manager", "manager", "password");
		
		User customer = new Customer("customer", "Customer", "customer", "password");
		
		Store store = new Store();
		store.setName("Target");
		store.setLocation("2nd Street");
		manager.setStore(store);

		userService.addStore(store);
		userService.addUser(admin);
		userService.addUser(manager);
		userService.addUser(customer);

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(event.getLocation()  
	        .getQueryParameters()
	        .getParameters()
	        .containsKey("error")) {
	            loginForm.setError(true);
        }
	}
}
