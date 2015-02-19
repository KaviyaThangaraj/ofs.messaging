/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author ramanann
 *
 */
public class ClientAlreadyRegisteredException extends RuntimeException {

  /**
   * 
   */
  public ClientAlreadyRegisteredException() {

  }

  /**
   * @param message
   */
  public ClientAlreadyRegisteredException(String message) {
    super(message);

  }

  /**
   * @param cause
   */
  public ClientAlreadyRegisteredException(Throwable cause) {
    super(cause);

  }

  /**
   * @param message
   * @param cause
   */
  public ClientAlreadyRegisteredException(String message, Throwable cause) {
    super(message, cause);

  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public ClientAlreadyRegisteredException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

}
