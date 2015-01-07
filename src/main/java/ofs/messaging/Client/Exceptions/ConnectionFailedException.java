package ofs.messaging.Client.Exceptions;

/**
 * @author Ramanan Natarajan
 *         <p>
 *         Description This is a customised Exception class that is thrown when a connection fails.
 */
public class ConnectionFailedException extends RuntimeException {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 8081331316651675513L;

	/**
	 * No argument constructor that calls the RuntimeException's constructor
	 */
	public ConnectionFailedException() {
		super();
	}

	/**
	 * @param Message
	 *            is the customised Message
	 */
	public ConnectionFailedException(String Message) {
		super(Message);

	}

	/**
	 * @param Message
	 *            is the customised Message
	 * @param cause
	 *            is the reason for the exception
	 */
	public ConnectionFailedException(String Message, Throwable cause) {
		super(Message, cause);
	}

	/**
	 * @param cause
	 *            is the reason for the exception
	 */
	public ConnectionFailedException(Throwable cause) {
		super(cause);
	}

}
