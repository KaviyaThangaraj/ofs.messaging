package ofs.messaging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.rabbitmq.client.Envelope;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.MessageHandler;
import ofs.messaging.Client.Impl.HandlerResponse;
import ofs.messaging.Client.Impl.MessageConsumer;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Models.ClientRegistration;
import ofs.messaging.Models.SubscriptionRegistration;
import ofs.messaging.Persistence.PersistenceManager;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testConsumer1 {

  public static final Logger log = LoggerFactory.getLogger(testConsumer1.class);

  public testConsumer1() {

  }

  public static void main(String[] args) throws NamingException {

    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

    Channel channelObject = null;
    // same client, different event id
    String eventId = PersistenceManager.listEvents().get(5).getEventId();
    log.debug("Event id is {}", eventId);

    try {

      // creating the client and registering an event - for subscription?
      RabbitMQClient clientNew =
          new RabbitMQClient().getInstance(new SubscriptionRegistration("GMO OMS Consumer2",
              "OFS Client description2", "IGHS5", eventId));



      String clientId = clientNew.getClientId().toString();

      log.debug("ClientId is {}" + clientId);



      channelObject = new RabbitMQChannel(con.connect());



      String queueName = PersistenceManager.getQueueFromSubscriptionClientId(clientId);


      log.debug("Queue name is {}", queueName);
      MessageHandler messageHandler = new MessageHandler(channelObject) {

        @Override
        public String doProcess(byte[] msgBody) {

          Message msg = null;
          try {
            msg = (Message) Util.toObject(msgBody);
            log.debug("This is my message Id==>" + msg.getMessageId());

          } catch (ClassNotFoundException e) {

            log.error("Processing failed", e);

          } catch (IOException e) {

            log.error("Processing failed", e);
          } catch (Exception e) {

            log.error("Processing failed", e);
          }
          if (msg != null) {
            return msg.getMessageId();
          } else {
            return null;
          }

        }
      };

      clientNew.setHandler(messageHandler);
      MessageConsumer msgConsumer = new MessageConsumer(channelObject, messageHandler, queueName);
      clientNew.Consume(msgConsumer);

    } catch (Exception e) {

      log.error("Consumer failed", e);
      System.exit(-1);
    }
  }
}
