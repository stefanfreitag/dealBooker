package de.freitag.stefan.alexa.dealbooker;

enum Unit {
    GIGAWATT("Gigawatt"),
    MEGAWATT("Megawatt"),
    KILOWATT("Kilowatt"),
    WATT("Watt");

    private String text;

    Unit(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static Unit fromString(final String text) {
        for (Unit unit : Unit.values()) {
            if (unit.text.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("No unit with text " + text + " found");
    }
}
