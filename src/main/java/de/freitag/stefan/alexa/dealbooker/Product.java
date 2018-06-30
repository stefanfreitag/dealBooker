package de.freitag.stefan.alexa.dealbooker;

enum Product {
    BOM("bom"),
    ROM("rom"),
    DAYAHEAD("day ahead"),
    WITHINDAY("within day"),
    WEEKEND("weekend");
    private String text;

    Product(final String text) {
        this.text = text;
    }

    String getText() {
        return this.text;
    }

    static Product fromString(final String text) throws  DealBookerException{
        for (Product product : Product.values()) {
            if (product.text.equalsIgnoreCase(text)) {
                return product;
            }
        }
        throw new DealBookerException(text + "is not a valid product");
    }
}
