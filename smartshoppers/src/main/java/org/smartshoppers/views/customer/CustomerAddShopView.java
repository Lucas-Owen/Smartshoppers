package org.smartshoppers.views.customer;

import org.smartshoppers.model.Store;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.forms.AddStoreForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="addshop", layout=CustomerHomeLayout.class)
public class CustomerAddShopView extends VerticalLayout{
	
	protected AddStoreForm form;
	protected final UserSessionService service;

	public CustomerAddShopView(@Autowired UserSessionService service, @Autowired AddStoreForm form) {
		this.service = service;
		this.form = form;
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		form.addListener(AddStoreForm.SaveEvent.class, this::addShop);
		form.addListener(AddStoreForm.CloseEvent.class, this::close);
		
		form.setWidth("25em");
		add(form);
	}
	
	protected void addShop(AddStoreForm.SaveEvent event) {
		Store store = event.getStore();

        service.addStore(store);
        Notification.show("Success!");
    }
	
	protected void close(AddStoreForm.CloseEvent event) {
        UI.getCurrent().navigate(CustomerHomeView.class);
	}
	
}
