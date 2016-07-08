package de.lycantrophia;

import com.vaadin.ui.Button;
import de.lycantrophia.client.ServerStateButtonClientRpc;
import de.lycantrophia.client.ServerStateButtonServerRpc;
import de.lycantrophia.client.ServerStateButtonState;

import com.vaadin.shared.MouseEventDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

// This is the server-side UI component that provides public API 
// for ServerStateButton
public class ServerStateButton extends com.vaadin.ui.AbstractComponent {

	private final Collection<Button.ClickListener> listeners = Collections.synchronizedSet(new LinkedHashSet<>());

	public void addClickListener(Button.ClickListener listener)
	{
		listeners.add(listener);
	}

	public void removeClickListener(Button.ClickListener listener)
	{
		listeners.remove(listener);
	}

	private void fireClickEvent(MouseEventDetails details)
	{
		for (Button.ClickListener listener : listeners) {
			listener.buttonClick(new Button.ClickEvent(this, details));
		}
	}

	// To process events from the client, we implement ServerRpc
	private ServerStateButtonServerRpc rpc = new ServerStateButtonServerRpc() {

		// Event received from client - user clicked our widget
		public void clicked(MouseEventDetails mouseDetails) {
			fireClickEvent(mouseDetails);
//			System.out.println("clicked");
			// Send nag message every 5:th click with ClientRpc
//			if (++clickCount % 5 == 0) {
//				getRpcProxy(ServerStateButtonClientRpc.class)
//						.alert("Ok, that's enough!");
//			}
//
//			// Update shared state. This state update is automatically
//			// sent to the client.
//			getState().text = "You have clicked " + clickCount + " times";
		}
	};

	public ServerStateButton() {

		// To receive events from the client, we register ServerRpc
		registerRpc(rpc);
	}

	// We must override getState() to cast the state to ServerStateButtonState
	@Override
	protected ServerStateButtonState getState() {
		return (ServerStateButtonState) super.getState();
	}

	public void setServerName(String serverName) {
		getRpcProxy(ServerStateButtonClientRpc.class).setServerName(serverName);
	}

	public void updateServerInfo(final String users, final double cpuLoad, final int memoryUsage)
	{
		final ServerStateButtonState state = getState();
		state.setUserCount(users);
		state.setCpuLoad(cpuLoad);
		state.setRamUsage(memoryUsage);
	}

	public void setMaxRam(int maxRam) {
		getRpcProxy(ServerStateButtonClientRpc.class).setMaxRam(maxRam);
	}
}
