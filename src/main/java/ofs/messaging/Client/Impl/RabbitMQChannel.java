/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import ofs.messaging.Client.ExchangeType;
import ofs.messaging.Client.Exceptions.ChannelException;
import ofs.messaging.Client.Exceptions.ConnectionFailedException;
import ofs.messaging.Client.Exceptions.ExchangeCreationException;
import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author Ramanan Natarajan
 *
 */
public class RabbitMQChannel implements ofs.messaging.Client.Channel {

	private com.rabbitmq.client.Connection connection = null;
	private Channel channel = null;

	/**
	 * @return the connection
	 */
	public com.rabbitmq.client.Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(com.rabbitmq.client.Connection connection) {
		this.connection = connection;
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
	 * @param connection
	 */
	public RabbitMQChannel(com.rabbitmq.client.Connection connection) {
		super();
		this.connection = connection;

	}

	public Channel createChannel() {

		try {
			this.channel = this.connection.createChannel();
			return this.channel;
		} catch (IOException e) {

			throw new ConnectionFailedException(e);
		}

	}

	public void exchangeDeclare(String exchange, ExchangeType type, boolean durable,
			boolean autoDelete, boolean internal, Map<String, Object> arguments) {
		try {
			// TODO: howto find out if the exchange already exists? and throw an
			// error?
			// if there is an api,this needs to be modified to suit it

			// FIXME: cant i create a channel if not there and return this back? just a thought?
			if (this.channel == null) {
				throw new IllegalArgumentException("Channel has not been created yet or it is null");
			}
			// this.channel.exchangeDeclare(exchange, "topic", durable, autoDelete, arguments);
			this.channel.exchangeDeclare(exchange, type.toString(), durable, autoDelete, arguments);

		} catch (IOException e) {

			throw new ExchangeCreationException("Exchange Creation Failed", e);
		} catch (Exception e) {

			throw new ExchangeCreationException(e);
		}

	}

	public void exchangeDeclare(String exchange, ExchangeType type) {

		exchangeDeclare(exchange, type, true, false, false, null);
	}

	public void exchangeDeclare(String exchange) {

		exchangeDeclare(exchange, ExchangeType.topic, true, false, false, null);
	}

	public void exchangeDeclare(String exchange, ExchangeType type, boolean durable,
			boolean autoDelete) {

		// TODO: understand what false means in internal exchange
		exchangeDeclare(exchange, type, durable, autoDelete, false, null);
	}

	public void basicPublish(String exchange, String routingKey, byte[] body) {

		basicPublish(exchange, routingKey, false, false, body);

	}

	public void basicPublish(String exchange, String routingKey, boolean mandatory,
			boolean immediate, byte[] body) {

		basicPublish(exchange, routingKey, false, false, null, body);

	}

	/*
	 * @see ofs.messaging.Client.Channel#basicPublish(java.lang.String, java.lang.String, boolean,
	 * boolean, com.rabbitmq.client.AMQP.BasicProperties, byte[])
	 */
	public void basicPublish(String exchange, String routingKey, boolean mandatory,
			boolean immediate, BasicProperties props, byte[] body) {

		try {
			this.channel.basicPublish(exchange, routingKey, mandatory, immediate, props, body);
			body = null;
		} catch (IOException e) {

			throw new MessagePublishingFailedException("Publishing this Message Failed", e);
		}

	}

	public void close() {
		try {
			this.channel.close();
		} catch (IOException e) {

			throw new ChannelException("Channel closing failed", e);
		}

	}

	public String basicConsume(String queue, DefaultConsumer callback) throws IOException {
		System.out.println("inside channel consume");
		String envelope = this.channel.basicConsume(queue, callback);
		System.out.println(envelope.toString());
		return envelope;
	}

	public String basicConsume(String queue, boolean autoAck, DefaultConsumer callback)
			throws IOException {
		return null;
	}

	public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments,
			DefaultConsumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String basicConsume(String queue, boolean autoAck, String consumerTag,
			DefaultConsumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal,
			boolean exclusive, Map<String, Object> arguments, Consumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void basicCancel(String consumerTag) throws IOException {
		// TODO Auto-generated method stub

	}

	public String basicConsume(String queue, Consumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments,
			Consumer callback) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String
			basicConsume(String queue, boolean autoAck, String consumerTag, Consumer callback)
					throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
