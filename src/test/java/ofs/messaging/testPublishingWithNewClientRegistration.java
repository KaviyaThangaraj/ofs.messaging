package ofs.messaging;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

<<<<<<< HEAD:src/test/java/ofs/messaging/testPublishingWithNewClientRegistration.java
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

=======
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/test.java
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.Module.SetupContext;
import com.google.gson.Gson;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Impl.MessagePublisher;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Models.ClientRegistration;
<<<<<<< HEAD:src/test/java/ofs/messaging/testPublishingWithNewClientRegistration.java
import ofs.messaging.Models.Routing;
=======
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/test.java
import ofs.messaging.Persistence.PersistenceManager;

public class testPublishingWithNewClientRegistration {

  public static final Logger log = LoggerFactory
      .getLogger(testPublishingWithNewClientRegistration.class);
  public static int i = 0;

  public static void main(String[] args) throws NamingException {

<<<<<<< HEAD:src/test/java/ofs/messaging/testPublishingWithNewClientRegistration.java

    // PersistenceManager.cleanUp();

=======
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/test.java
    // creating a context - use the jndi proeprties file for url and initial context factory
    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

    log.info("start of producer 1");

    Channel channelObject = null;
    Message msg = null;
    boolean isRedundant = true;

    try {

<<<<<<< HEAD:src/test/java/ofs/messaging/testPublishingWithNewClientRegistration.java

=======
      // creating a client instance, with client name and description. again, this can be done
      // long before publishing

      String dispatchEventId = "69654ef1-5c99-4df6-b427-25427e4d4fdd";// PersistenceManager.listEvents().get(6).getEventId();
      // log.debug(dispatchEventId);

      ClientRegistration cr =
          new ClientRegistration("GMO OMS", "OFS Client description", "GMO", dispatchEventId);
>>>>>>> parent of 5b004dc... cleaned up. added subscription:src/main/java/ofs/messaging/test.java

      // getting an event id as we want to publish messages for those events
      String eventId = PersistenceManager.listEvents().get(6).getEventId(); // 6 is dispatch in our
                                                                            // testPublishingWithNewClientRegistration
                                                                            // db
      log.debug(eventId);

      ClientRegistration cr =
          new ClientRegistration("CLIENTNAME", "CLIENT DESCRIPTION", "BU1", eventId);


      String clientId = cr.getClientRegistrationId();
      log.debug(cr.getExchangeId());

      final String exchangeId =
          PersistenceManager.getExangeIdFromClientIdAndEventId(clientId, eventId);

      if (exchangeId.isEmpty()) {
        throw new Exception("Exchange Id shouldnt be null. check the client id");

      }


      RabbitMQClient clientNew = new RabbitMQClient().getInstance(cr);
      // creating a channel to connect and declaring the exchange
      channelObject = new RabbitMQChannel(con.connect());
      channelObject.createChannel();



      Path path = Paths.get("test.json");
      byte[] data = null;
      data = Files.readAllBytes(path);


      String routingKey = cr.getRoutingKey().getRoutingKey();
      log.debug("Routing key ==>" + routingKey);

      long startTime = System.currentTimeMillis();

      for (;;) {

        Payload payload = new Payload();
        payload.setPayLoadFormat(PayloadFormat.JSON);
        payload.setData(new String(data));
        isRedundant = isRedundant ? false : true;
        msg = new Message(clientId, payload, isRedundant);
        log.debug(new Boolean(isRedundant).toString());

        MessagePublisher mp = new MessagePublisher(channelObject, exchangeId, routingKey, msg);
        clientNew.publish(mp);
        Thread.sleep(1000);

      }

      // clientNew.waitForScheduledTasksToComplete(20, TimeUnit.SECONDS);
      //
      // log.debug("Completed...");
      //
      // long endTime = System.currentTimeMillis();
      // log.debug("Total duration is:" + Long.toString((endTime - startTime)));
      // channelObject.close();
      // con.close();

    } catch (Exception e) {
      log.error("App failed", e);

    } finally {
for(int i=0;i<clientNew.resultList.size();i++)
    System.out.println(clientNew.resultList.get(i).get());
      System.exit(0);
    }
  }
}
