package de.freitag.stefan.alexa.dealbooker;

enum Unit {
    GIGAWATT,
    MEGAWATT,
    KILOWATT,
    WATT;

    static Unit from(final String text) throws DealBookerException {
        for (Unit unit : Unit.values()) {
            if (unit.name().equalsIgnoreCase(text)) {
                return unit;
            }
        }
        throw new DealBookerException("No unit with name " + text + " found.");
    }
}
