/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author ramanann
 *
 */
public class ClientAlreadySubscribedToThisEventException extends RuntimeException {

  /**
   * 
   */
  public ClientAlreadySubscribedToThisEventException() {

  }

  /**
   * @param message
   */
  public ClientAlreadySubscribedToThisEventException(String message) {
    super(message);

  }

  /**
   * @param cause
   */
  public ClientAlreadySubscribedToThisEventException(Throwable cause) {
    super(cause);

  }

  /**
   * @param message
   * @param cause
   */
  public ClientAlreadySubscribedToThisEventException(String message, Throwable cause) {
    super(message, cause);

  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public ClientAlreadySubscribedToThisEventException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

}
