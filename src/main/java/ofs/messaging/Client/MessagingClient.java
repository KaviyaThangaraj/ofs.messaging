/**
 * 
 */
package ofs.messaging.Client;

/**
 * @author Ramanan Natarajan
 *
 */
public interface MessagingClient {

	// public ofs.messaging.Client.Connection Connect();

	public boolean Publish();

	public boolean Consume();

	public String registerClient(String eventId);
}
