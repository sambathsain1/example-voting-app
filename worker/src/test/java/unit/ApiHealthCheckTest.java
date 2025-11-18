package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiHealthCheckTest {

    @Test
    public void checkApiStatus() {
        int statusCode = 500; // mock
        Assert.assertEquals(statusCode, 200, "API is not healthy");
    }
}
