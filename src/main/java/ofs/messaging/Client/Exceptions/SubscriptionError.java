/**
 * 
 */
package ofs.messaging.Client.Exceptions;

/**
 * @author ramanann
 *
 */
public class SubscriptionError extends RuntimeException {

  /**
   * 
   */
  public SubscriptionError() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   */
  public SubscriptionError(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param cause
   */
  public SubscriptionError(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   */
  public SubscriptionError(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public SubscriptionError(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }

}
