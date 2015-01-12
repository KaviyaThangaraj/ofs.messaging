package ofs.messaging.Client.Impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.naming.Context;

import ofs.messaging.Client.MessagingContext;
import ofs.messaging.Client.MessagingContextFactory;
import ofs.messaging.Client.Exceptions.ConnectionFailedException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

public class RabbitMQConnection implements ofs.messaging.Client.Connection {

	private int port;
	private String host = "localhost";
	private String URI; // for future HTTP work
	private Connection connection = null;
	private Properties properties;

	public RabbitMQConnection() {

	}

	public RabbitMQConnection(String URI) {
		this.setURI(URI);

	}

	// FIXME: User a connection Factory, context factory and inject
	public RabbitMQConnection(String Host, int Port) {

		if ((Host.trim().length() > 0 && Port > 0)) {
			this.host = Host;
			this.port = Port;
		} else {
			throw new IllegalArgumentException(
					"Please verify the values of Host or Port. One of them is invalid as its length  is less than 0");
		}
	}

	@Deprecated
	public RabbitMQConnection(Context context) throws KeyManagementException,
			NoSuchAlgorithmException, URISyntaxException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(context.PROVIDER_URL);

	}

	public Connection connect() throws KeyManagementException, NoSuchAlgorithmException,
			URISyntaxException {

		ConnectionFactory factory = new ConnectionFactory();

		if (this.URI.isEmpty()) {
			factory.setHost(host);
			factory.setPort(port);
		} else {
			factory.setUri(URI);
		}

		try {
			connection = factory.newConnection();
			return connection;

		} catch (java.net.ConnectException e) {

			throw new ConnectionFailedException(host + ":" + port
					+ " is Not available. Check if the Server is available." + "\n ", e);

		} catch (IOException e) {

			throw new ConnectionFailedException(e);
		} catch (Exception e) {

			throw new ConnectionFailedException(e);

		}

	}

	public void close() {
		try {
			this.connection.close();
		} catch (IOException e) {
			throw new ConnectionFailedException("Closing the connection failed", e);

		}
	}

	/**
	 * @return the uRI
	 */
	public String getURI() {
		return URI;
	}

	/**
	 * @param uRI
	 *            the uRI to set
	 */
	public void setURI(String uRI) {
		URI = uRI;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;

	}

}
