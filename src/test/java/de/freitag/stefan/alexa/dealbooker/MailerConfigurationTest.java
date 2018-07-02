package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailerConfigurationTest {

    private static MailerConfiguration mailerConfiguration;

    @BeforeAll
    @SuppressWarnings("unused")
    static void setup(){
        mailerConfiguration = new MailerConfiguration();
    }

    @Test
    void getFromReturnsNotNullValue() {
        assertNotNull(mailerConfiguration.getFrom());
    }
    @Test
    void getSubjectReturnsNotNullValue() {
        assertNotNull(mailerConfiguration.getSubject());
    }
    @Test
    void getToReturnsNotNullValue() {
        assertNotNull(mailerConfiguration.getTo());
    }
}