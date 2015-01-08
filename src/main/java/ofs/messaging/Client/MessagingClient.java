/**
 * 
 */
package ofs.messaging.Client;

import ofs.messaging.Client.Impl.MessageConsumer;
import ofs.messaging.Client.Impl.MessagePublisher;

/**
 * @author Ramanan Natarajan
 *
 */
public interface MessagingClient {

	// public ofs.messaging.Client.Connection Connect();

	public String registerClient(String eventId);

	public void Consume(MessageConsumer msgConsumer);

	public void publish(MessagePublisher messagePublisher);
}
