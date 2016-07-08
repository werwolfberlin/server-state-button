package de.lycantrophia.addon.serverstatebutton.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

// Extend any GWT Widget
@SuppressWarnings("WeakerAccess")
public class ServerStateButtonWidget extends FlexTable {

	private static final int MAX_COLOR = 200;
	private final Label nameLabel = new Label("");
	private final Label usersLabel = new Label("");
	private final Label cpuLoadLabel = new Label("");
	private final Label ramUsageLabel = new Label("");

	private int maxRam = 1024;

	public ServerStateButtonWidget() {

		setStyleName("server-state-button");
		getElement().getStyle().setDisplay(Style.Display.INLINE_TABLE);

		nameLabel.addStyleName("server-state-server-name");
		nameLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		nameLabel.setWidth("100%");

		setWidget(0, 0, nameLabel);
		getFlexCellFormatter().setWidth(0, 0, "100%");

		getFlexCellFormatter().setWidth(1, 0, "100%");
		getFlexCellFormatter().setHeight(1, 0, "100%");

		setCellPadding(0);
		setCellSpacing(0);
		setBorderWidth(0);
		setWidth("100%");
		setHeight("100%");

		final FlexTable flexTable = new FlexTable();
		flexTable.getElement().getStyle().setDisplay(Style.Display.INLINE_TABLE);
		flexTable.setCellPadding(0);
		flexTable.setCellSpacing(3);
		flexTable.setBorderWidth(0);
		flexTable.setWidth("100%");
		flexTable.setHeight("100%");

		// State is set to widget in ServerStateButtonConnector
		final FlexTable.FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();

		//adjustments
		cellFormatter.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

		flexTable.getColumnFormatter().setWidth(1, "100%");

		cellFormatter.setHeight(0, 1, "100%");
		cellFormatter.setHeight(0, 0, "100%");

		cellFormatter.setWidth(0, 1, "100%");
		cellFormatter.setWidth(1, 1, "100%");
		cellFormatter.setWidth(2, 1, "100%");

		// user online
		final Label usersText = new Label("Users");
		usersText.addStyleName("server-state-caption");
		usersLabel.addStyleName("server-state-users");
		flexTable.setWidget(0, 0, usersText);
		flexTable.setWidget(0, 1, usersLabel);

		final Label cpuLoadText = new Label("CPU");
		cpuLoadText.addStyleName("server-state-caption");
		cpuLoadLabel.addStyleName("server-state-bar");
		flexTable.setWidget(1, 0, cpuLoadText);
		flexTable.setWidget(1, 1, cpuLoadLabel);

		final Label ramUsageText = new Label("RAM");
		ramUsageText.addStyleName("server-state-caption");
		ramUsageLabel.addStyleName("server-state-bar");
		flexTable.setWidget(2, 0, ramUsageText);
		flexTable.setWidget(2, 1, ramUsageLabel);

		setWidget(1, 0, flexTable);
	}

	public void setServerName(final String name)
	{
		nameLabel.setText(name);
	}

	public void setStatistics(final String userCount, final double cpuLoad, final int memoryUsage)
	{
		usersLabel.setText(userCount);
		setBarValue(cpuLoadLabel, cpuLoad);
		setBarValue(ramUsageLabel, ((double)memoryUsage/(double)maxRam));
	}

	private void setBarValue(Label label, double value) {
		double val = value > 1 ? 1 : value;
		label.getElement().getStyle().setBackgroundColor(createColor(val));
		label.getElement().getStyle().setWidth(val * 100, Style.Unit.PCT);
	}

	private String createColor(double value)
	{
		int r = value < 0.5 ? (int)(value * 2 * MAX_COLOR) : MAX_COLOR;
		int g = value > 0.5 ? (int)((value - 1) * -2 * MAX_COLOR) : MAX_COLOR;

		return rgbToHexString(r, g, 0);
	}

	private String rgbToHexString(final int r, final int g, final int b)
	{
		return '#' + intToHexString(r) + intToHexString(g) + intToHexString(b);
	}

	private String intToHexString(int r) {
		String s = Integer.toHexString(r);
		return s.length() == 1 ? ('0' + s) : s;
	}

	private String createPercentageString(double number)
	{
		final long round = Math.round(number * 1000);

		return (round / 10) + "." + (round % 10);
	}

	public void setMaxRam(int maxRam)
	{
		this.maxRam = maxRam;
	}
}