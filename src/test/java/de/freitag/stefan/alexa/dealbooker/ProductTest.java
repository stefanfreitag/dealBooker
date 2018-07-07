package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void fromStringForUnknownValueThrowsExceptions() {
        assertThrows(DealBookerException.class, () -> Product.from("unknownProduct"));
    }
}