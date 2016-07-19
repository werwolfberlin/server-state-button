package de.lycantrophia.addon.serverstatebutton.client;

import com.google.gwt.dom.client.Style;
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

	private boolean isVertical = false;

	public ServerStateButtonWidget() {

		addStyleName("server-state-button");
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
	}

	public void setVertical(boolean vertical) {
		isVertical = vertical;
		setWidget(1, 0, isVertical ? createVerticalLayout() : createHorizontalLayout());
	}

	private FlexTable createVerticalLayout()
	{
		final FlexTable contentTable = new FlexTable();
		contentTable.setCellPadding(0);
		contentTable.setCellSpacing(3);
		contentTable.setBorderWidth(0);
		contentTable.setWidth("100%");
		contentTable.setHeight("100%");

		final ColumnFormatter columnFormatter = contentTable.getColumnFormatter();
		columnFormatter.setWidth(0, "25%");
		columnFormatter.setWidth(1, "25%");
		columnFormatter.setWidth(2, "25%");
		columnFormatter.setWidth(3, "25%");

		// State is set to widget in ServerStateButtonConnector
		final FlexCellFormatter cellFormatter = contentTable.getFlexCellFormatter();
		cellFormatter.setColSpan(0, 1, 2);
		cellFormatter.setColSpan(1, 0, 2);
		cellFormatter.setRowSpan(0, 0, 3);
		cellFormatter.setRowSpan(0, 2, 3);

		cellFormatter.setHeight(0, 0, "100%");
		cellFormatter.setHeight(0, 1, "100%");

		//adjustments
		cellFormatter.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_BOTTOM);
		cellFormatter.setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_BOTTOM);

		cellFormatter.setAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_BOTTOM);

		cellFormatter.setAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT,   HasVerticalAlignment.ALIGN_BOTTOM);
		cellFormatter.setAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT,  HasVerticalAlignment.ALIGN_BOTTOM);

		// user online
		final Label usersText = new Label("Users");
		usersText.addStyleName("server-state-caption");
		usersLabel.addStyleName("server-state-users");
		contentTable.setWidget(0, 1, usersLabel);
		contentTable.setWidget(1, 0, usersText);

		final Label cpuLoadText = new Label("CPU");
		cpuLoadText.addStyleName("server-state-caption");
		contentTable.setWidget(0, 0, cpuLoadLabel);
		contentTable.setWidget(2, 0, cpuLoadText);
		cellFormatter.addStyleName(0, 0, "bar-border");

		final Label ramUsageText = new Label("RAM");
		ramUsageText.addStyleName("server-state-caption");
		contentTable.setWidget(0, 2, ramUsageLabel);
		contentTable.setWidget(2, 1, ramUsageText);
		cellFormatter.addStyleName(0, 2, "bar-border");

		return contentTable;
	}

	private FlexTable createHorizontalLayout()
	{
		final FlexTable contentTable = new FlexTable();
		contentTable.getElement().getStyle().setDisplay(Style.Display.INLINE_TABLE);
		contentTable.setCellPadding(0);
		contentTable.setCellSpacing(3);
		contentTable.setBorderWidth(0);
		contentTable.setWidth("100%");
		contentTable.setHeight("100%");

		// State is set to widget in ServerStateButtonConnector
		final FlexCellFormatter cellFormatter = contentTable.getFlexCellFormatter();

		//adjustments
		cellFormatter.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);

		contentTable.getColumnFormatter().setWidth(1, "100%");

		cellFormatter.setHeight(0, 1, "100%");
		cellFormatter.setHeight(0, 0, "100%");

		cellFormatter.setWidth(0, 1, "100%");
		cellFormatter.setWidth(1, 1, "100%");
		cellFormatter.setWidth(2, 1, "100%");

		// user online
		final Label usersText = new Label("Users");
		usersText.addStyleName("server-state-caption");
		usersLabel.addStyleName("server-state-users");
		contentTable.setWidget(0, 0, usersText);
		contentTable.setWidget(0, 1, usersLabel);

		final Label cpuLoadText = new Label("CPU");
		cpuLoadText.addStyleName("server-state-caption");
		contentTable.setWidget(1, 0, cpuLoadText);
		contentTable.setWidget(1, 1, cpuLoadLabel);
		cellFormatter.addStyleName(1, 1, "bar-border");

		final Label ramUsageText = new Label("RAM");
		ramUsageText.addStyleName("server-state-caption");
		contentTable.setWidget(2, 0, ramUsageText);
		contentTable.setWidget(2, 1, ramUsageLabel);
		cellFormatter.addStyleName(2, 1, "bar-border");

		return contentTable;
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

	private void setBarValue(final Label label, final double value) {
		final double val = value > 1 ? 1 : value;
		final double percVal = val * 100;
		final Style style = label.getElement().getStyle();

		style.setBackgroundColor(createColor(val));

		if(isVertical)
		{
			style.setHeight(percVal, Style.Unit.PCT);
			style.setWidth (100, Style.Unit.PCT);
		}
		else
		{
			style.setHeight(100, Style.Unit.PCT);
			style.setWidth (percVal, Style.Unit.PCT);
		}
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