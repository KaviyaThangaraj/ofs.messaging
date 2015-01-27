/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author ramanann
 *
 */
public class ClientIdDoesNotExistException extends RuntimeException {

  /**
   * 
   */
  public ClientIdDoesNotExistException() {

  }

  /**
   * @param message
   */
  public ClientIdDoesNotExistException(String message) {
    super(message);

  }

  /**
   * @param cause
   */
  public ClientIdDoesNotExistException(Throwable cause) {
    super(cause);

  }

  /**
   * @param message
   * @param cause
   */
  public ClientIdDoesNotExistException(String message, Throwable cause) {
    super(message, cause);

  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public ClientIdDoesNotExistException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

}
