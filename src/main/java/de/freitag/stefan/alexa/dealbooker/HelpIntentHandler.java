package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import java.util.Optional;
import java.util.ResourceBundle;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HelpIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {

  private ResourceBundle bundle = ResourceBundle.getBundle("HelpIntentHandler");

  @Override
  public boolean canHandle(HandlerInput input) {
    return input.matches(intentName("AMAZON.HelpIntent"));
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {
    String help = bundle.getString("HELP");
    return input
        .getResponseBuilder()
        .withSpeech(help)
        .withSimpleCard(DealBookerUtils.CARD_TITLE, help)
        .build();
  }
}
