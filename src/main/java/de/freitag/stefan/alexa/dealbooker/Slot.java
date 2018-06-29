package de.freitag.stefan.alexa.dealbooker;

enum Slot {
    DEAL_TYPE("dealType"),
    AMOUNT("amount"),
    UNIT("unit");

    private String text;

    Slot(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static Slot fromString(String text) {
        for (Slot slot : Slot.values()) {
            if (slot.text.equalsIgnoreCase(text)) {
                return slot;
            }
        }
        throw new IllegalArgumentException("No slot with text " + text + " found");
    }

}
