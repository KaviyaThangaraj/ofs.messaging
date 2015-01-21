/**
 * 
 */
package ofs.messaging.Models;

import java.util.UUID;

import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

import ofs.messaging.Util;
import ofs.messaging.test;
import ofs.messaging.Client.Exceptions.EventIdDoesNotExistException;
import ofs.messaging.Persistence.PersistenceManager;

/**
 * @author ramanann
 *
 */
public class ClientRegistration {
  public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(ClientRegistration.class);

  private String clientName;
  private String businessUnit;
  private String eventId;
  private UUID clientRegistrationId;
  private String clientDescription;
  private String exchangeId;

  // /FIXME: have an enum for business unit and if possible, enforce this through a proeprty file
  public ClientRegistration(String clientName, String description, String businessUnit,
      String eventId) {

    // FIXME: validate if the eventId provided is avbl already and if not, please stop this
    // registration
    log.debug("Eventid" + eventId);
    if (!PersistenceManager.isEventExists(eventId)) {
      throw new EventIdDoesNotExistException(
          "The Event Id provided does not exist. Please query to obtain the existing list "
              + "and use it or contact the admin for inclusion of new Events");
    }
    this.clientName = clientName;
    this.clientDescription = description;
    this.businessUnit = businessUnit;
    this.eventId = eventId;
    this.clientRegistrationId = generateClientRegistrationId();
    // /FIXME: currently using the same id as event. if Exchange requires a different id, create it
    // later
    this.exchangeId = this.eventId;

    // Persist this
    PersistenceManager.saveClientRegistration(this);

  }

  public ClientRegistration() {

  }

  /**
   * @return the exchangeId
   */
  public String getExchangeId() {
    return exchangeId;
  }

  /**
   * @param exchangeId the exchangeId to set
   */
  public void setExchangeId(String exchangeId) {
    this.exchangeId = exchangeId;
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
   * @return the clientRegistrationId
   */
  public UUID getClientRegistrationId() {
    return clientRegistrationId;
  }

  /**
   * @param clientRegistrationId the clientRegistrationId to set
   */
  public void setClientRegistrationId(UUID clientRegistrationId) {
    this.clientRegistrationId = clientRegistrationId;
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



  private UUID generateClientRegistrationId() {

    return Util.getUUID();

  }

}
