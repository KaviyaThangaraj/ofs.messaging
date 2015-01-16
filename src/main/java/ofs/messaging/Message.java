/**
 * This class encapsulates the messages that the producers write
 */
package ofs.messaging;

import java.io.Serializable;

/**
 * @author Ramanan Natarajan
 *
 */

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5758536527649505533L;
	private String MessageId = "";
	private int ttl = Constants.MAX_TIME_TO_LIVE_IN_DAYS;
	private Payload payload;
	private boolean isRedundant = true;
	private String clientId = "";
	private int sequenceNo = 0;

	/**
	 * 
	 * @param producerId
	 *            Single argument constructor that takes in a producer id and generates the Message
	 *            object
	 */
	public Message(String producerId) {

		this.clientId = producerId;
		this.MessageId = generateMessageId();

	}

	/**
	 * Two argument constructor takes in a producer id and payload object and creates a Message
	 * 
	 * @param clientId
	 *            clientId
	 * @param payload
	 *            the payload that needs to be transmitted
	 */

	public Message(String clientId, Payload payload) {

		this.clientId = clientId;
		this.MessageId = generateMessageId();
		this.payload = payload;

	}

	public Message(String clientId, Payload payload, boolean isRedundant) {

		this.clientId = clientId;
		this.MessageId = generateMessageId();
		this.payload = payload;
		this.isRedundant = isRedundant;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return MessageId;
	}

	/**
	 * @return the payload
	 */
	public Payload getPayload() {
		return payload;
	}

	/**
	 * @return the producerId
	 */
	public String getProducerId() {
		return clientId;
	}

	/**
	 * @return the time to live
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @return the isRedundant
	 */
	public boolean isRedundant() {
		return isRedundant;
	}

	/**
	 * returns a string representation of an UUID object.
	 * 
	 * @return
	 * 
	 */
	private String generateMessageId() {

		setMessageId(Util.getUUID().toString());
		return this.MessageId;

	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		MessageId = messageId;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	/**
	 * @param producerId
	 *            the producerId to set
	 */
	public void setProducerId(String producerId) {
		this.clientId = producerId;
	}

	/**
	 * @param isRedundant
	 *            the isRedundant to set
	 */
	public void setRedundant(boolean isRedundant) {
		this.isRedundant = isRedundant;
	}

	/**
	 * @param ttl
	 *            This is the time to live in Days. takes the value from constant
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	/**
	 * @return the sequenceNo
	 */
	public int getSequenceNo() {
		return sequenceNo;
	}

	/**
	 * @param sequenceNo
	 *            the sequenceNo to set
	 */
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
