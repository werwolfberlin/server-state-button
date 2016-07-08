package de.lycantrophia.addon.serverstatebutton;

import com.vaadin.ui.Button;
import de.lycantrophia.addon.serverstatebutton.client.ServerStateButtonClientRpc;
import de.lycantrophia.addon.serverstatebutton.client.ServerStateButtonServerRpc;
import de.lycantrophia.addon.serverstatebutton.client.ServerStateButtonState;

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

	public ServerStateButton() {

		// To receive events from the client, we register ServerRpc
		registerRpc((ServerStateButtonServerRpc) this::fireClickEvent);
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
