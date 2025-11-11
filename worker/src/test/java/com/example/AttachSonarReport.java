package com.example;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class AttachSonarReport {

    @Test
    void attachSonarSummary() throws FileNotFoundException {
        Allure.addAttachment(
            "SonarQube Quality Gate Summary",
            new FileInputStream("target/allure-results/sonarqube-summary.json")
        );
    }
}
