package ofs.messaging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Impl.MessagePublisher;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;
import ofs.messaging.Client.Impl.RoutingKey;

public class test {

	public static void main(String[] args) {
		RabbitMQConnection con = new RabbitMQConnection("localhost", 5673);

		Channel channelObject = null;
		Message msg = null;

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

			Path path = Paths.get("18slide.ppt");
			byte[] data = null;
			data = Files.readAllBytes(path);

			final RoutingKey r = new RoutingKey("GMO", dispatchEventId);
			String routingKey = r.getRoutingKey().toUpperCase();
			System.out.println(routingKey);

			long startTime = System.currentTimeMillis();

			for (int i = 0; i < 100000; i++) {

				Payload payload = new Payload();
				payload.setPayLoadFormat(PayloadFormat.BINARY);
				payload.setbData(data);
				msg = new Message(clientId, payload);

				clientNew.publish(new MessagePublisher(channelObject, exchangeId, r, msg));

			}

			clientNew.waitForScheduledTasksToComplete(200, TimeUnit.SECONDS);

			System.out.println("done");
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime));
			channelObject.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
	}
}
