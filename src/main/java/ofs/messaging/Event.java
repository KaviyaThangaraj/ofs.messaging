/**
 * 
 */
package ofs.messaging;

/**
 * @author Ramanan Natarajan
 *
 */
public class Event {

	/**
	 * This class encapulates the Events that a producer can produce
	 */
	private String eventId;

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	private String eventName;

	/**
	 * A single argument constructor that takes in the client supplied Event Name
	 */
	public Event(String eventName) {
		this.eventName = eventName;
		this.eventId = generateEventId();
		new DataStore().addEvents(this.eventId, this.eventName);
	}

	@SuppressWarnings("unused")
	private String getEvent(String eventName) {
		return this.eventId.toString();
	}

	private String generateEventId() {

		return Constants.PREFIX + "." + (this.eventName).replace(" ", ".").toUpperCase();
	}

}
