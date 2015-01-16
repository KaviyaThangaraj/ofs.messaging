package ofs.messaging.Client.Impl;

import java.io.IOException;

import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

import ofs.messaging.Message;
import ofs.messaging.Util;
import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;

public class MessagePublisher implements Runnable {

	public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(MessagePublisher.class);
	private Channel channel = null;
	private String exchangeId;
	private RoutingKey routingKey;
	private Message Message;

	public MessagePublisher(Channel channel, String exchangeId, RoutingKey routingKey,
			Message message) {
		this.channel = channel;
		this.exchangeId = exchangeId;
		this.routingKey = routingKey;
		this.Message = message;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
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
	 * @param exchangeId
	 *            the exchangeId to set
	 */
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	/**
	 * @return the routingKey
	 */
	public RoutingKey getRoutingKey() {
		return routingKey;
	}

	/**
	 * @param routingKey
	 *            the routingKey to set
	 */
	public void setRoutingKey(RoutingKey routingKey) {
		this.routingKey = routingKey;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return Message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(Message message) {
		Message = message;
	}

	public void run() {
		try {

			byte[] bytes = Util.toByteArray(this.Message);
			channel.basicPublish(exchangeId, this.routingKey.getRoutingKey().toUpperCase(), bytes);
			// log.trace(Marker, msg);("Messag {}","a");

		} catch (IOException e) {

			new MessagePublishingFailedException("publishing this message with MessageId="
					+ this.Message.getMessageId(), e);
		}

	}
}
