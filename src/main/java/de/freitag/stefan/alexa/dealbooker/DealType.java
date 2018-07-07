package de.freitag.stefan.alexa.dealbooker;

import com.google.common.base.Enums;

import java.util.Objects;
import java.util.Optional;

enum DealType {
    BUY, SELL;

    static Optional<DealType> from(final String text) {
        Objects.requireNonNull(text);
        return Enums.getIfPresent(DealType.class, text).toJavaUtil();
    }
}