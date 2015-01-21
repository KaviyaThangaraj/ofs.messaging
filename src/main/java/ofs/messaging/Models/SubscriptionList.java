/**
 * 
 */
package ofs.messaging.Models;

import java.util.ArrayList;

/**
 * @author ramanann
 *
 */
public class SubscriptionList {

	private static ArrayList<EventRegistration> eventRegistrationList = new ArrayList();

	/**
	 * @return the eventRegistrationList
	 */
	public static ArrayList<EventRegistration> getEventRegistrationList() {
		return eventRegistrationList;
	}

	/**
	 * 
	 */
	public SubscriptionList() {

	}

	public void addEventRegistration(String clientId, String eventId) {
		eventRegistrationList.add(new EventRegistration(clientId, eventId));
	}

	public class EventRegistration {

		private String clientId;
		private String eventId;

		public EventRegistration(String clientId, String eventId) {
			this.setClientId(clientId);
			this.setEventId(eventId);
		}

		/**
		 * @return the clientId
		 */
		public String getClientId() {
			return clientId;
		}

		/**
		 * @param clientId
		 *            the clientId to set
		 */
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		/**
		 * @return the eventId
		 */
		public String getEventId() {
			return eventId;
		}

		/**
		 * @param eventId
		 *            the eventId to set
		 */
		public void setEventId(String eventId) {
			this.eventId = eventId;
		}

	}
}
