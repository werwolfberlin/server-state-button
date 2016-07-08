package de.lycantrophia.demo;

import de.lycantrophia.addon.serverstatebutton.ServerStateButton;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("ServerStateButton Add-on Demo")
@SuppressWarnings({"serial", "WeakerAccess"})
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "de.lycantrophia.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        final ServerStateButton component = new ServerStateButton();
        component.setServerName("MCServer");
        component.setMaxRam(4096);
        component.updateServerInfo("5", 0.35, 684);
        component.setWidth(140, Unit.PIXELS);
        component.setHeight(140, Unit.PIXELS);

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponent(component);
        layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

}
