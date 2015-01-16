package ofs.messaging;

import java.io.Serializable;

import ofs.messaging.DocumentType;
import ofs.messaging.Message;
import ofs.messaging.Client.Impl.RoutingKey;

/**
 * 
 */

/**
 * @author ramanann
 *
 */
public class Document implements Serializable {

	private String id;
	private DocumentType docType;
	private String routingKeyId;
	private Message msg;

	/**
	 * 
	 */

	public Document(String id, DocumentType docType, String routingKeyId, Message msg) {
		this.id = id;
		this.docType = docType;
		this.routingKeyId = routingKeyId;
		this.msg = msg;

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the docType
	 */
	public DocumentType getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            the docType to set
	 */
	public void setDocType(DocumentType docType) {
		this.docType = docType;
	}

	/**
	 * @return the routingKeyId
	 */
	public String getRoutingKeyId() {
		return routingKeyId;
	}

	/**
	 * @param routingKeyId
	 *            the routingKeyId to set
	 */
	public void setRoutingKeyId(String routingKeyId) {
		this.routingKeyId = routingKeyId;
	}

	/**
	 * @return the msg
	 */
	public Message getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(Message msg) {
		this.msg = msg;
	}
}
