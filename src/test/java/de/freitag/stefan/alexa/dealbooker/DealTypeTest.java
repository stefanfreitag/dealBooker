package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealTypeTest {

    @Test
    void fromStringForBuyReturnsExpectedValue() throws DealBookerException {
        assertEquals(DealType.BUY,DealType.fromString("buy"));
    }

    @Test
    void fromStringForSelReturnsExpectedValue()throws DealBookerException {
        assertEquals(DealType.SELL,DealType.fromString("sell"));
    }

    @Test
    void fromStringForUnknownValueThrowsExceptions() {
        assertThrows(DealBookerException.class, () -> DealType.fromString("unknownDealType"));
    }

}