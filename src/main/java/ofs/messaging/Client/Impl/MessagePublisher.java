package ofs.messaging.Client.Impl;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.ConfigurationException;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ofs.messaging.Document;
import ofs.messaging.DocumentType;
import ofs.messaging.Message;
import ofs.messaging.Util;
import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;
import ofs.messaging.Models.Routing;

public class MessagePublisher implements Runnable,Callable<String> {

  public static final Logger log = LoggerFactory.getLogger(MessagePublisher.class);
  private Channel channel = null;
  private String exchangeId;
  private String routingKey;
  private Message Message;

  public MessagePublisher(Channel channel, String exchangeId, String routingKey, Message message) {
    this.channel = channel;
    this.exchangeId = exchangeId;
    this.routingKey = routingKey;
    this.Message = message;
    log.debug("Publisher with this message created {}",this.Message.getMessageId());
  }

  /**
   * @return the channel
   */
  public Channel getChannel() {
    return channel;
  }

  /**
   * @param channel the channel to set
   */
  public void setChannel(Channel channel) {
    this.channel = channel;
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
   * @return the routingKey
   */
  public String getRoutingKey() {
    return routingKey;
  }

  /**
   * @param routingKey the routingKey to set
   */
  public void setRoutingKey(String routingKey) {
    this.routingKey = routingKey;
  }

  /**
   * @return the message
   */
  public Message getMessage() {
    return Message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(Message message) {
    Message = message;
  }

  public void run() {
    try {

    	log.debug("before byarray conversion");
      byte[] bytes = Util.toByteArray(this.Message);
      channel.basicPublish(exchangeId, this.routingKey, bytes);
      
      log.debug("Thread {}, message {}", Thread.currentThread().getName(), this.Message.getMessageId());
      
//      if (this.Message.isRedundant()) {
//        try {
//          storeMessage(this);
//        } catch (InterruptedException e) {
//          log.error("Storing failed ", e);
//          e.printStackTrace();
//        } catch (ExecutionException e) {
//
//          log.error("Storing failed ", e);
//        } catch (ConfigurationException e) {
//          log.error("Storing failed ", e);
//        }
//      }
    } catch (IOException e) {

      throw new MessagePublishingFailedException("publishing this message with MessageId="
          + this.Message.getMessageId(), e);
    }

  }

  public void storeMessage(MessagePublisher messagePublisher) throws InterruptedException,
      ExecutionException, ConfigurationException {

    Gson gson = new Gson();
    Document doc =
        new Document(messagePublisher.getMessage().getMessageId(), DocumentType.MESSAGE,
            messagePublisher.routingKey, messagePublisher.getMessage());
    DatastoreManager.getInstance().set(doc.getId(), gson.toJson(doc)).get();

    log.debug("Storing message " + messagePublisher.getMessage().getMessageId());
  }

@Override
public String call() throws Exception {
	// TODO Auto-generated method stub
	return null;
}

}
