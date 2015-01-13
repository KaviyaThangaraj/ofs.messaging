package ofs.messaging;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.rabbitmq.client.Envelope;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.MessageHandler;
import ofs.messaging.Client.Impl.HandlerResponse;
import ofs.messaging.Client.Impl.MessageConsumer;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;

import com.rabbitmq.client.AMQP;
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

public class testConsumer {

	public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(testConsumer.class);

	public testConsumer() {

	}

	public static void main(String[] args) throws NamingException {

		// RabbitMQConnection con = new RabbitMQConnection("localhost", 5673);

		Context ctx = new InitialContext();
		RabbitMQConnection con = (RabbitMQConnection) ctx.lookup("RabbitMQConnection");

		Channel channelObject = null;

		try {
			RabbitMQClient clientNew = new RabbitMQClient().getInstance("GMO OMS CONSUMER",
					"OFS Client Consumer description");
			// Query the list of all events and subscribe to it. choose an event id. below, we have
			// assumed that this is the dispatch event

			String dispatchEventId = ""; // this is the event we need to consume

			final String exchangeId = clientNew.registerClient(dispatchEventId);
			channelObject = new RabbitMQChannel(con.connect());
			channelObject.createChannel();
			channelObject.exchangeDeclare(exchangeId);
			String queueName = "test";

			MessageHandler messageHandler = new MessageHandler(channelObject) {

				@Override
				public void doProcess(byte[] msgBody) {

					Message msg;
					try {
						msg = (Message) Util.toObject(msgBody);
						log.debug("This is my message Id==>" + msg.getMessageId());

					} catch (ClassNotFoundException e) {

						log.error("Processing failed", e);

					} catch (IOException e) {

						log.error("Processing failed", e);
					}

				}

			};

			clientNew.setHandler(messageHandler);
			MessageConsumer msgConsumer = new MessageConsumer(channelObject, messageHandler,
					queueName);
			clientNew.Consume(msgConsumer);

		} catch (Exception e) {

			log.error("Consumer failed", e);
		}
	}
}
