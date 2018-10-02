package pl.qalabs.workshops.javaselenium1.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotTaker implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        // Browser browser = context.getStore(ExtensionContext.Namespace.create(BrowserExtension.class)).get("browser", Browser.class);
        Browser browser = BrowserContextHolder.getBrowser();
        WebDriver driver = browser.getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        var name = screenshot.getName();
        Files.copy(screenshot.toPath(), Paths.get(browser.getBrowserConfig().getScreenshotsPath(), name), StandardCopyOption.REPLACE_EXISTING);
        throw throwable;
    }
}
