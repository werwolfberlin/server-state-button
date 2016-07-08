package de.lycantrophia.addon.serverstatebutton.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;

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

		setCellPadding(0);
		setCellSpacing(0);
		setBorderWidth(0);
		// CSS class-name should not be v- prefixed
		setStyleName("server-state-button");

		// State is set to widget in ServerStateButtonConnector
		final FlexCellFormatter cellFormatter = getFlexCellFormatter();

		getColumnFormatter().setWidth(1, "100%");
		cellFormatter.setHeight(1, 1, "100%");

		cellFormatter.setAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

//		cellFormatter.setAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_BOTTOM);
//		cellFormatter.setAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_BOTTOM);

		//header
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setWidth(0, 0, "100%");
		cellFormatter.setWidth(1, 1, "100%");
		cellFormatter.setWidth(2, 1, "100%");
		cellFormatter.setWidth(3, 1, "100%");

		cellFormatter.setHeight(1, 1, "100%");

		nameLabel.addStyleName("server-state-server-name");
		nameLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(0, 0, nameLabel);

		// user online
		final Label usersText = new Label("Users");
		usersText.addStyleName("server-state-caption");
		usersLabel.addStyleName("server-state-users");
		setWidget(1, 0, usersText);
		setWidget(1, 1, usersLabel);

		Label cpuLoadText = new Label("CPU");
		cpuLoadText.addStyleName("server-state-caption");
		cpuLoadLabel.addStyleName("server-state-bar");
		setWidget(2, 0, cpuLoadText);
		setWidget(2, 1, cpuLoadLabel);

		Label ramUsageText = new Label("RAM");
		ramUsageText.addStyleName("server-state-caption");
		ramUsageLabel.addStyleName("server-state-bar");
		setWidget(3, 0, ramUsageText);
		setWidget(3, 1, ramUsageLabel);
	}

	public void setServerName(final String name)
	{
		nameLabel.setText(name);
	}

	public void setStatistics(final String userCount, final double cpuLoad, final int memoryUsage)
	{
		usersLabel.setText(userCount);

//		cpuLoadLabel.setText(createPercentageString(cpuLoad));
		setBarValue(cpuLoadLabel, cpuLoad);

//		ramUsageLabel.setText(memoryUsage + "MB");
		setBarValue(ramUsageLabel, ((double)memoryUsage/(double)maxRam));
	}

	private void setBarValue(Label label, double value) {
		String color = createColor(value);
		label.getElement().getStyle().setBackgroundColor(color);
		label.getElement().getStyle().setWidth(value * 100, Style.Unit.PCT);
//		label.setText(color);
	}

	private String createColor(double cpuLoad)
	{
		int r = cpuLoad < 0.5 ? (int)(cpuLoad * 2 * MAX_COLOR) : MAX_COLOR;
		int g = cpuLoad > 0.5 ? (int)((cpuLoad - 0.5) * 2 * MAX_COLOR) : MAX_COLOR;

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