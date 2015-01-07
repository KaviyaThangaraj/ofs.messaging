package ofs.messaging.Client;

/**
 * @author Ramanan Natarajan
 *
 *         Defines the Connection method which the implementors can implement as per their provider
 *         Currently returns an object of type RabbitMQConnection
 */
public interface Connection {
	public com.rabbitmq.client.Connection connect();

}
