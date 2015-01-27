/**
 * 
 */
package ofs.messaging.Client.Impl;

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
import ofs.messaging.Client.Exceptions.SubscriptionError;
import ofs.messaging.Models.Routing;

/**
 * @author ramanann
 *
 */
public class QueueHelper {
  public static final Logger log = LoggerFactory.getLogger(QueueHelper.class);

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
}
