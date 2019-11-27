package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import java.util.Optional;
import java.util.ResourceBundle;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CancelIntentHandler implements RequestHandler {

  private ResourceBundle bundle = ResourceBundle.getBundle("CancelIntentHandler");

  @Override
  public boolean canHandle(@NonNull final HandlerInput input) {
    return input.matches(intentName("AMAZON.CancelIntent"));
  }

  @Override
  public Optional<Response> handle(@NonNull final HandlerInput input) {
    String text = bundle.getString("TEXT");
    return input
        .getResponseBuilder()
        .withSpeech(text)
        .withSimpleCard(DealBookerUtils.getCardTitle(), text)
        .withShouldEndSession(Boolean.TRUE)
        .build();
  }
}
