/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ofs.messaging.Client.ExchangeType;
import ofs.messaging.Client.Exceptions.ChannelException;
import ofs.messaging.Client.Exceptions.ConnectionFailedException;
import ofs.messaging.Client.Exceptions.ExchangeCreationException;
import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author Ramanan Natarajan
 *
 */
public class RabbitMQChannel implements ofs.messaging.Client.Channel {

	 public static final Logger log = LoggerFactory.getLogger(RabbitMQChannel.class);
  private com.rabbitmq.client.Connection connection = null;
  private Channel channel = null;

  /**
   * @return the connection
   */
  public com.rabbitmq.client.Connection getConnection() {
    return connection;
  }

  /**
   * @param connection the connection to set
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
   * @param channel the channel to set
   */
  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  /**
   * @param connection
   * @throws IOException
   */
  public RabbitMQChannel(com.rabbitmq.client.Connection connection) throws IOException {
    super();
    this.connection = connection;
    if (this.channel == null) {
      this.channel = this.connection.createChannel();
    }

  }

  public Channel createChannel() {

    try {
      if (this.channel == null) {
        this.channel = this.connection.createChannel();
      }
      return this.channel;
    } catch (IOException e) {

      throw new ConnectionFailedException(e);
    }

  }

  public void exchangeDeclare(String exchange, ExchangeType type, boolean durable,
      boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    try {

      if (this.channel == null) {
        throw new IllegalArgumentException("Channel has not been created yet or it is null");
      }

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

    // FIXME: understand what false means in internal exchange
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
      log.error("Successfully published");
     // body = null;
    } catch (Exception e) {

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

    String envelope = this.channel.basicConsume(queue, callback);

    return envelope;
  }

  // FIXME: fix all the overload methods of the basic consume
  public String basicConsume(String queue, boolean autoAck, DefaultConsumer callback)
      throws IOException {
    return null;
  }

  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments,
      DefaultConsumer callback) throws IOException {

    return null;
  }

  public String basicConsume(String queue, boolean autoAck, String consumerTag,
      DefaultConsumer callback) throws IOException {

    return null;
  }

  public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal,
      boolean exclusive, Map<String, Object> arguments, Consumer callback) throws IOException {

    return null;
  }

  public void basicCancel(String consumerTag) throws IOException {

  }

  public String basicConsume(String queue, Consumer callback) throws IOException {

    return null;
  }

  public String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException {

    return null;
  }

  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments,
      Consumer callback) throws IOException {

    return null;
  }

  public String basicConsume(String queue, boolean autoAck, String consumerTag, Consumer callback)
      throws IOException {

    return null;
  }

  public GetResponse basiGet(String queueName, boolean autoAck) throws IOException {

    return this.channel.basicGet(queueName, autoAck);

  }

  @Override
  public void queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
      Map<String, Object> arguments) throws IOException {

    this.channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
  }

  @Override
  public void queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete)
      throws IOException {

    queueDeclare(queue, durable, false, autoDelete, null);
  }

  @Override
  public void queueDeclare(String queue, boolean durable, boolean autoDelete) throws IOException {

    queueDeclare(queue, durable, true, autoDelete, null);
  }

  @Override
  public void queueDeclare(String queue) throws IOException {
    queueDeclare(queue, true, true, false);
  }

  @Override
  public void queueBind(String queue, String exchange, String routingKey,
      Map<String, Object> arguments) throws IOException {


    this.channel.queueBind(queue, exchange, routingKey, null);
  }

  @Override
  public void queueBind(String queue, String exchange, String routingKey) throws IOException {
    queueBind(queue, exchange, routingKey, null);

  }


}
