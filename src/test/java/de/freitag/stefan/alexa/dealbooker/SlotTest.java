package de.freitag.stefan.alexa.dealbooker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

final class SlotTest {

  @Test
  void fromStringForUnknownValueThrowsExceptions() {
    assertThrows(IllegalArgumentException.class, () -> Slot.from("unknownSlot"));
  }
}
