package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.requestType;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.SessionEndedRequest;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SessionEndedRequestHandler
    implements com.amazon.ask.dispatcher.request.handler.RequestHandler {

  @Override
  public boolean canHandle(@NonNull final HandlerInput input) {
    return input.matches(requestType(SessionEndedRequest.class));
  }

  @Override
  public Optional<Response> handle(@NonNull final HandlerInput input) {
    final Request request = input.getRequestEnvelope().getRequest();
    final Session session = input.getRequestEnvelope().getSession();
    log.info(
        "onSessionEnded requestId={}, sessionId={}",
        request.getRequestId(),
        session.getSessionId());
    return Optional.empty();
  }
}
