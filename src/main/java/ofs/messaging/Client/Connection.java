package ofs.messaging.Client;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.naming.Context;

/**
 * @author Ramanan Natarajan
 *
 *         Defines the Connection method which the implementors can implement as per their provider
 *         Currently returns an object of type RabbitMQConnection
 */
public interface Connection {
	public com.rabbitmq.client.Connection connect() throws KeyManagementException,
			NoSuchAlgorithmException, URISyntaxException;

}
