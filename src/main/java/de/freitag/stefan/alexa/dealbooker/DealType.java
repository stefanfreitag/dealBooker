package de.freitag.stefan.alexa.dealbooker;

enum DealType {
    BUY("Buy"), SELL("Sell");

    private String text;

    DealType(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static DealType fromString(final String text) throws DealBookerException {
        for (DealType dealType : DealType.values()) {
            if (dealType.text.equalsIgnoreCase(text)) {
                return dealType;
            }
        }
        throw new DealBookerException("No deal type with text " + text + " found");
    }
}