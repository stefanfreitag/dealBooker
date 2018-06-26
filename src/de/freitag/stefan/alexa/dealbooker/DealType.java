package de.freitag.stefan.alexa.dealbooker;

enum DealType {
    BUY("buy"), SELL("sell");

    private String text;

    DealType(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static DealType fromString(String text) {
        for (DealType dealType : DealType.values()) {
            if (dealType.text.equalsIgnoreCase(text)) {
                return dealType;
            }
        }
        throw new IllegalArgumentException("No deal type with text " + text + " found");
    }
}