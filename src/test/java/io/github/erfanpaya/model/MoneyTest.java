package io.github.erfanpaya.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void doubleArithmeticIsImpreciseForCents() {
        assertNotEquals(0.10 + 0.20, 0.30);
        System.out.println(0.10 + 0.20);
    }
}
