package com.example;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleTest {

    @Test
    @Description("Verify basic functionality")
    void verifyTrueCondition() {
        assertTrue(true);
    }
}
