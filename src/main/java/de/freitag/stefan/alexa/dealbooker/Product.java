package de.freitag.stefan.alexa.dealbooker;

import com.google.common.base.Enums;

import java.util.Objects;
import java.util.Optional;

enum Product {
    @SuppressWarnings("unused")
    BOM,
    @SuppressWarnings("unused")
    ROM,
    @SuppressWarnings("unused")
    DAYAHEAD,
    @SuppressWarnings("unused")
    WITHINDAY,
    @SuppressWarnings("unused")
    WEEKEND;

    static Optional<Product> from(final String text) {
        Objects.requireNonNull(text);
        return Enums.getIfPresent(Product.class, text).toJavaUtil();
    }
}
