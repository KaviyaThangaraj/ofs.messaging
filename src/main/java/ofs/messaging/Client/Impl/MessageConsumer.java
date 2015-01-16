package ofs.messaging.Client.Impl;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import ofs.messaging.Client.*;
import ofs.messaging.Client.Exceptions.MessageDeliveryFailedException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

/**
 * 
 */

/**
 * @author ramanann
 *
 */
public class MessageConsumer implements Runnable {

	public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(MessageConsumer.class);
	private RabbitMQChannel channel = null;
	private DefaultConsumer callback;
	private String queueName;

	/**
	 * 
	 */
	public MessageConsumer(Channel channel) {

		this.channel = ((RabbitMQChannel) channel);

	}

	/**
	 * @return the channel
	 */
	public RabbitMQChannel getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(RabbitMQChannel channel) {
		this.channel = channel;
	}

	public MessageConsumer(Channel channel, MessageHandler handler, String queueName) {

		this.channel = ((RabbitMQChannel) channel);
		this.queueName = queueName;
		this.callback = handler;

	}

	/**
	 * @return the callback
	 */
	public DefaultConsumer getCallback() {
		return callback;
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(DefaultConsumer callback) {
		this.callback = callback;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName
	 *            the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void run() {

		try {

			channel.basicConsume(queueName, callback);

		} catch (IOException e) {

			log.error("Message consumption failed inside consumer.java ", e);
			throw new MessageDeliveryFailedException("Message consumption failed", e);
		}
	}

}
