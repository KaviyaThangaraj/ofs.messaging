/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author Ramanan Natarajan
 *
 */
public class ExchangeCreationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392295708337154932L;

	/**
	 * No argument constructor
	 */
	public ExchangeCreationException() {

	}

	/**
	 * @param message
	 *            is the customized message
	 */
	public ExchangeCreationException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 *            the reason for the exception
	 */
	public ExchangeCreationException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 *            is the customised message
	 * @param cause
	 *            the reason for the exception
	 */
	public ExchangeCreationException(String message, Throwable cause) {
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
	public ExchangeCreationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
