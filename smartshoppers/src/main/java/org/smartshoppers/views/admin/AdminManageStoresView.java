package org.smartshoppers.views.admin;

import org.smartshoppers.model.ShoppingItem;
import org.smartshoppers.model.Store;
import org.smartshoppers.model.User;
import org.smartshoppers.service.UserSessionService;
import org.smartshoppers.views.forms.AdminAddStoreForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="admin-manage-shops",layout=AdminHomeLayout.class)
public class AdminManageStoresView extends VerticalLayout{

	protected AdminAddStoreForm form;
	protected final UserSessionService service;
	
	protected Grid<Store> stores = new Grid<>(Store.class); 
	protected Grid<ShoppingItem> shoppingItems = new Grid<>(ShoppingItem.class); 
    protected TextField filterText = new TextField();

	public AdminManageStoresView(@Autowired UserSessionService service) {
		this.service = service;
		this.form = new AdminAddStoreForm(service);
		
		initLayout();
		updateList();
	}
	
	
	
	protected void initLayout() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		form.addListener(AdminAddStoreForm.SaveEvent.class, this::addStore);
		form.addListener(AdminAddStoreForm.DeleteEvent.class, this::deleteStore);
		form.addListener(AdminAddStoreForm.CloseEvent.class, this::close);
		
		form.setWidth("25em");
		
		configureGrid();
		
		VerticalLayout l1 = new VerticalLayout(getToolbar(), stores);
		VerticalLayout l2 = new VerticalLayout(new H4("Store Details"), form);
		
		HorizontalLayout content = new HorizontalLayout(l1, l2);

		l1.setSizeFull();
		l2.setSizeFull();
		content.setFlexGrow(2, l1);
		content.setFlexGrow(1, l2);
		content.setSizeFull();
		
		add(content);
	}

	private void configureGrid() {
        stores.addClassNames("store-grid");
        stores.setSizeFull();
        stores.setColumns("name", "location"); 
        stores.getColumns().forEach(col -> col.setAutoWidth(true)); 
        stores.asSingleSelect().addValueChangeListener(event ->{
        	Store store = event.getValue();
        	form.updateValues(store);
        }); 
        
    }
	
	private void updateList() { 
		stores.setItems(service.findAllStores());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); 
        filterText.setMaxWidth("30%");
        H4 heading = new H4("Stores");
        HorizontalLayout toolbar = new HorizontalLayout(heading, filterText); 
        toolbar.setWidth("100%");
        toolbar.addClassName("toolbar");
        toolbar.setFlexGrow(2, heading);
        toolbar.setFlexGrow(1, filterText);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        return toolbar;
    }
    
    protected void addStore(AdminAddStoreForm.SaveEvent event) {
    	Store store = event.getStore();
    	User manager = event.getManager();
    	if(manager != null) {
    		service.addStore(store, manager);
    	}
    	else {
    		service.addStore(store);
    	}
    	Notification.show("Success!");
    	updateList();
    }
    
    protected void deleteStore(AdminAddStoreForm.DeleteEvent event) {
    	Store store = event.getStore();
    	User manager = event.getManager();
    	if(manager != null) {
    		service.deleteStoreWithManager(manager);
    	}
    	else{
    		service.deleteStoreById(store);
    	}
    	Notification.show("Success");
    	UI.getCurrent().getPage().reload();
    }
    
    protected void close(AdminAddStoreForm.CloseEvent event) {
    	UI.getCurrent().navigate(AdminHomeView.class);
    }
}
