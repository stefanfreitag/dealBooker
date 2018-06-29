package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class UnitTest {

    @Test
    void fromStringForWattReturnsExpectedValue() {
        assertEquals(Unit.WATT,Unit.fromString("watt"));
    }

    @Test
    void fromStringForKilowattReturnsExpectedValue() {
        assertEquals(Unit.KILOWATT,Unit.fromString("kilowatt"));
    }

    @Test
    void fromStringForMegawattReturnsExpectedValue() {
        assertEquals(Unit.MEGAWATT,Unit.fromString("megawatt"));
    }

    @Test
    void fromStringForGigawattReturnsExpectedValue() {
        assertEquals(Unit.GIGAWATT,Unit.fromString("gigawatt"));
    }

    @Test
    void fromStringForUnknownValueThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> Unit.fromString("unknownUnit"));
    }
}