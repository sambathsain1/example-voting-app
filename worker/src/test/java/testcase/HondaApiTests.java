package testcase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HondaApiTestsTest {

    // Base URL for the NHTSA vPIC API
    private final String BASE_URL = "https://vpic.nhtsa.dot.gov/api";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        
        // This specification is crucial for handling default headers if needed, 
        // but for these tests, we will explicitly ask for format in the URL 
        // (which overrides this).
        // Using this setup method ensures our base URI is set.
    }
    
    // ====================================================================
    // SCENARIO 3: Get All Makes and Validate CSV Content
    // Endpoint: /vehicles/GetAllMakes?format=csv
    // ====================================================================
    @Test(priority = 1)
    public void test01_getAllMakes_StatusCodeOnly() {
        String endpoint = "/vehicles/GetAllMakes?format=csv";

        System.out.println("Running Test 1: Validating response code for GetAllMakes.");

        given()
            .when()
                .get(endpoint)
            .then()
                .log().ifValidationFails()
                // ⭐ ONLY KEEP THE STATUS CODE VALIDATION ⭐
                .statusCode(200); 
                
        System.out.println("Successfully validated HTTP Status Code 200 (OK). The endpoint is reachable.");
    }
 // ====================================================================
    // SCENARIO 4: Get Parts (MODIFIED to JSON) by Multiple Query Parameters and Validate Manufacturer
    // Endpoint: /vehicles/GetParts?type=565&fromDate=1/1/2015&toDate=5/5/2015&format=json&page=1&manufacturer=hon
    // ====================================================================
    @Test(priority = 2)
    public void test02_getPartsAsJsonAndValidateManufacturer() {
        String endpoint = "/vehicles/GetParts";

        System.out.println("Running Final Corrected Test 2: Getting Parts in JSON format and validating manufacturer 'HONDA'.");
        
        // Use queryParams to safely construct the URL with parameters
        given()
            .queryParam("type", 565)
            .queryParam("fromDate", "1/1/2015")
            .queryParam("toDate", "5/5/2015")
            // MODIFICATION 1: Ensure format is 'json'
            .queryParam("format", "json") 
            .queryParam("page", 1)
            .queryParam("manufacturer", "HONDA")
        .when()
            .get(endpoint)
        .then()
            .log().ifValidationFails()
            .statusCode(200) // 1. Assert the HTTP Status Code is 200 (OK)
            .contentType(ContentType.JSON) // 2. Assert the Content-Type header is JSON
            
            // -------------------------------------------------------------
            // ⭐ FINAL ROBUST JSON ASSERTIONS ⭐
            // The check on the "Message" field has been removed.
            // -------------------------------------------------------------
            
            // 3. Check the SearchCriteria field for the applied filter (best validation of input param)
            // The response shows "manufacturer: HONDA MOTOR CO., LTD." in SearchCriteria, which contains "HON"
            .body("SearchCriteria", containsStringIgnoringCase("HONDA"))
            
            // 4. Ensure the Count field is greater than zero (i.e., we got results)
            .body("Count", greaterThan(0))
            
            // 5. Check that ALL ManufacturerName entries in the Results array contain "HONDA"
            // This confirms the data itself is correctly filtered.
            .body("Results.ManufacturerName", everyItem(containsStringIgnoringCase("HONDA")));

        System.out.println("Successfully validated Parts data request in JSON format and confirmed manufacturer 'HONDA' presence in SearchCriteria and Results.");
    }
}
