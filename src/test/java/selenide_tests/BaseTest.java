package selenide_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void beforeRunningSuite() {
        Configuration.timeout = 20000;
        Configuration.browserSize = "1920x1080";
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return Selenide.screenshot(OutputType.BYTES);
    }

    @AfterMethod
    public void attachScreenshotOnFailure(ITestResult result) {
        if (!result.isSuccess()) {
            takeScreenshot();
        }
    }
}
