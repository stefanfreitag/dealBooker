package de.freitag.stefan.alexa.dealbooker;

enum Slot {
  DEAL_TYPE,
  QUANTITY,
  PRICE,
  PRODUCT,
  UNIT;

  static Slot from(final String text) {
    for (Slot slot : Slot.values()) {
      if (slot.name().equalsIgnoreCase(text)) {
        return slot;
      }
    }
    throw new IllegalArgumentException("No slot with text " + text + " found.");
  }
}
