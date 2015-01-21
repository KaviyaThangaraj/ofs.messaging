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
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

public class testConsumer {

  public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(testConsumer.class);

  public testConsumer() {

  }

  public static void main(String[] args) throws NamingException {

    Context ctx = new InitialContext();
    RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

    Channel channelObject = null;

    try {
      RabbitMQClient clientNew =
          new RabbitMQClient().getInstance(new ClientRegistration("GMO OMS",
              "OFS Client description", "GMO", "69654ef1-5c99-4df6-b427-25427e4d4fdd"));

      String dispatchEventId = PersistenceManager.listEvents().get(6).getEventId();
      log.debug(dispatchEventId);

      String clientId = clientNew.getClientId().toString();
      final String exchangeId = PersistenceManager.getExangeIdFromClientId(clientId);

      if (exchangeId.isEmpty()) {
        throw new Exception("Exchange Id shouldnt be null. check the client id");
      }
      channelObject = new RabbitMQChannel(con.connect());

      // this is not required
      // channelObject.createChannel();

      String queueName = "test";

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
          return msg.getMessageId();
        }
      };

      clientNew.setHandler(messageHandler);
      MessageConsumer msgConsumer = new MessageConsumer(channelObject, messageHandler, queueName);
      clientNew.Consume(msgConsumer);

    } catch (Exception e) {

      log.error("Consumer failed", e);
    }
  }

}
