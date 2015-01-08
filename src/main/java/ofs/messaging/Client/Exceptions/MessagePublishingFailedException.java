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

	}

	/**
	 * @param message
	 *            is the customised message
	 */
	public MessagePublishingFailedException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 *            the reason for the excepti
	 */
	public MessagePublishingFailedException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 *            is the customised message
	 * @param cause
	 *            the reason for the exception
	 */
	public MessagePublishingFailedException(String message, Throwable cause) {
		super(message, cause);

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
