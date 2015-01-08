/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author ramanann
 *
 */
public class MessageDeliveryFailedException extends RuntimeException {

	/**
	 * 
	 */
	public MessageDeliveryFailedException() {

	}

	/**
	 * @param message
	 */
	public MessageDeliveryFailedException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public MessageDeliveryFailedException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageDeliveryFailedException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MessageDeliveryFailedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
