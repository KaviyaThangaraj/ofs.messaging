/**
 * 
 */
package ofs.messaging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramanan Natarajan
 *
 */
public class DataStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 725234008468123739L;
	/**
	 * 
	 */

	private static HashMap<String, String> EventRegistration = new HashMap<String, String>();
	private static HashMap<String, String> ClientRegistration = new HashMap<String, String>();
	private static HashMap<String, String> EventTable = new HashMap<String, String>();
	private static HashMap<String, String> RoutingKeyTable = new HashMap<String, String>();

	public DataStore() {

	}

	public void addRegistration(String clientId, String eventId) {
		DataStore.EventRegistration.put(clientId, eventId);
	}

	public Map<String, String> getRegistrationData() {

		return DataStore.ClientRegistration;

	}

	public void addClient(String clientId, String clientName) {

		DataStore.ClientRegistration.put(clientId, clientName);

	}

	public String getClientName(String clientId) {
		return ClientRegistration.get(clientId);
	}

	public void addEvents(String eventId, String eventName) {
		DataStore.EventTable.put(eventId, eventName);

	}

	public String getEventName(String eventId) {

		return EventTable.get(eventId).replace(" ", ".");
	}

	public void addRoutingKeys(String routingKeyId, String routingKey) {
		DataStore.RoutingKeyTable.put(routingKeyId, routingKey);

	}

	public Map<String, String> getAvailableRoutingKeys() {

		return DataStore.RoutingKeyTable;
	}

}
