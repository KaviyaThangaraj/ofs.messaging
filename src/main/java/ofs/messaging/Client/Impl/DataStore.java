/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ofs.messaging.Models.SubscriptionList;

import org.apache.commons.configuration.ConfigurationException;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;

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

	private static CouchbaseClient client = null;

	public DataStore() throws ConfigurationException, InterruptedException, ExecutionException {

		try {
			if (client == null) {
				client = ConfigurationStoreManager.getInstance();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRegistration(String clientId, String eventId) throws InterruptedException,
			ExecutionException {
		DataStore.EventRegistration.put(clientId, eventId);

		new SubscriptionList().addEventRegistration(clientId, eventId);
		new SubscriptionList().addEventRegistration(clientId, eventId);
		client.set("EventRegistration",
				new Gson().toJson(SubscriptionList.getEventRegistrationList())).get();

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
