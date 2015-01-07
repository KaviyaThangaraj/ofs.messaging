/**
 * 
 */
package ofs.messaging.Client;

import java.util.Map;

/**
 * @author Ramanan Natarajan
 *
 */

public interface Channel {

	public com.rabbitmq.client.Channel createChannel();

	/**
	 * @param exchange
	 * @param type
	 *            Exchange is the name of the exchange and type defines one of topic or queues as
	 *            defined by the enum ExchangeType
	 */

	public void exchangeDeclare(String exchange, ExchangeType type);

	/**
	 * @param exchange
	 * 
	 *            Exchange is the name of the exchange and type defaults to topic
	 */
	// TODO: to appropriately javadoc this exchange type
	public void exchangeDeclare(String exchange);

	/**
	 * @param exchange
	 * @param type
	 * @param durable
	 * @param autoDelete
	 */
	public void exchangeDeclare(String exchange, ExchangeType type, boolean durable,
			boolean autoDelete);

	/**
	 * @param exchange
	 * @param type
	 * @param durable
	 * @param autoDelete
	 * @param internal
	 * @param arguments
	 */
	public void exchangeDeclare(String exchange, ExchangeType type, boolean durable,
			boolean autoDelete, boolean internal, Map<String, Object> arguments);

	// TODO: understand what this mandatory flag means?. rabbit mq doesnt support immediate
	// set default to false;
	public void basicPublish(String exchange, String routingKey, boolean mandatory,
			boolean immediate, byte[] body);

	/**
	 * @param exchange
	 * @param routingKey
	 * @param mandatory
	 * @param immediate
	 * @param props
	 * @param body
	 */
	public void basicPublish(String exchange, String routingKey, boolean mandatory,
			boolean immediate, com.rabbitmq.client.AMQP.BasicProperties props, byte[] body);

	/**
	 * @param exchange
	 * @param routingKey
	 * @param body
	 */
	public void basicPublish(String exchange, String routingKey, byte[] body);

	/**
	 * This method closes the Channel
	 */
	public void close();

}
