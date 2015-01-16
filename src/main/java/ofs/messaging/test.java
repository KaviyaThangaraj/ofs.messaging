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

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.Module.SetupContext;
import com.google.gson.Gson;
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Impl.MessagePublisher;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Client.Impl.RoutingKey;

public class test {

	public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(test.class);
	public static int i = 0;

	public static void main(String[] args) throws NamingException {

		// creating a context - use the jndi proeprties file for url and initial context factory
		Context ctx = new InitialContext();
		RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

		log.info("start of producer");

		Channel channelObject = null;
		Message msg = null;
		boolean isRedundant = true;

		try {

			// creating a new event - this need not be tied to publishing. this is a seperate task
			// and getting its id

			String dispatchEventId = new Event("ORDER DESPATCH").getEventId();
			// creating a client instance, with client name and description. again, this can be done
			// long before publishing

			RabbitMQClient clientNew = new RabbitMQClient().getInstance("GMO OMS",
					"OFS Client description");

			// registering the client to publish, providing the event type for the messages. as long
			// as we pass the event id it should be ok
			final String exchangeId = clientNew.registerClient(dispatchEventId);

			String clientId = clientNew.getClientId().toString();
			// creating a channel to connect and declaring the exchange
			channelObject = new RabbitMQChannel(con.connect());
			channelObject.createChannel();
			channelObject.exchangeDeclare(exchangeId);

			Path path = Paths.get("test.json");
			byte[] data = null;
			data = Files.readAllBytes(path);

			final RoutingKey r = new RoutingKey("GMO", dispatchEventId);
			String routingKey = r.getRoutingKey().toUpperCase();
			log.debug("Routing key ==>" + routingKey);

			long startTime = System.currentTimeMillis();

			for (int i = 0; i < 100; i++) {

				Payload payload = new Payload();
				payload.setPayLoadFormat(PayloadFormat.JSON);
				payload.setData(new String(data));
				// payload.setbData(data);

				msg = new Message(clientId, payload, isRedundant = isRedundant ? false : true);
				log.debug(new Boolean(isRedundant).toString());

				MessagePublisher mp = new MessagePublisher(channelObject, exchangeId, r, msg);
				clientNew.publish(mp);
				// publish(mp, cbClient);

			}

			clientNew.waitForScheduledTasksToComplete(20, TimeUnit.SECONDS);

			log.debug("Completed...");

			long endTime = System.currentTimeMillis();
			log.debug("Total duration is:" + Long.toString((endTime - startTime)));
			channelObject.close();
			con.close();

		} catch (Exception e) {
			log.error("App failed", e);

		} finally {

		}
	}
}
