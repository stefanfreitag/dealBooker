package de.freitag.stefan.alexa.dealbooker;

enum Unit {
    MEGAWATT("megawatt"),
    KILOWATT("kilowatt");
    private String text;

    Unit(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static Unit fromString(String text) {
        for (Unit unit : Unit.values()) {
            if (unit.text.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("No unit with text " + text + " found");
    }
}
