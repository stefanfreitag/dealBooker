package de.freitag.stefan.alexa.dealbooker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SlotTest {

    @Test
    void fromStringForUnknownValueThrowsExceptions() {
        assertThrows(IllegalArgumentException.class, () -> Slot.from("unknownSlot"));
    }
}