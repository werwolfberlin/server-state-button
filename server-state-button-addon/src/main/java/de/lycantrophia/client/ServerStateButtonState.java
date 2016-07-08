package de.lycantrophia.client;

public class ServerStateButtonState extends com.vaadin.shared.AbstractComponentState {

	// State can have both public variable and bean properties
	public String text = "ServerStateButton";
	private String serverName;
	private String userCount;
	private double cpuLoad;
	private int ramUsage;
	private int maxRam;

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public String getUserCount() { return userCount; }
	public double getCpuLoad() { return cpuLoad; }
	public int getRamUsage() { return ramUsage; }
	public int getMaxRam() { return maxRam; }

	public void setUserCount(String userCount) { this.userCount = userCount; }
	public void setCpuLoad(double cpuLoad) { this.cpuLoad = cpuLoad; }
	public void setRamUsage(int ramUsage) { this.ramUsage = ramUsage; }
	public void setMaxRam(int maxRam) { this.maxRam = maxRam; }
}