package org.smartshoppers.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport("./styles/shared-styles.css")
public class EntryLayout extends AppLayout {
	
    public EntryLayout() {
        createHeader();
    }

    protected void createHeader() {
        H1 logo = new H1("SmartShoppers");

        HorizontalLayout header = new HorizontalLayout(
          logo
        );
        
        header.setSizeFull();
        logo.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        header.setWidth("100%");

        addToNavbar(header); 

    }
    
}
