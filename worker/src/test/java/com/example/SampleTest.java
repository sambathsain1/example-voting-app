package com.example;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import org.testng.Assert;

public class SampleTest {

    @Test
    @Description("Verify basic functionality")
    public void verifyTrueCondition() {
        Assert.assertTrue(true);  // TestNG assert
    }
}
