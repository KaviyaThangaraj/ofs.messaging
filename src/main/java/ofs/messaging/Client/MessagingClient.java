/**
 * 
 */
package ofs.messaging.Client;

import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.Client.Impl.MessageConsumer;
import ofs.messaging.Client.Impl.MessagePublisher;

/**
 * @author Ramanan Natarajan
 *
 */
public interface MessagingClient {



  public void Consume(MessageConsumer msgConsumer);

  public void publish(MessagePublisher messagePublisher);
}
