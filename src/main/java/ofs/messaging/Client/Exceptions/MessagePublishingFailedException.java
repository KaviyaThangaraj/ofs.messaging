/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author Ramanan Natarajan
 *
 */
public class MessagePublishingFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 556970869603530072L;

	/**
	 * 
	 */
	public MessagePublishingFailedException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 *            is the customised message
	 */
	public MessagePublishingFailedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 *            the reason for the excepti
	 */
	public MessagePublishingFailedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 *            is the customised message
	 * @param cause
	 *            the reason for the exception
	 */
	public MessagePublishingFailedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 *            is the customized message
	 * @param cause
	 *            the reason for the exception
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	public MessagePublishingFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
