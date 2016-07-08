package de.lycantrophia.demo;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import de.lycantrophia.addon.serverstatebutton.ServerStateButton;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("ServerStateButton Add-on Demo")
@SuppressWarnings({"serial", "WeakerAccess"})
public class DemoUI extends UI
{
    private static final int MAX_RAM = 4096;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "de.lycantrophia.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {


        // Show it in the middle of the screen
        final HorizontalLayout layout = new HorizontalLayout(
                createButtonPair(),
                createButtonPair(),
                createButtonPair(),
                createButtonPair(),
                createButtonPair(),
                createButtonPair());

        layout.setSpacing(true);

        VerticalLayout frame = new VerticalLayout(layout);
        frame.setHeight(100, Unit.PERCENTAGE);
        frame.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

        setContent(frame);
    }

    private VerticalLayout createButtonPair()
    {
        String users = "" + (int) (Math.random() * 20);
        double cpuLoad = Math.random();
        int memoryUsage = (int) (Math.random() * MAX_RAM);

        final VerticalLayout components = new VerticalLayout(createButton(true, users, cpuLoad, memoryUsage),
                                                             createButton(false, users, cpuLoad, memoryUsage));
        components.setSpacing(true);
        return components;
    }

    private ServerStateButton createButton(boolean isVertivalLayout, String users, double cpuLoad, int memoryUsage) {
        final ServerStateButton component = new ServerStateButton(isVertivalLayout);
        component.setServerName("MCServer");
        component.setMaxRam(MAX_RAM);
        component.updateServerInfo(users, cpuLoad, memoryUsage);
        component.setWidth(140, Unit.PIXELS);
        component.setHeight(140, Unit.PIXELS);
        return component;
    }
}
