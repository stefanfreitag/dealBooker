package de.freitag.stefan.alexa.dealbooker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class DealTypeTest {

  @Test
  void fromStringForBuyReturnsExpectedValue() {
    Optional<DealType> dealType = DealType.from("BUY");
    assertTrue(dealType.isPresent());
    assertEquals(DealType.BUY, dealType.get());
  }

  @Test
  void fromStringForSelReturnsExpectedValue() {
    Optional<DealType> dealType = DealType.from("SELL");
    assertTrue(dealType.isPresent());
    assertEquals(DealType.SELL, dealType.get());
  }

  @Test
  void fromStringForUnknownValueThrowsExceptions() {
    Optional<DealType> dealType = DealType.from("UNKNOWN");
    assertTrue(!dealType.isPresent());
  }
}
