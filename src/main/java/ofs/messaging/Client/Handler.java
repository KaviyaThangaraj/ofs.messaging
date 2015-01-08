package ofs.messaging.Client;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;

public interface Handler {

	public abstract void doProcess(byte[] body);

	public abstract void handleDelivery(String consumerTag, Envelope envelope,
			AMQP.BasicProperties properties, byte[] body) throws IOException;

}