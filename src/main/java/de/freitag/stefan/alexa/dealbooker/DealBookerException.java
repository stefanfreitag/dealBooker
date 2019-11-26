package de.freitag.stefan.alexa.dealbooker;

import java.io.Serializable;

class DealBookerException extends Exception implements Serializable {

  private static final long serialVersionUID = 1L;

  DealBookerException(final String message) {
    super(message);
  }
}
