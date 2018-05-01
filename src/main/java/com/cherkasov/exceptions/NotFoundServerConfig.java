package com.cherkasov.exceptions;

public class NotFoundServerConfig extends Exception {

  public NotFoundServerConfig() {
    super();
  }

  public NotFoundServerConfig(String message) {
    super(message);
  }

  public NotFoundServerConfig(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundServerConfig(Throwable cause) {
    super(cause);
  }

  protected NotFoundServerConfig(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
