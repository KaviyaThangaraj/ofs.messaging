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
import ofs.messaging.Persistence.PersistenceManager;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testConsumer {

  public static final Logger log = LoggerFactory.getLogger(testConsumer.class);

  public testConsumer() {

  }

  public static void main(String[] args) throws NamingException {

    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

    Channel channelObject = null;
<<<<<<< HEAD:src/test/java/ofs/messaging/testConsumer.java
    String eventId = PersistenceManager.listEvents().get(6).getEventId();
    log.debug("Event id is {}", eventId);
=======
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/testConsumer.java

    try {
      RabbitMQClient clientNew =
<<<<<<< HEAD:src/test/java/ofs/messaging/testConsumer.java
          new RabbitMQClient().getInstance(new SubscriptionRegistration("CONSUMER1",
              "OFS Client description1", "BU1", eventId));

=======
          new RabbitMQClient().getInstance(new ClientRegistration("GMO OMS",
              "OFS Client description", "GMO", "69654ef1-5c99-4df6-b427-25427e4d4fdd"));
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/testConsumer.java

      String dispatchEventId = PersistenceManager.listEvents().get(6).getEventId();
      log.debug(dispatchEventId);

      String clientId = clientNew.getClientId().toString();
      final String exchangeId = PersistenceManager.getExangeIdFromClientId(clientId);

<<<<<<< HEAD:src/test/java/ofs/messaging/testConsumer.java
      log.debug("ClientId is {}", clientId);



=======
      if (exchangeId.isEmpty()) {
        throw new Exception("Exchange Id shouldnt be null. check the client id");
      }
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/testConsumer.java
      channelObject = new RabbitMQChannel(con.connect());

      // this is not required
      // channelObject.createChannel();

<<<<<<< HEAD:src/test/java/ofs/messaging/testConsumer.java

      String queueName = PersistenceManager.getQueueFromSubscriptionClientId(clientId);
      // string queueName is yet to be bound, hardcoding it now
      // queueName = "testPublishingWithNewClientRegistration";
=======
      String queueName = "test";
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/testConsumer.java

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
