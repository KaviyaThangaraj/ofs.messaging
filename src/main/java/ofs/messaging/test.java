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
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Impl.MessagePublisher;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Client.Impl.RoutingKey;

public class test {

	public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(test.class);

	public static void main(String[] args) throws NamingException {

		Context ctx = new InitialContext();
		RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

		log.debug("start of producer");

		Channel channelObject = null;
		Message msg = null;
		CouchbaseClient cbClient = null;

		try {

			// creating a new event - this need not be tied to publishing. this is a seperate task
			// and getting its id

			String dispatchEventId = new Event("ORDER DESPATCH").getEventId();
			// creating a client instance, with client name and description. again, this can be done
			// long before publishing

			RabbitMQClient clientNew = new RabbitMQClient().getInstance("GMO OMS",
					"OFS Client description");

			cbClient = new test().setup();

			// registering the client to publish, providing the event type for the messages. as long
			// as we pass the event id it should be ok
			final String exchangeId = clientNew.registerClient(dispatchEventId);

			String clientId = clientNew.getClientId().toString();
			// creating a channel to connect and declaring the exchange
			channelObject = new RabbitMQChannel(con.connect());
			channelObject.createChannel();
			channelObject.exchangeDeclare(exchangeId);

			Path path = Paths.get("18slide.ppt");
			byte[] data = null;
			data = Files.readAllBytes(path);

			final RoutingKey r = new RoutingKey("GMO", dispatchEventId);
			String routingKey = r.getRoutingKey().toUpperCase();
			log.debug("Routing key ==>" + routingKey);

			long startTime = System.currentTimeMillis();

			for (int i = 0; i < 1000; i++) {

				Payload payload = new Payload();
				payload.setPayLoadFormat(PayloadFormat.BINARY);
				payload.setbData(data);
				msg = new Message(clientId, payload);

				MessagePublisher mp = new MessagePublisher(channelObject, exchangeId, r, msg);
				clientNew.publish(mp);
				publish(mp, cbClient);

			}

			clientNew.waitForScheduledTasksToComplete(20, TimeUnit.SECONDS);

			log.debug("Completed...");

			long endTime = System.currentTimeMillis();
			log.debug("Total duration is:" + Long.toString((endTime - startTime)));
			channelObject.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
	}

	private static void publish(MessagePublisher mp, CouchbaseClient cbClient) {
		cbClient.set(mp.getExchangeId() + "-" + mp.getRoutingKey(), mp.getMessage());
	}

	public CouchbaseClient setup() throws InterruptedException, ExecutionException {
		ArrayList<URI> nodes = new ArrayList<URI>();

		// Add one or more nodes of your cluster (exchange the IP with yours)
		nodes.add(URI.create("http://127.0.0.1:8091/pools"));

		// Try to connect to the client
		CouchbaseClient client = null;
		try {
			client = new CouchbaseClient(nodes, "default", "");
		} catch (Exception e) {
			System.err.println("Error connecting to Couchbase: " + e.getMessage());
			System.exit(1);
		}

		return client;
	}
}
