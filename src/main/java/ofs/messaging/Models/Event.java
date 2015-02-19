/**
 * 
 */
package ofs.messaging.Models;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.EventCategory;
import ofs.messaging.EventPriority;
import ofs.messaging.Util;
import ofs.messaging.Client.Helper.BrokerHelper;
import ofs.messaging.Client.Impl.DataStore;
import ofs.messaging.Persistence.PersistenceManager;

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

  public Event() {

  }

  /**
   * A single argument constructor that takes in the client supplied Event Name
   * 
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws ConfigurationException
 * @throws URISyntaxException 
 * @throws NamingException 
 * @throws IOException 
 * @throws NoSuchAlgorithmException 
 * @throws KeyManagementException 
   */
  public Event(String eventName) throws ConfigurationException, InterruptedException,
      ExecutionException, KeyManagementException, NoSuchAlgorithmException, IOException, NamingException, URISyntaxException {
    this.eventName = eventName.replace(" ", "");
    
    this.eventId = generateEventId();
    if(!PersistenceManager.isEventNameExists(this.eventName)){
    PersistenceManager.saveEvent(this);
    BrokerHelper.createExchange(this.getEventId());
    }
  }

  @SuppressWarnings("unused")
  private String getEvent(String eventName) {
    return this.eventId.toString();
  }

  private String generateEventId() {

    return Util.getUUID().toString();

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
  
  public String getEventName(){
	  return eventName;
  }

}
