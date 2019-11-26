package de.freitag.stefan.alexa.dealbooker;

import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DealHandler
    implements com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler {

  @Override
  public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
    return input.matches(intentName("enterDeal"));
  }

  @Override
  public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
    /**
     * Slot dealTypeSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.DEAL_TYPE.name());
     * Optional<DealType> dealType = getDealType(dealTypeSlot); if (!dealType.isPresent()) {
     * log.error("Deal type is absent."); return createErrorResponse("I had problems in
     * understanding if you would like to buy or sell"); } log.info("Found deal type " + dealType +
     * ".");
     *
     * <p>int quantity; try { quantity = getQuantity(intent); log.info("Found quantity " + quantity
     * + "."); } catch (final DealBookerException exception) { log.error(exception.getMessage(),
     * exception); return createErrorResponse(exception.getMessage()); }
     *
     * <p>Slot unitSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.UNIT.name());
     * Optional<Unit> unit = getUnit(unitSlot); if (!unit.isPresent()) { log.error("Unit value is
     * absent."); return createErrorResponse("I had problems understanding the unit."); }
     * log.info("Found unit " + unit);
     *
     * <p>int price; try { price = getPrice(intent); log.info("Found price " + quantity); } catch
     * (DealBookerException exception) { log.error(exception.getMessage()); return
     * createErrorResponse(exception.getMessage()); }
     *
     * <p>Slot productSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.PRODUCT.name());
     * Optional<Product> product = getProduct(productSlot); if (!product.isPresent()) {
     * log.error("Product information is missing"); return createErrorResponse("I had problems
     * figuring out the product."); } log.info("Found product: " + product);
     *
     * <p>String speechText; if (DealType.BUY.equals(dealType.get())) { speechText =
     * MessageFormat.format(bundle.getString("BUY_INFORMATION"), new Object[]{quantity,
     * unit.get().name(), product.get().name(), price}); } else { speechText =
     * MessageFormat.format(bundle.getString("SELL_INFORMATION"), new Object[]{quantity,
     * unit.get().name(), product.get().name(), price}); } Mailer.sendMail(dealType.get(), quantity,
     * unit.get(), product.get(), price);
     *
     * <p>SimpleCard card = getSimpleCard(CARD_TITLE, speechText); PlainTextOutputSpeech speech =
     * getPlainTextOutputSpeech(speechText); return SpeechletResponse.newTellResponse(speech, card);
     */
    return Optional.empty();
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

  private DealType getDealType(final Slot dealTypeSlot) throws DealBookerException {
    /** String value = getIdFromResolution(dealTypeSlot); return DealType.from(value); */
    return DealType.BUY;
  }

  private Optional<Unit> getUnit(final Slot unitSlot) {
    String value = getIdFromResolution(unitSlot);
    return Unit.from(value.toUpperCase());
  }

  private Optional<Product> getProduct(final Slot productSlot) {
    String value = getIdFromResolution(productSlot);
    return Product.from(value.toUpperCase());
  }

  private String getIdFromResolution(final Slot slot) {
    /**
     * String value = slot.getValue(); Resolution resolution = (slot.getResolutions() != null &&
     * !slot.getResolutions().getResolutionsPerAuthority().isEmpty() ) ?
     * slot.getResolutions().getResolutionsPerAuthority().get(0) : null;
     *
     * <p>if (resolution != null &&
     * resolution.getStatus().getCode().equals(StatusCode.ER_SUCCESS_MATCH) &&
     * resolution.getValueWrappers() != null) { value =
     * resolution.getValueWrapperAtIndex(0).getValue().getId(); }
     *
     * <p>return value;
     */
    return "";
  }
}
