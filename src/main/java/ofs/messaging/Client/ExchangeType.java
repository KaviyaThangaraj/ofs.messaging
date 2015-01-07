/**
 * 
 */
package ofs.messaging.Client;

/**
 * @author Ramanan Natarajan
 *
 *         Direct defines an exchange such that message goes to the queues whose binding key exactly
 *         matches the routing key of the message
 *         <p>
 * 
 *         <p>
 *         Topic defines an exchange of type TOPIC
 *         <p>
 *         Headers defines and exchange where routing key will be ignored and the header will be
 *         used to route
 *         <p>
 *         Fanout defines an exchange where irrespective of the routing key, messages will sent to
 *         all queues
 */
public enum ExchangeType {
	// DIRECT="direct", TOPIC="topic", HEADERS="headers", FANOUT="fanout";
	direct, topic, headers, fanout;

}
