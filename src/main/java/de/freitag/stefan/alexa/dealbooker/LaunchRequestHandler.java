package de.freitag.stefan.alexa.dealbooker;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Session;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LaunchRequestHandler
    implements com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler {
  @Override
  public boolean canHandle(HandlerInput input, LaunchRequest launchRequest) {
    return true;
  }

  @Override
  public Optional<Response> handle(HandlerInput input, LaunchRequest launchRequest) {
    Request request = input.getRequestEnvelope().getRequest();
    Session session = input.getRequestEnvelope().getSession();
    log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

    Locale locale = new Locale.Builder().setLanguageTag(launchRequest.getLocale()).build();

    log.info("Using  locale " + locale);
    ResourceBundle bundle = ResourceBundle.getBundle("LaunchRequestHandler", locale);

    String text = bundle.getString("WELCOME") + " " + bundle.getString("WELCOME_REPROMPT");
    return input
        .getResponseBuilder()
        .withSpeech(text)
        .withSimpleCard(DealBookerUtils.CARD_TITLE, text)
        .build();
  }
}
