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
import org.slf4j.LoggerFactory;

import ofs.messaging.Util;
import ofs.messaging.Client.Exceptions.ClientAlreadyRegisteredException;
import ofs.messaging.Client.Exceptions.EventIdDoesNotExistException;
import ofs.messaging.Client.Helper.BrokerHelper;
import ofs.messaging.Persistence.PersistenceManager;
import ch.qos.logback.classic.Logger;



/**
 * @author ramanann
 *
 */
public class ClientRegistration {

  public static final org.slf4j.Logger log = LoggerFactory.getLogger(ClientRegistration.class);
  private String exchangeId;
  private String clientName;
  private String clientDescription;
  private String businessUnit;
  private String eventId;
  private String clientRegistrationId;
  private Routing routingKey;

  // /FIXME: have an enum for business unit and if possible, enforce this through a proeprty file
  public ClientRegistration(String clientName, String description, String businessUnit,
      String eventId) throws ConfigurationException, InterruptedException, ExecutionException,
      KeyManagementException, NoSuchAlgorithmException, IOException, NamingException,
      URISyntaxException {

    // FIXME: validate if the eventId provided is avbl already and if not, please stop this
    // registration
    log.debug("Eventid" + eventId);
    if (!PersistenceManager.isEventExists(eventId)) {
      throw new EventIdDoesNotExistException(
          "The Event Id provided does not exist. Please query to obtain the existing list "
              + "and use it or contact the admin for inclusion of new Events");
    }
    // validate if a record witht the above detail already exists? if yes, throw back and exception
    // citing an already existing client. we shouldnt be sending that id as someone can play around
    // and get that id
    if (PersistenceManager.isAlreadyRegistered(clientName, businessUnit, eventId)) {

      throw new ClientAlreadyRegisteredException(
          "There is an already existing registered client with the same Client Name, Business Unit and for the same event id");

    }
    this.clientName = clientName;
    this.clientDescription = description;
    this.businessUnit = businessUnit;
    this.eventId = eventId;
    this.clientRegistrationId = generateRegistrationId();
    // /FIXME: currently using the same id as event. if Exchange requires a different id, create it
    // later
    this.exchangeId = this.eventId;

    // Persist this
    PersistenceManager.saveClientRegistration(this);
    // FIXME: check if creating the routing id here is ok!
    this.routingKey = setupRoutingKey(this);
    //BrokerHelper.createExchange(eventId);


  }

  private Routing setupRoutingKey(ClientRegistration clientRegistration)
      throws ConfigurationException, InterruptedException, ExecutionException {


    return new Routing(clientRegistration.businessUnit, clientRegistration.eventId);
  }

  private String generateRegistrationId() {

    return Util.getUUID().toString();
  }

  // a default constructor for hibernate
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

  public static ClientRegistration getClient(String clientId) {

    return PersistenceManager.getPublishingClientFromClientId(clientId);
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
   * @return the routingKey
   */
  public Routing getRoutingKey() {
    return routingKey;
  }

  /**
   * @param routingKey the routingKey to set
   */
  public void setRoutingKey(Routing routingKey) {
    this.routingKey = routingKey;
  }
}
