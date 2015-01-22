/**
 * 
 */
package ofs.messaging.Models;

import java.util.ArrayList;

import ofs.messaging.Util;
import ofs.messaging.Client.Exceptions.ClientAlreadySubscribedToThisEventException;
import ofs.messaging.Client.Exceptions.EventIdDoesNotExistException;
import ofs.messaging.Persistence.PersistenceManager;

import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

/**
 * @author ramanann
 *
 */
public class SubscriptionRegistration {

  public static final OFSPlatformLogger log = OFSPlatformLogger
      .getLogger(SubscriptionRegistration.class);
  private String queue;
  private String clientSubscriptionId;
  private String clientName;
  private String businessUnit;
  private String eventId;

  private String clientDescription;

  /**
	 * 
	 */
  public SubscriptionRegistration(String clientName, String description, String businessUnit,
      String eventId) {
    super();
    // if the same combination of client, business and event exists, then it means that the client
    // has already registered for this event and we need to throw an exception citing that its
    // already subscribed. we dont need to give back the existing queue as this would again, be a
    // means for some unknown client to read someone's data

    log.debug("Eventid" + eventId);
    if (PersistenceManager.isAlreadySubscribed(clientName, businessUnit, eventId)) {

      throw new ClientAlreadySubscribedToThisEventException(
          "The client with this name, business unit is already subscribed "
              + "to this event id. please use the key already provided to get your messages");
    }
    this.businessUnit = businessUnit;
    this.eventId = eventId;
    this.clientName = clientName;
    this.clientSubscriptionId = generateRegistrationId();
    this.queue = generateQueueId();
    createAndBindQueue(this.queue);

  }



  // /FIXME: this is the part where teh created queue is bound to the exchange
  private void createAndBindQueue(String queue) {


  }

  private String generateQueueId() {
    return Util.getUUID().toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return "Client Name is " + clientName + ", Client Description is " + clientDescription
        + ", Business Unit is" + businessUnit + ", Event Id is " + eventId;
  }



  /**
   * @return the queue
   */
  public Object getQueue() {
    return queue;
  }



  /**
   * @param queue the queue to set
   */
  public void setQueue(String queue) {
    this.queue = queue;
  }



  /**
   * @return the clientSubscriptionId
   */
  public String getClientSubscriptionId() {
    return clientSubscriptionId;
  }



  /**
   * @param clientSubscriptionId the clientSubscriptionId to set
   */
  public void setClientSubscriptionId(String clientSubscriptionId) {
    this.clientSubscriptionId = clientSubscriptionId;
  }



  /**
   * @return the clientName
   */
  public String getClientName() {
    return clientName;
  }



  /**
   * @param clientName the clientName to set
   */
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }



  /**
   * @return the businessUnit
   */
  public String getBusinessUnit() {
    return businessUnit;
  }



  /**
   * @param businessUnit the businessUnit to set
   */
  public void setBusinessUnit(String businessUnit) {
    this.businessUnit = businessUnit;
  }



  /**
   * @return the eventId
   */
  public String getEventId() {
    return eventId;
  }



  /**
   * @param eventId the eventId to set
   */
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }



  /**
   * @return the clientDescription
   */
  public String getClientDescription() {
    return clientDescription;
  }



  /**
   * @param clientDescription the clientDescription to set
   */
  public void setClientDescription(String clientDescription) {
    this.clientDescription = clientDescription;
  }

  private String generateRegistrationId() {

    return Util.getUUID().toString();

  }

}
