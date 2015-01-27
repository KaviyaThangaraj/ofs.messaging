/**
 * 
 */
package ofs.messaging.Client.Impl;

import ofs.messaging.Client.MessageHandler;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ramanann
 *
 */
public class HandlerResponse {

  public static final Logger log = LoggerFactory.getLogger(HandlerResponse.class);

  private HandlerResponse(ResponseCode responseCode, Envelope envelope,
      AMQP.BasicProperties properties, byte[] body) {
    this.responseCode = responseCode;
    this.envelope = envelope;
    this.properties = properties;
    this.body = body;
  }

  public static HandlerResponse ok(Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
    return new HandlerResponse(ResponseCode.OK, envelope, properties, body);
  }

  public static HandlerResponse noOp() {
    return new HandlerResponse(ResponseCode.NOOP, null, null, null);
  }

  public static enum ResponseCode {
    NOOP, OK
  }

  private ResponseCode responseCode;
  private final Envelope envelope;
  private final AMQP.BasicProperties properties;
  private final byte[] body;

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  public boolean isSuccess() {
    return responseCode == ResponseCode.OK;
  }

  public boolean isNoOp() {
    return responseCode == ResponseCode.NOOP;
  }

  public Envelope getEnvelope() {
    return envelope;
  }

  public AMQP.BasicProperties getProperties() {
    return properties;
  }
}
