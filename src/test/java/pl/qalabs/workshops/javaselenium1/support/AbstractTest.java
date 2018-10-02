package pl.qalabs.workshops.javaselenium1.support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@ExtendWith({BrowserExtension.class, ScreenshotTaker.class})
public abstract class AbstractTest {

    protected Browser browser;
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp(Browser browser) {
        this.browser = browser;
        this.driver = browser.getDriver();
        this.wait = browser.getWait();
        driver.get(getUrl());
    }

    protected abstract String getUrl();

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
