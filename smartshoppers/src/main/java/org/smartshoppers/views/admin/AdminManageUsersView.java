package org.smartshoppers.views.admin;

import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.forms.AddUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "admin_add_remove_user", layout=AdminHomeLayout.class)
public class AdminManageUsersView extends HorizontalLayout {
	private AddUserForm addUserForm;
	private UserSessionService sessionService;
	private Grid<User> users = new Grid<>(User.class);
	
	public AdminManageUsersView(@Autowired UserSessionService sessionService) {
		this.sessionService = sessionService;
		addUserForm = new AddUserForm(sessionService);
		init();
		configureForm();
		configureUsers();
		
	}
	
	protected void configureForm() {
		addUserForm.addListener(AddUserForm.SaveEvent.class, event ->{
				User user = event.getUser();
				if(sessionService.addUser(user)) {
					Notification.show("User added");
					UI.getCurrent().getPage().reload();
				}
				else {
					Notification.show("Could not add user");
				}
		});
		addUserForm.addListener(AddUserForm.DeleteEvent.class, event ->{
				if(sessionService.deleteUserById(event.getUser())) {
					Notification.show("User deleted");
					UI.getCurrent().getPage().reload();
				}
				else {
					Notification.show("Could not delete user");
				}
			}
		);
		addUserForm.addListener(AddUserForm.UpdateEvent.class, event ->{
			sessionService.updateUser(event.getUser());
			updateItems();
		});
		addUserForm.setMaxWidth("24em");
	}
	
	protected void configureUsers() {
		users.setSizeFull();
        users.setColumns("userName", "firstName", "lastName", "role"); 
        users.getColumns().forEach(col -> col.setAutoWidth(true)); 
        
        users.asSingleSelect().addValueChangeListener(event -> {
        	User user = event.getValue();
        	if(user != null) addUserForm.updateFields(user);
        });
        
        updateItems();
	}
	
	protected void updateItems() {
		users.setItems(sessionService.findAllUsers());
	}
	
	protected void init() {
		setSizeFull();
		setWidth("100%");
		VerticalLayout left = new VerticalLayout(new H4("All Users"), users);
		VerticalLayout right = new VerticalLayout(addUserForm);
		
		left.setSizeUndefined();
		right.setSizeUndefined();
		
		setFlexGrow(3, left);
		setFlexGrow(0.2, right);
		
		add(left,right);
	}
}
