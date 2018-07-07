package de.freitag.stefan.alexa.dealbooker;

enum DealType {
    BUY, SELL;


    static DealType from(final String text) throws DealBookerException {
        for (DealType dealType : DealType.values()) {
            if (dealType.name().equalsIgnoreCase(text)) {
                return dealType;
            }
        }
        throw new DealBookerException("No deal type with text " + text + " found.");
    }
}