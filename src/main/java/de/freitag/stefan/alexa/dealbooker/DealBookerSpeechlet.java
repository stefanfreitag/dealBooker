package de.freitag.stefan.alexa.dealbooker;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.slu.entityresolution.Resolution;
import com.amazon.speech.slu.entityresolution.StatusCode;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;


public class DealBookerSpeechlet implements SpeechletV2 {

    private static final Logger log = LoggerFactory.getLogger(DealBookerSpeechlet.class);

    private ResourceBundle bundle;

    /**
     * Title used when displaying cards on Alexa devices.
     */
    private static final String CARD_TITLE = "Deal Booker";


    @Override
    public void onSessionStarted(final SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        SessionStartedRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(final SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        LaunchRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        Locale locale = request.getLocale();
        log.info("Found locale: " + locale);
        bundle = ResourceBundle.getBundle("DealBookerSpeechlet", locale);
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("enterDeal".equals(intentName)) {
            return bookDeal(intent);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else if ("AMAZON.CancelIntent".equals(intentName) || "AMAZON.StopIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(bundle.getString("GOODBYE"));
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else {
            return getAskResponse(CARD_TITLE, bundle.getString("DO_NOT_UNDERSTAND"));
        }
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
    }

    private SpeechletResponse getWelcomeResponse() {
        String welcome = bundle.getString("WELCOME") + " " + bundle.getString("WELCOME_REPROMPT");
        String welcomeReprompt = bundle.getString("WELCOME_REPROMPT");
        return newAskResponse(welcome, false, welcomeReprompt, false);
    }

    private SpeechletResponse bookDeal(final Intent intent) {

        Slot dealTypeSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.DEAL_TYPE.name());
        DealType dealType;
        try {
            dealType = getDealType(dealTypeSlot);
            log.info("Found deal type  " + dealType + ".");
        } catch (final DealBookerException exception) {
            log.error(exception.getMessage());
            return createErrorResponse(exception.getMessage());
        }

        int quantity;
        try {
            quantity = getQuantity(intent);
            log.info("Found quantity  " + quantity + ".");
        } catch (final DealBookerException exception) {
            log.error(exception.getMessage());
            return createErrorResponse(exception.getMessage());
        }

        Slot unitSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.UNIT.name());
        Unit unit;
        try {
            unit = getUnit(unitSlot);
            log.info("Found unit " + unit);
        } catch (final DealBookerException exception) {
            log.error(exception.getMessage());
            return createErrorResponse(exception.getMessage());
        }

        int price;
        try {
            price = getPrice(intent);
            log.info("Found price  " + quantity);
        } catch (DealBookerException exception) {
            log.error(exception.getMessage());
            return createErrorResponse(exception.getMessage());
        }

        Slot productSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.PRODUCT.name());
        Product product;
        try {
            product = Product.from(productSlot.getValue());
            log.info("Found product: " + product);
        } catch (DealBookerException exception) {
            log.error(exception.getMessage());
            return createErrorResponse(exception.getMessage());
        }

        //TODO: Lokalisieren
        String speechText = dealType.name() + " " + quantity + " " + unit.name() + " " + product.getText();

        Mailer.sendMail(dealType, quantity, unit, product, price);

        SimpleCard card = getSimpleCard(CARD_TITLE, speechText);

        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    //TODO: Refactoring with getQuantity
    private int getPrice(final Intent intent) throws DealBookerException {
        Slot priceSlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.PRICE.name());
        String value = priceSlot.getValue();
        if (value != null) {
            return Integer.valueOf(priceSlot.getValue());
        } else {
            throw new DealBookerException("Information on price is missing.");
        }
    }

    private int getQuantity(final Intent intent) throws DealBookerException {
        Slot quantitySlot = intent.getSlot(de.freitag.stefan.alexa.dealbooker.Slot.QUANTITY.name());
        String value = quantitySlot.getValue();
        if (value != null) {
            return Integer.valueOf(quantitySlot.getValue());
        } else {
            throw new DealBookerException("Information on quantity is missing.");
        }
    }

    private DealType getDealType(final Slot dealTypeSlot) throws DealBookerException {
        String value = getIdFromResolution(dealTypeSlot);
        return DealType.from(value);
    }

    private Unit getUnit(final Slot unitSlot) throws DealBookerException {
        String value = getIdFromResolution(unitSlot);
        return Unit.from(value);
    }

    private String getIdFromResolution(final Slot slot) {
        String value = slot.getValue();
        Resolution resolution = (slot.getResolutions() != null
                && !slot.getResolutions().getResolutionsPerAuthority().isEmpty()
        ) ? slot.getResolutions().getResolutionsPerAuthority().get(0) : null;

        if (resolution != null
                && resolution.getStatus().getCode().equals(StatusCode.ER_SUCCESS_MATCH)
                && resolution.getValueWrappers() != null) {
            value = resolution.getValueWrapperAtIndex(0).getValue().getId();
        }
        return value;
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        return getAskResponse(CARD_TITLE, bundle.getString("HELP"));
    }

    /**
     * Helper method that creates a card object.
     *
     * @param title   title of the card
     * @param content body of the card
     * @return SimpleCard the display card to be sent along with the voice response.
     */
    private SimpleCard getSimpleCard(final String title, final String content) {
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(content);

        return card;
    }

    /**
     * Helper method for retrieving an OutputSpeech object when given a string of TTS.
     *
     * @param speechText the text that should be spoken out to the user.
     * @return an instance of SpeechOutput.
     */
    private PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return speech;
    }

    /**
     * Helper method that returns a reprompt object. This is used in Ask responses where you want
     * the user to be able to respond to your speech.
     *
     * @param outputSpeech The OutputSpeech object that will be said once and repeated if necessary.
     * @return Reprompt instance.
     */
    private Reprompt getReprompt(OutputSpeech outputSpeech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);

        return reprompt;
    }

    /**
     * Helper method for retrieving an Ask response with a simple card and reprompt included.
     *
     * @param cardTitle  Title of the card that you want displayed.
     * @param speechText speech text that will be spoken to the user.
     * @return the resulting card and speech text.
     */
    private SpeechletResponse getAskResponse(String cardTitle, String speechText) {
        SimpleCard card = getSimpleCard(cardTitle, speechText);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
        Reprompt reprompt = getReprompt(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Wrapper for creating the Ask response from the input strings.
     *
     * @param stringOutput   the output to be spoken
     * @param isOutputSsml   whether the output text is of type SSML (Speech Synthesis Markup Language)
     * @param repromptText   the reprompt for if the user doesn't reply or is misunderstood.
     * @param isRepromptSsml whether the reprompt text is of type SSML
     * @return SpeechletResponse the speechlet response
     */
    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
                                             String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;

        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }


        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    SpeechletResponse createErrorResponse(final String errorMessage) {
        SimpleCard card = getSimpleCard(CARD_TITLE, errorMessage);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(errorMessage);
        return SpeechletResponse.newTellResponse(speech, card);
    }


}