package ofs.messaging;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.rabbitmq.client.Envelope;

import ofs.messaging.Client.Channel;
import ofs.messaging.Client.MessageHandler;
import ofs.messaging.Client.Impl.HandlerResponse;
import ofs.messaging.Client.Impl.MessageConsumer;
import ofs.messaging.Client.Impl.RabbitMQChannel;
import ofs.messaging.Client.Impl.RabbitMQClient;
import ofs.messaging.Client.Impl.RabbitMQConnection;

import com.rabbitmq.client.AMQP;

public class testConsumer {

	public testConsumer() {

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
			String queueName = "test";

			MessageHandler messageHandler = new MessageHandler(channelObject) {

				@Override
				public void doProcess(byte[] msgBody) {

					Message msg;
					try {
						msg = (Message) Util.toObject(msgBody);
						System.out.println("this is my message==>" + msg.getMessageId());

					} catch (ClassNotFoundException e) {

						e.printStackTrace(); // stack trace is used as this is a test harness. this
												// shoudnt be in any core code
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			};

			clientNew.setHandler(messageHandler);
			MessageConsumer msgConsumer = new MessageConsumer(channelObject, messageHandler,
					queueName);
			clientNew.Consume(msgConsumer);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
