package ofs.messaging.Client;

import java.io.IOException;

import ofs.messaging.Client.Exceptions.MessageDeliveryFailedException;
import ofs.messaging.Client.Impl.RabbitMQChannel;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public abstract class MessageHandler extends DefaultConsumer implements Handler {

	private RabbitMQChannel channel;

	public MessageHandler(ofs.messaging.Client.Channel channelObject) {
		super(((RabbitMQChannel) channelObject).getChannel());
		this.channel = (RabbitMQChannel) channelObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ofs.messaging.Client.Handler#doProcess(byte[])
	 */
	public abstract void doProcess(byte[] body);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ofs.messaging.Client.Handler#handleDelivery(java.lang.String,
	 * com.rabbitmq.client.Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
			AMQP.BasicProperties properties, byte[] body) throws IOException {

		final long msgTag = envelope.getDeliveryTag();

		try {
			doProcess(body);
			channel.getChannel().basicAck(msgTag, false);
		} catch (IOException e) {

			throw new MessageDeliveryFailedException("Processing/ or Ack Failed", e);
		}

	}
}
