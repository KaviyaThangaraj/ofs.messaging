package ofs.messaging.Client;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;

import ofs.messaging.Message;
import ofs.messaging.Util;
import ofs.messaging.Client.Impl.HandlerResponse;
import ofs.messaging.Client.Impl.RabbitMQChannel;

public abstract class MessageHandler extends DefaultConsumer implements Handler {

	private RabbitMQChannel channel;

	public MessageHandler(ofs.messaging.Client.Channel channelObject) {
		super(((RabbitMQChannel) channelObject).getChannel());
		this.channel = (RabbitMQChannel) channelObject;
	}

	/* (non-Javadoc)
	 * @see ofs.messaging.Client.Handler#doProcess(byte[])
	 */
	public abstract void doProcess(byte[] body);

	/* (non-Javadoc)
	 * @see ofs.messaging.Client.Handler#handleDelivery(java.lang.String, com.rabbitmq.client.Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
			AMQP.BasicProperties properties, byte[] body) throws IOException {

		System.out.println("test");
		// msg = (Message) Util.toObject(body);
		System.out.println(envelope.getDeliveryTag());
		final long msgTag = envelope.getDeliveryTag();
		// Runnable runnable = new Runnable() {

		// public void run() {
		// handle the message here

		doProcess(body);
		try {
			System.out.println("inside doprocess implementation");
			channel.getChannel().basicAck(msgTag, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		// };

	}
}
