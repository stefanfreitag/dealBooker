package de.freitag.stefan.alexa.dealbooker;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

@SuppressWarnings("unused")
public final class DealBookerSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;
    static {
        supportedApplicationIds = new HashSet<>();
        supportedApplicationIds.add("amzn1.ask.skill.aeb31ea6-f0af-49a1-8e4c-9183d077064a");
    }

    public DealBookerSpeechletRequestStreamHandler() {
        super(new DealBookerSpeechlet(), supportedApplicationIds);
    }
}
