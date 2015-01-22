package ofs.messaging.Models;

import java.util.UUID;

import ofs.messaging.Util;

import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;



public abstract class Registration {


  protected String clientName;
  protected String businessUnit;
  protected String eventId;
  protected String clientRegistrationId;
  protected String clientDescription;

  public Registration() {
    super();
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
  public String getClientRegistrationId() {
    return clientRegistrationId;
  }

  /**
   * @param clientRegistrationId the clientRegistrationId to set
   */
  public void setClientRegistrationId(String clientRegistrationId) {
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

  protected String generateRegistrationId() {

    return Util.getUUID().toString();

  }

}
