package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.StatusCode;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DealIntentHandler
    implements com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler {

  private ResourceBundle bundle = ResourceBundle.getBundle("DealIntentHandler");

  @Override
  public boolean canHandle(@NonNull final HandlerInput input, IntentRequest intentRequest) {
    return input.matches(intentName("enterDeal"));
  }

  @Override
  public Optional<Response> handle(
      @NonNull final HandlerInput input, @NonNull final IntentRequest intentRequest) {
    com.amazon.ask.model.Slot dealTypeSlot =
        intentRequest.getIntent().getSlots().get(Slot.DEAL_TYPE.name());

    Optional<DealType> dealType = getDealType(dealTypeSlot);
    if (!dealType.isPresent()) {
      log.error("Deal type is absent.");
      return createErrorResponse(
          input, "I had problems in understanding if you would like to buy or sell");
    }

    log.info("Found deal type " + dealType + ".");

    int quantity;
    try {
      quantity = getQuantity(intentRequest.getIntent());
      log.info("Found quantity " + quantity + ".");
    } catch (final DealBookerException exception) {
      log.error(exception.getMessage(), exception);
      return createErrorResponse(input, exception.getMessage());
    }

    com.amazon.ask.model.Slot unitSlot = intentRequest.getIntent().getSlots().get(Slot.UNIT.name());
    Optional<Unit> unit = getUnit(unitSlot);
    if (!unit.isPresent()) {
      log.error("Unit value is absent.");
      return createErrorResponse(input, "I had problems understanding the unit.");
    }
    log.info("Found unit " + unit);

    int price;
    try {
      price = getPrice(intentRequest.getIntent());
      log.info("Found price " + quantity);
    } catch (DealBookerException exception) {
      log.error(exception.getMessage());
      return createErrorResponse(input, exception.getMessage());
    }

    com.amazon.ask.model.Slot productSlot =
        intentRequest.getIntent().getSlots().get(Slot.PRODUCT.name());
    Optional<Product> product = getProduct(productSlot);
    if (!product.isPresent()) {
      log.error("Product information is missing");
      return createErrorResponse(input, "I had problems figuring out the product.");
    }
    log.info("Found product: " + product);

    String speechText;
    if (DealType.BUY.equals(dealType.get())) {
      speechText =
          MessageFormat.format(
              bundle.getString("BUY_INFORMATION"),
              new Object[] {quantity, unit.get().name(), product.get().name(), price});
    } else {
      speechText =
          MessageFormat.format(
              bundle.getString("SELL_INFORMATION"),
              new Object[] {quantity, unit.get().name(), product.get().name(), price});
    }
    Mailer.sendMail(dealType.get(), quantity, unit.get(), product.get(), price);

    return input
        .getResponseBuilder()
        .withSpeech(speechText)
        .withSimpleCard(DealBookerUtils.getCardTitle(), speechText)
        .build();
  }

  // TODO: Refactoring with getQuantity
  private int getPrice(final Intent intent) throws DealBookerException {
    com.amazon.ask.model.Slot priceSlot = intent.getSlots().get(Slot.PRICE.name());
    String value = priceSlot.getValue();
    if (value != null) {
      try {
        return Integer.valueOf(priceSlot.getValue());
      } catch (NumberFormatException exception) {
        throw new DealBookerException("Could not parse price information.");
      }
    } else {
      throw new DealBookerException("Information on price is missing.");
    }
  }

  private int getQuantity(final Intent intent) throws DealBookerException {
    com.amazon.ask.model.Slot quantitySlot = intent.getSlots().get(Slot.QUANTITY.name());
    String value = quantitySlot.getValue();
    if (value != null) {
      return Integer.valueOf(quantitySlot.getValue());
    } else {
      throw new DealBookerException("Information on quantity is missing.");
    }
  }

  private Optional<DealType> getDealType(@NonNull final com.amazon.ask.model.Slot dealTypeSlot) {
    final String value = getIdFromResolution(dealTypeSlot);
    return DealType.from(value);
  }

  private Optional<Unit> getUnit(@NonNull final com.amazon.ask.model.Slot unitSlot) {
    String value = getIdFromResolution(unitSlot);
    return Unit.from(value.toUpperCase());
  }

  private Optional<Product> getProduct(@NonNull final com.amazon.ask.model.Slot productSlot) {
    String value = getIdFromResolution(productSlot);
    return Product.from(value.toUpperCase());
  }

  private String getIdFromResolution(@NonNull final com.amazon.ask.model.Slot slot) {
    String value = slot.getValue();
    Resolution resolution =
        (slot.getResolutions() != null
                && !slot.getResolutions().getResolutionsPerAuthority().isEmpty())
            ? slot.getResolutions().getResolutionsPerAuthority().get(0)
            : null;
    if (resolution != null
        && resolution.getStatus().getCode().equals(StatusCode.ER_SUCCESS_MATCH)
        && resolution.getValues() != null) {
      value = resolution.getValues().get(0).getValue().getId();
    }

    return value;
  }

  Optional<Response> createErrorResponse(
      @NonNull final HandlerInput input, @NonNull final String message) {
    return input
        .getResponseBuilder()
        .withSpeech(message)
        .withSimpleCard(DealBookerUtils.getCardTitle(), message)
        .build();
  }
}
