package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MailerConfigurationTest {

  private static MailerConfiguration mailerConfiguration;

  @BeforeAll
  @SuppressWarnings("unused")
  static void setup() {
    mailerConfiguration = new MailerConfiguration();
  }

  @Disabled
  @Test
  void getFromReturnsNotNullValue() {

    // assertNotNull(mailerConfiguration.getFrom());
  }

  @Disabled
  @Test
  void getSubjectReturnsNotNullValue() {

    // assertNotNull(mailerConfiguration.getSubject());
  }

  @Disabled
  @Test
  void getToReturnsNotNullValue() {
    // assertNotNull(mailerConfiguration.getTo());
  }
}
