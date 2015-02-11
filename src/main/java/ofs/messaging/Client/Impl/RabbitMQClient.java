/**
 * 
 */
package ofs.messaging.Client.Impl;

import ofs.messaging.Util;
import ofs.messaging.testConsumer;
import ofs.messaging.Client.Channel;
import ofs.messaging.Client.Handler;
import ofs.messaging.Client.MessageHandler;
import ofs.messaging.Client.MessagingClient;
import ofs.messaging.Client.Exceptions.MessagePublishingFailedException;
import ofs.messaging.Models.ClientRegistration;
import ofs.messaging.Models.SubscriptionRegistration;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ramanan Natarajan
 *
 */
public class RabbitMQClient implements MessagingClient{

  public static final Logger log = LoggerFactory.getLogger(MessagingClient.class);

  private String description;
  private String clientName;
  private ExecutorService executorService;
  private MessageHandler handler;
  MessagePublisher messagepublisher=null;
  Callable<String> c=null;
 public static List<Future<String>> resultList = new ArrayList<Future<String>>();


  /*
   * FIXME: Also, when we create a client, we can return the Id, and then on registration take the
   * clientId and register and return back the exchange to which it has to be published
   */

  private String clientId;

  public RabbitMQClient() {

  }

  /**
	 * 
	 */

  private RabbitMQClient(ClientRegistration registration) {
    this.clientName = registration.getClientName();
    this.description = registration.getClientDescription();
    this.clientId = registration.getClientRegistrationId();

  }


  private RabbitMQClient(SubscriptionRegistration registration) {
    this.clientName = registration.getClientName();
    this.description = registration.getClientDescription();
    this.clientId = registration.getClientSubscriptionId();

    log.debug("Registration client id is------------>" + this.clientId);
  }

  private RabbitMQClient getRabbitMQClient(Object obj) {

    RabbitMQClient client = null;
    if (obj instanceof ClientRegistration) {
      client = new RabbitMQClient((ClientRegistration) obj);


    } else if ((obj instanceof SubscriptionRegistration)) {
      client = new RabbitMQClient((SubscriptionRegistration) obj);
    }

    return client;
  }

  /*
   * (non-Javadoc)
   * 
   * @see ofs.messaging.Client.MessagingClient#Consume(ofs.messaging.Client.Impl.MessageConsumer)
   */
  public void Consume(MessageConsumer msgConsumer) {

    this.executorService.execute(new MessageConsumer((Channel) msgConsumer.getChannel(),
        this.handler, msgConsumer.getQueueName()) {

    });

  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the executorService
   */
  public ExecutorService getExecutorService() {
    return executorService;
  }

  /**
   * @return the handler
   */
  public MessageHandler getHandler() {
    return handler;
  }

  public RabbitMQClient getInstance(Object obj) throws ClassNotFoundException {


    RabbitMQClient client = getRabbitMQClient(obj);



    if (client.executorService == null) {
      // We want at least 4 threads, even if we only have 2 CPUS.
    // int nThreads = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
    	System.out.println("hi");
    	int nThreads=1;
      final ThreadFactory clientThreadFactory = new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
          final Thread thread = new Thread(r, "client-" + threadNumber.getAndIncrement());
          thread.setDaemon(true);
          return thread;
        }
      };
      client.executorService  = Executors.newFixedThreadPool(nThreads, clientThreadFactory);
      
	
      
    }
    return client;

  }

  @SuppressWarnings("unchecked")
public void publish(MessagePublisher messagepublisher) {
	 
	 
	  
	  

                          // resultList.add((Future<Object>) this.executorService.submit(m)) ;
 this.c= new MessagePublisher(messagepublisher.getChannel(),
        messagepublisher.getExchangeId(), messagepublisher.getRoutingKey(), messagepublisher
        .getMessage());
   Future<String> future =(Future<String>) this.executorService.submit(c); 
   resultList.add( future);
   
   this.executorService.execute((Runnable) c);
                          
    
        
     

  }



  /**
   * @param executorService the executorService to set
   */
  public void setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * @param handler the handler to set
   */
  public void setHandler(MessageHandler handler) {
    this.handler = handler;
  }

 


}
