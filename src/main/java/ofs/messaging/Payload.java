/**
 * @author Ramanan Natarajan
 *
 */

package ofs.messaging;

import org.w3c.dom.Document;
import java.io.Serializable;

/**
 * 
 * This class encapulates the payload attached with the message and contains either the string or
 * binary representation to accomodate string, Document or a binary data (example being video stream
 * etc.,)
 *
 */

public class Payload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4074735503360008775L;
	private String data = "";
	private byte[] bData = null;
	private Document xmlData;
	private PayloadFormat payLoadFormat;

	/**
	 * @return the bData
	 * 
	 */
	public byte[] getbData() {
		return bData;
	}

	/**
	 * @param bData
	 *            the bData to set
	 */
	public void setbData(byte[] bData) {
		this.bData = bData;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the payLoadFormat
	 */
	public PayloadFormat getPayLoadFormat() {
		return payLoadFormat;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public PayloadFormat getPayloadFormat() {
		return this.payLoadFormat;
	}

	/**
	 * @param payLoadFormat
	 *            the payLoadFormat to set
	 */
	public void setPayLoadFormat(PayloadFormat payLoadFormat) {
		this.payLoadFormat = payLoadFormat;
	}

	/**
	 * @return the xmlData
	 */
	public Document getXmlData() {
		return xmlData;
	}

	/**
	 * @param xmlData
	 *            the xmlData to set
	 */
	public void setXmlData(Document xmlData) {
		this.xmlData = xmlData;
	}
}
