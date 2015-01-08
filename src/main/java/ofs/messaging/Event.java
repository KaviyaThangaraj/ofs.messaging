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
	 * This class encapulates the Events that a producer can produce and a consumer can subscribe to
	 */
	private String eventId;

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	private String eventName;

	private EventPriority eventPriority;

	private EventCategory eventCategory;

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

	/**
	 * @return the eventPriority
	 */
	public EventPriority getEventPriority() {
		return eventPriority;
	}

	/**
	 * @param eventPriority the eventPriority to set
	 */
	public void setEventPriority(EventPriority eventPriority) {
		this.eventPriority = eventPriority;
	}

	/**
	 * @return the eventCategory
	 */
	public EventCategory getEventCategory() {
		return eventCategory;
	}

	/**
	 * @param eventCategory the eventCategory to set
	 */
	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

}
