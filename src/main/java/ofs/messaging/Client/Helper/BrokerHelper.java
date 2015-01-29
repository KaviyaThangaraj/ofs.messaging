/**
 * 
 */
package ofs.messaging.Client.Helper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ofs.messaging.testConsumer;
import ofs.messaging.Client.Exceptions.ExchangeCreationException;
import ofs.messaging.Client.Exceptions.SubscriptionError;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Models.Routing;

/**
 * @author ramanann
 *
 */
public class BrokerHelper {
  public static final Logger log = LoggerFactory.getLogger(BrokerHelper.class);

  public static void createAndBindQueue(String queueName, String exchangeId, String businessUnit)
      throws IOException, NamingException, KeyManagementException, NoSuchAlgorithmException,
      URISyntaxException, ConfigurationException, InterruptedException, ExecutionException {

    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");
    RabbitMQChannel channel = new RabbitMQChannel(con.connect());
    channel.createChannel();
    channel.queueDeclare(queueName);

    try {
      channel.queueBind(queueName, exchangeId,
          new Routing(businessUnit, exchangeId).getRoutingKey());
      channel.close();

    } catch (IOException e) {

      throw new SubscriptionError("Unable to create subscription. Please check if the "
          + "Event supplied is available. Please contact the helpdesk if there  are "
          + "still issues", e);

    } finally {
      con.close();
    }


  }

  public static void createExchange(String exchangeId) throws IOException, NamingException,
      KeyManagementException, NoSuchAlgorithmException, URISyntaxException {

    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");
    RabbitMQChannel channel = new RabbitMQChannel(con.connect());
    channel.createChannel();


    try {
      channel.exchangeDeclare(exchangeId);
      channel.close();

    } catch (Exception e) {

      throw new ExchangeCreationException("Unable to create Exchange", e);

    } finally {
      con.close();
    }


  }
}
