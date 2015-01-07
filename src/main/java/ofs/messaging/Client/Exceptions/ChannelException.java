/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author Ramanan Natarajan
 *
 */
public class ChannelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8134038203160851350L;

	/**
	 * Single argument constructor
	 */
	public ChannelException() {

	}

	/**
	 * @param message
	 *            user friendly message
	 */
	public ChannelException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 * 
	 *            cause denotes the reason for the exception
	 */
	public ChannelException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 *            is the customised message
	 * @param cause
	 *            is the exception thrown
	 * 
	 * 
	 */
	public ChannelException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 *            message the detail message.
	 * @param cause
	 *            cause the cause.(A null value is permitted, and indicates that the cause is
	 *            nonexistent or unknown.)
	 * @param enableSuppression
	 *            enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            writableStackTrace whether or not the stack trace should be writable
	 * 
	 * 
	 */
	public ChannelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
