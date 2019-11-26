package de.freitag.stefan.alexa.dealbooker;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GenericExceptionHandler
    implements com.amazon.ask.dispatcher.exception.ExceptionHandler {
  @Override
  public boolean canHandle(HandlerInput input, Throwable throwable) {
    return false;
  }

  @Override
  public Optional<Response> handle(HandlerInput input, Throwable throwable) {
    return Optional.empty();
  }
}
