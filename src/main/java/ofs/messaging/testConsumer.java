package ofs.messaging;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;

public class testConsumer {

	public testConsumer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		RabbitMQConnection con = new RabbitMQConnection("localhost", 5673);
		Channel channelObject = null;

		try {
			RabbitMQClient clientNew = new RabbitMQClient().getInstance("GMO OMS CONSUMER",
					"OFS Client Consumer description");
			String dispatchEventId = ""; // this is the event we need to consume
			final String exchangeId = clientNew.registerClient(dispatchEventId);
			channelObject = new RabbitMQChannel(con.connect());
			channelObject.createChannel();
			channelObject.exchangeDeclare(exchangeId);

		} catch (Exception e) {

		}
	}

}
