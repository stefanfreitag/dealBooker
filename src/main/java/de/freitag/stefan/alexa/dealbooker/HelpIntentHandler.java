package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import java.util.Optional;
import java.util.ResourceBundle;
import lombok.NonNull;

public class HelpIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {

  private ResourceBundle bundle = ResourceBundle.getBundle("HelpIntentHandler");

  @Override
  public boolean canHandle(@NonNull final HandlerInput input) {
    return input.matches(intentName("AMAZON.HelpIntent"));
  }

  @Override
  public Optional<Response> handle(@NonNull final HandlerInput input) {
    final String text = bundle.getString("HELP");
    return input
        .getResponseBuilder()
        .withSpeech(text)
        .withSimpleCard(DealBookerUtils.getCardTitle(), text)
        .withShouldEndSession(Boolean.FALSE)
        .build();
  }
}
