package de.lycantrophia.addon.serverstatebutton.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface ServerStateButtonServerRpc extends ServerRpc {

	// Example API: Widget click is clicked
	void clicked(MouseEventDetails mouseDetails);

}
