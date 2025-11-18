package utils; // Example package name

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReporterNG implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Helper method to create report file path with timestamp
    private String getReportPath() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportFileName = "TestReport_" + timestamp + ".html";
        // Ensure 'reports' directory exists in the project root
        return System.getProperty("user.dir") + "/reports/" + reportFileName;
    }

    // Called before any tests are run
    @Override
    public synchronized void onStart(ITestContext context) {
        // 1. Initialize Spark Reporter
        String path = getReportPath();
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Amazon Automation Test Results");
        reporter.config().setDocumentTitle("Amazon Test Report");
        reporter.config().setTheme(Theme.STANDARD); // Set the report theme

        // 2. Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        
        // Add environment details
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Chrome");
        System.out.println("Extent Reports setup complete. Report will be saved to: " + path);
    }

    // Called after all tests are run
    @Override
    public synchronized void onFinish(ITestContext context) {
        // Writes the information gathered from the tests to the report file
        if (extent != null) {
            extent.flush();
            System.out.println("Extent Report has been generated successfully.");
        }
    }

    // Called when a test method starts
    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        // 3. Create a new test entry in the report
        ExtentTest test = extent.createTest(methodName, result.getMethod().getDescription());
        extentTest.set(test); // Store in ThreadLocal for parallel execution safety
        extentTest.get().log(Status.INFO, "Starting Test: " + methodName);
    }

    // Called when a test method succeeds
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        // 4. Log the test status as PASS
        extentTest.get().log(Status.PASS, result.getMethod().getMethodName() + " - **PASSED**");
    }

    // Called when a test method fails
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        // 5. Log the test status as FAIL and include the exception stack trace
        extentTest.get().log(Status.FAIL, result.getMethod().getMethodName() + " - **FAILED**");
        extentTest.get().log(Status.FAIL, result.getThrowable());
        
        // NOTE: Screenshot capturing logic would go here
    }

    // Called when a test method is skipped
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        // 6. Log the test status as SKIP
        extentTest.get().log(Status.SKIP, result.getMethod().getMethodName() + " - **SKIPPED**");
    }

    // Unused methods of the ITestListener interface
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}
