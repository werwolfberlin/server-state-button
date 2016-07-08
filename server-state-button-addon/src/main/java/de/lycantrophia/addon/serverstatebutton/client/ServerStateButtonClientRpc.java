package de.lycantrophia.addon.serverstatebutton.client;

import com.vaadin.shared.communication.ClientRpc;

// ClientRpc is used to pass events from server to client
// For sending information about the changes to component state, use State instead
public interface ServerStateButtonClientRpc extends ClientRpc {
	void setServerName(String serverName);
	void setMaxRam(int maxRam);
	void setVertical(boolean isVertivalLayout);
	void updateServerInfo(String users, double cpuLoad, int memoryUsage);
}