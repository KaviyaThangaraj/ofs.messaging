/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.Util;

/**
 * @author Ramanan Natarajan
 *
 */
public class RoutingKey {

  private UUID routingKeyId;
  private String routingKey;

  /**
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws ConfigurationException
   * 
   */
  public RoutingKey(String Businessunit, String EventId) throws ConfigurationException,
      InterruptedException, ExecutionException {

    this.routingKeyId = Util.getUUID();
    this.routingKey = generateRoutingKey(Businessunit, EventId);
    // new DataStore().addRoutingKeys(this.routingKeyId.toString(), this.routingKey);
  }

  /**
   * @return the routingKeyId
   */
  public UUID getRoutingKeyId() {
    return routingKeyId;
  }

  /**
   * @param routingKeyId the routingKeyId to set
   */
  public void setRoutingKeyId(UUID routingKeyId) {
    this.routingKeyId = routingKeyId;
  }

  private String generateRoutingKey(String businessunit, String eventId)
      throws ConfigurationException, InterruptedException, ExecutionException {

    return businessunit + "." + eventId; // + new DataStore().getEventName(eventId);
  }

  public String getRoutingKey() {

    if (this.routingKey != null) {
      return this.routingKey;
    }
    return null;

  }
}
