package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealTypeTest {

    @Test
    void fromStringForBuyReturnsExpectedValue() {
        assertEquals(DealType.BUY,DealType.fromString("buy"));
    }

    @Test
    void fromStringForSelReturnsExpectedValue() {
        assertEquals(DealType.SELL,DealType.fromString("sell"));
    }

}