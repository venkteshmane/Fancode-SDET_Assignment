package com.FanCode.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static com.FanCode.utilities.constantUtil.DocumentName;
import static com.FanCode.utilities.constantUtil.ReportName;

public class extentReport implements ITestListener {
    private static ExtentReports reports;
    public Properties prop;
    private static ExtentSparkReporter sparkReporter;
    private static final String OutputFolder = System.getProperty("user.dir") + "/Attachments/Extent_Reports/";
    public static String timeStamp = new SimpleDateFormat(" HH:mm:ss:SSS dd-MM-yyyy").format(new Date());
    private static final String FileName = "Test Report" + timeStamp;

    public static boolean isApi = false;


    private static ExtentTest extentTest;
    public static ThreadLocal<ExtentTest> tlTest = new ThreadLocal<ExtentTest>();


    public ExtentReports initExtentReports() {
        prop = new Properties();
        Path path = Paths.get(OutputFolder);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sparkReporter = new ExtentSparkReporter(OutputFolder + FileName);
        sparkReporter.config().setReportName(ReportName + timeStamp);
        sparkReporter.config().setDocumentTitle(DocumentName);

        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Environment", System.getProperty("env"));
        reports.setSystemInfo("Author", "Venktesh Mane");

        return reports;
    }

    public ExtentReports extent = initExtentReports();
    // ClassName:- result.getTestClass().getName()

    public void onStart(ITestContext context) {
        System.out.println("Test Suit Started.");
    }

    public void onTestStart(ITestResult result) {
        extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        System.out.println("Test Case: " + result.getMethod().getMethodName() + " Started!");

        tlTest.set(extentTest);
        tlTest.get().assignCategory(result.getTestContext().getSuite().getName().toUpperCase());
    }

    public void onTestSuccess(ITestResult result) {
        tlTest.get().log(Status.INFO, MarkupHelper.createLabel("Class : " + result.getTestClass().getName() + " is Running ", ExtentColor.BLUE));
        tlTest.get().log(Status.PASS, MarkupHelper.createLabel("Test Case: " + result.getMethod().getMethodName() + " is PASSED! ", ExtentColor.GREEN));
    }


    public void onTestFailure(ITestResult result) {
        tlTest.get().log(Status.INFO, MarkupHelper.createLabel("Class : " + result.getTestClass().getName() + " is Running ", ExtentColor.BLUE));
        tlTest.get().fail("EXCEPTION OCCURRED : \n" + result.getThrowable());
        tlTest.get().log(Status.FAIL, MarkupHelper.createLabel("Test Case: " + result.getMethod().getMethodName() + " is FAILED! ", ExtentColor.RED));

    }

    public void onTestSkipped(ITestResult result) {
        tlTest.get().log(Status.INFO, MarkupHelper.createLabel("Class : " + result.getTestClass().getName() + " is Running ", ExtentColor.BLUE));
        tlTest.get().log(Status.SKIP, MarkupHelper.createLabel("Test Case: " + result.getMethod().getMethodName() + " is SKIPPED! ", ExtentColor.GREY));

    }

    public void onFinish(ITestContext context) {
        System.out.println("Test Suit is Completed.");
        extent.flush();
        extent.getStats();
    }


}
