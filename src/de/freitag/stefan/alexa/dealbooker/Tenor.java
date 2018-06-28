package de.freitag.stefan.alexa.dealbooker;

enum Tenor {
    DAYAHEAD("day ahead"),
    WITHINDAY("within day"),
    WEEKEND("weekend");
    private String text;

    Tenor(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static Tenor fromString(String text) {
        for (Tenor tenor : Tenor.values()) {
            if (tenor.text.equalsIgnoreCase(text)) {
                return tenor;
            }
        }
        throw new IllegalArgumentException("No tenor with text " + text + " found");
    }
}
