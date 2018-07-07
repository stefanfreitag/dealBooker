package de.freitag.stefan.alexa.dealbooker;

import jdk.nashorn.internal.ir.annotations.Ignore;
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


    @Ignore
    @Test
    void getFromReturnsNotNullValue() {

        //assertNotNull(mailerConfiguration.getFrom());
    }
    @Ignore
    @Test
    void getSubjectReturnsNotNullValue() {

        //assertNotNull(mailerConfiguration.getSubject());
    }
    @Ignore
    @Test
    void getToReturnsNotNullValue() {
       // assertNotNull(mailerConfiguration.getTo());
    }
}