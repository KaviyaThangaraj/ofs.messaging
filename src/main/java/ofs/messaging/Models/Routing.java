/**
 * 
 */
package ofs.messaging.Models;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.Util;
import ofs.messaging.Persistence.PersistenceManager;

/**
 * @author Ramanan Natarajan
 *
 */
public class Routing {

  private String routingKeyId;
  private String routingKey;

  /**
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws ConfigurationException
   * 
   */
  public Routing(String Businessunit, String EventId) throws ConfigurationException,
      InterruptedException, ExecutionException {

    // XXX: is this key id really required?
    this.routingKeyId = Util.getUUID().toString();
    this.routingKey = generateRoutingKey(Businessunit, EventId);

  }

  /**
   * @return the routingKeyId
   */
  public String getRoutingKeyId() {
    return routingKeyId;
  }

  /**
   * @param routingKeyId the routingKeyId to set
   */
  public void setRoutingKeyId(String routingKeyId) {
    this.routingKeyId = routingKeyId;
  }

  private String generateRoutingKey(String businessunit, String eventId)
      throws ConfigurationException, InterruptedException, ExecutionException {

    return businessunit.replace(" ", "-") + "." + eventId;
  }

  public String getRoutingKey() {

    if (this.routingKey != null) {
      return this.routingKey;
    }
    return null;

  }

  public static String getRoutingKey(String clientId) throws ConfigurationException,
      InterruptedException, ExecutionException {
    ClientRegistration cr = PersistenceManager.getPublishingClientFromClientId(clientId);
    return new Routing(cr.getBusinessUnit(), cr.getEventId()).getRoutingKey();
  }
}
